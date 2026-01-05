package com.bharat.respository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.bharat.entity.InventoryEntity;

public interface InventoryRepository extends JpaRepository<InventoryEntity, Long> {
    Optional<InventoryEntity> findByItemNameIgnoreCase(String itemName);

}
