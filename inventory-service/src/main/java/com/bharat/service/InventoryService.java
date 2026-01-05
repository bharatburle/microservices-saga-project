package com.bharat.service;

import org.springframework.stereotype.Service;

import com.bharat.entity.InventoryEntity;
import com.bharat.respository.InventoryRepository;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@RequiredArgsConstructor
public class InventoryService {

    private final InventoryRepository inventoryRepository;

    public String reserveStock(String itemName, int qty) {
        InventoryEntity stock = inventoryRepository.findByItemNameIgnoreCase(itemName)
                .orElse(null);

        if (stock == null) {
            log.warn("Item not found: {}", itemName);
            return "FAILED";
        }

        if (stock.getAvailableQuantity() < qty) {
            log.warn("Not enough stock for item: {}", itemName);
            return "FAILED";
        }

        stock.setAvailableQuantity(stock.getAvailableQuantity() - qty);
        inventoryRepository.save(stock);

        log.info("Stock reserved: {} Qty: {}", itemName, qty);
        return "SUCCESS";
    }

    public void releaseStock(String itemName, int qty) {
        InventoryEntity stock = inventoryRepository.findByItemNameIgnoreCase(itemName)
                .orElse(null);

        if (stock != null) {
            stock.setAvailableQuantity(stock.getAvailableQuantity() + qty);
            inventoryRepository.save(stock);
            log.info("Stock released: {} Qty: {}", itemName, qty);
        } else {
            log.warn("No stock record found for release, item={}", itemName);
        }
    }
}
