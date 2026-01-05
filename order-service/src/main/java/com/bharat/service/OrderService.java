package com.bharat.service;

import org.springframework.stereotype.Service;

import com.bharat.entity.OrderEntity;
import com.bharat.repository.OrderRepository;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@RequiredArgsConstructor
public class OrderService {

    private final OrderRepository orderRepository;

    public OrderEntity createOrder(OrderEntity order) {
        order.setStatus("PENDING");
        OrderEntity saved = orderRepository.save(order);
        log.info("Order created: {}", saved);
        return saved;
    }
}
