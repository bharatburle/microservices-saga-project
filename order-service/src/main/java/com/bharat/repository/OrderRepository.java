package com.bharat.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.bharat.entity.OrderEntity;

public interface OrderRepository extends JpaRepository<OrderEntity, Long> {
}
