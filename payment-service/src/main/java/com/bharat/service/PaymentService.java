package com.bharat.service;

import org.springframework.stereotype.Service;

import com.bharat.entity.PaymentEntity;
import com.bharat.repository.PaymentRepository;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@RequiredArgsConstructor
public class PaymentService {

	private final PaymentRepository paymentRepository;

	public PaymentEntity processPayment(PaymentEntity payment) {

		// Mock logic for RnD: Always succeed
		payment.setStatus("SUCCESS");

		PaymentEntity saved = paymentRepository.save(payment);
		log.info("Payment processed: {}", saved);

		return saved;
	}

	public void refundPayment(Long orderId) {
		PaymentEntity lastPayment = paymentRepository.findTopByOrderIdOrderByIdDesc(orderId).orElse(null);

		if (lastPayment != null) {
			lastPayment.setStatus("REFUNDED");
			paymentRepository.save(lastPayment);
			log.info("Payment refunded for orderId={}", orderId);
		} else {
			log.warn("No payment found to refund for orderId={}", orderId);
		}
	}
}
