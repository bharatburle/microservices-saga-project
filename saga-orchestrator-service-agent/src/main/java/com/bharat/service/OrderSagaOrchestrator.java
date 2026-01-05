package com.bharat.service;

import java.util.Map;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.bharat.dto.SagaOrderRequest;
import com.bharat.event.SagaEvent;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@RequiredArgsConstructor
public class OrderSagaOrchestrator {

	private final RestTemplate restTemplate;
	private final SagaEventPublisher eventPublisher;

	public String startSaga(SagaOrderRequest req) {

		// ---------------- 1. ORDER ----------------
		SagaEvent startEvent = SagaEvent.builder().sagaId("TEMP") // set after you get orderId
				.step("ORDER").status("STARTED").details("Starting saga for item=" + req.getItem()).build();
		eventPublisher.publish(startEvent);

		Map<String, Object> orderPayload = Map.of("item", req.getItem(), "quantity", req.getQuantity(), "status",
				"PENDING");

		ResponseEntity<Map> orderResponse = restTemplate.postForEntity("http://order-service/orders", orderPayload,
				Map.class);

		Long orderId = Long.parseLong(orderResponse.getBody().get("id").toString());
		log.info("Order created with ID: {}", orderId);

		eventPublisher.publish(SagaEvent.builder().sagaId(orderId.toString()).step("ORDER").status("SUCCESS")
				.details("Order created").build());

		try {
			// ---------------- 2. PAYMENT ----------------
			Map<String, Object> paymentPayload = Map.of("orderId", orderId, "amount", req.getAmount());

			ResponseEntity<Map> paymentResponse = restTemplate.postForEntity("http://payment-service/payment/process",
					paymentPayload, Map.class);

			String paymentStatus = paymentResponse.getBody().get("status").toString();
			log.info("Payment status: {}", paymentStatus);

			eventPublisher.publish(SagaEvent.builder().sagaId(orderId.toString()).step("PAYMENT").status(paymentStatus)
					.details("Payment processed").build());

			if (!"SUCCESS".equals(paymentStatus)) {
				compensatePayment(orderId, req);
				return "FAILED at PAYMENT step (compensation triggered)";
			}

			// ---------------- 3. INVENTORY ----------------
			String inventoryResult = restTemplate.postForObject(
					"http://inventory-service/inventory/reserve?item={item}&qty={qty}", null, String.class,
					req.getItem(), req.getQuantity());

			log.info("Inventory result: {}", inventoryResult);

			eventPublisher.publish(SagaEvent.builder().sagaId(orderId.toString()).step("INVENTORY")
					.status(inventoryResult).details("Inventory reservation result").build());

			if (!"SUCCESS".equals(inventoryResult)) {
				compensatePayment(orderId, req);
				// no inventory reserved (or partial) – you can also handle here
				return "FAILED at INVENTORY step (compensation triggered)";
			}

			// ---------------- 4. SHIPPING ----------------
			Map<String, Object> shippingPayload = Map.of("orderId", orderId, "address", req.getAddress());

			ResponseEntity<Map> shippingResponse = restTemplate
					.postForEntity("http://shipping-service/shipping/schedule", shippingPayload, Map.class);

			String shippingStatus = shippingResponse.getBody().get("status").toString();
			log.info("Shipping status: {}", shippingStatus);

			eventPublisher.publish(SagaEvent.builder().sagaId(orderId.toString()).step("SHIPPING")
					.status(shippingStatus).details("Shipping schedule result").build());

			if (!"SCHEDULED".equals(shippingStatus)) {
				// shipping failed -> compensation of payment + inventory
				compensatePayment(orderId, req);
				compensateInventory(req);
				return "FAILED at SHIPPING step (compensation triggered)";
			}

			// ---------------- SUCCESS ----------------
			eventPublisher.publish(SagaEvent.builder().sagaId(orderId.toString()).step("SAGA").status("COMPLETED")
					.details("Order completed successfully").build());

			log.info("ORDER {} COMPLETED SUCCESSFULLY", orderId);
			return "ORDER COMPLETED (Saga Success)";

		} catch (Exception e) {
			log.error("Unexpected error in Saga, triggering compensation", e);
			eventPublisher.publish(SagaEvent.builder().sagaId(orderId.toString()).step("SAGA").status("FAILED")
					.details("Unexpected error: " + e.getMessage()).build());
			compensatePayment(orderId, req);
			compensateInventory(req);
			return "FAILED due to exception. Compensation triggered.";
		}
	}

	// ---------------- COMPENSATION METHODS ----------------

	private void compensatePayment(Long orderId, SagaOrderRequest req) {
		log.warn("Compensating PAYMENT for orderId={}", orderId);

		eventPublisher.publish(SagaEvent.builder().sagaId(orderId.toString()).step("PAYMENT").status("COMPENSATING")
				.details("Refunding payment").build());

		restTemplate.postForLocation("http://payment-service/payment/refund?orderId={orderId}", null, orderId);
	}

	private void compensateInventory(SagaOrderRequest req) {
		log.warn("Compensating INVENTORY for item={} qty={}", req.getItem(), req.getQuantity());

		eventPublisher.publish(SagaEvent.builder().sagaId("UNKNOWN") // or pass orderId if you store mapping
				.step("INVENTORY").status("COMPENSATING").details("Releasing reserved stock").build());

		restTemplate.postForLocation("http://inventory-service/inventory/release?item={item}&qty={qty}", null,
				req.getItem(), req.getQuantity());
	}
}
