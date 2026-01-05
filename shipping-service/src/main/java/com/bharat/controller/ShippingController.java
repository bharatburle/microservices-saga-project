package com.bharat.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.bharat.entity.ShippingEntity;
import com.bharat.service.ShippingService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequestMapping("/shipping")
@RequiredArgsConstructor
public class ShippingController {

    private final ShippingService shippingService;

    @PostMapping("/schedule")
    public ResponseEntity<ShippingEntity> schedule(@RequestBody ShippingEntity request) {

        log.info("Received shipping schedule request: {}", request);

        return ResponseEntity.ok(shippingService.scheduleShipping(request));
    }
}
