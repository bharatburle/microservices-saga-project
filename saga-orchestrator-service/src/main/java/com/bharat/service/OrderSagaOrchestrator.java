package com.bharat.service;

import java.util.Map;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.bharat.dto.SagaOrderRequest;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@RequiredArgsConstructor
public class OrderSagaOrchestrator {

	private final RestTemplate restTemplate;

	public String startSaga(SagaOrderRequest req) {

		log.info("Starting Saga for order: {}", req);

		// --------------------------------------------------
		// 1. ORDER SERVICE
		// --------------------------------------------------
		Map<String, Object> orderPayload = Map.of("item", req.getItem(), "quantity", req.getQuantity(), "status",
				"PENDING");

		@SuppressWarnings("rawtypes")
		ResponseEntity<Map> orderResponse = restTemplate.postForEntity("http://order-service/orders", orderPayload,
				Map.class);

		Long orderId = Long.parseLong(orderResponse.getBody().get("id").toString());
		log.info("Order created with ID: {}", orderId);

		// --------------------------------------------------
		// 2. PAYMENT SERVICE
		// --------------------------------------------------
		Map<String, Object> paymentPayload = Map.of("orderId", orderId, "amount", req.getAmount());

		@SuppressWarnings("rawtypes")
		ResponseEntity<Map> paymentResponse = restTemplate.postForEntity("http://payment-service/payment/process",
				paymentPayload, Map.class);

		String paymentStatus = paymentResponse.getBody().get("status").toString();
		log.info("Payment status: {}", paymentStatus);

		if (!paymentStatus.equals("SUCCESS")) {
			return "FAILED at PAYMENT step";
		}

		// --------------------------------------------------
		// 3. INVENTORY SERVICE
		// --------------------------------------------------
		String inventoryResult = restTemplate.postForObject(
				"http://inventory-service/inventory/reserve?item={item}&qty={qty}", null, String.class, req.getItem(),
				req.getQuantity());

		log.info("Inventory result: {}", inventoryResult);

		if (!inventoryResult.equals("SUCCESS")) {
			return "FAILED at INVENTORY step";
		}

		// --------------------------------------------------
		// 4. SHIPPING SERVICE
		// --------------------------------------------------
		Map<String, Object> shippingPayload = Map.of("orderId", orderId, "address", req.getAddress());

		ResponseEntity<Map> shippingResponse = restTemplate.postForEntity("http://shipping-service/shipping/schedule",
				shippingPayload, Map.class);

		String shippingStatus = shippingResponse.getBody().get("status").toString();
		log.info("Shipping status: {}", shippingStatus);

		if (!shippingStatus.equals("SCHEDULED")) {
			return "FAILED at SHIPPING step";
		}

		// --------------------------------------------------
		// SUCCESS
		// --------------------------------------------------
		log.info("ORDER {} COMPLETED SUCCESSFULLY", orderId);
		return "ORDER COMPLETED (Saga Success)";
	}
}
