package com.bharat.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.bharat.entity.PaymentEntity;
import com.bharat.service.PaymentService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequestMapping("/payment")
@RequiredArgsConstructor
public class PaymentController {

	private final PaymentService paymentService;

	@PostMapping("/process")
	public ResponseEntity<PaymentEntity> process(@RequestBody PaymentEntity payment) {

		log.info("Received payment request: {}", payment);
		return ResponseEntity.ok(paymentService.processPayment(payment));
	}

	@PostMapping("/refund")
	public ResponseEntity<Void> refund(@RequestParam Long orderId) {
		log.warn("Refund request for orderId={}", orderId);
		paymentService.refundPayment(orderId);
		return ResponseEntity.ok().build();
	}
}
