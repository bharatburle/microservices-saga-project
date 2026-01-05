package com.bharat.service;

import org.springframework.stereotype.Service;

import com.bharat.entity.ShippingEntity;
import com.bharat.repository.ShippingRepository;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@RequiredArgsConstructor
public class ShippingService {

    private final ShippingRepository shippingRepository;

    public ShippingEntity scheduleShipping(ShippingEntity shipping) {

        // RnD logic: Always SUCCESS
        shipping.setStatus("SCHEDULED");

        ShippingEntity saved = shippingRepository.save(shipping);
        log.info("Shipping scheduled: {}", saved);

        return saved;
    }
}
