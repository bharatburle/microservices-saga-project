package com.bharat.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.bharat.entity.ShippingEntity;

public interface ShippingRepository extends JpaRepository<ShippingEntity, Long> {
}
