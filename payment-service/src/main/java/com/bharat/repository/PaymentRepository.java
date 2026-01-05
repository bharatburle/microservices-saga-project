package com.bharat.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.bharat.entity.PaymentEntity;

public interface PaymentRepository extends JpaRepository<PaymentEntity, Long> {
	Optional<PaymentEntity> findTopByOrderIdOrderByIdDesc(Long orderId);

}
