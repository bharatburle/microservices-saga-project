package com.bharat.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.bharat.service.InventoryService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequestMapping("/inventory")
@RequiredArgsConstructor
public class InventoryController {

	private final InventoryService inventoryService;

	@PostMapping("/reserve")
	public ResponseEntity<String> reserveStock(@RequestParam String item, @RequestParam int qty) {

		log.info("Reserve stock request: {} {}", item, qty);
		String result = inventoryService.reserveStock(item, qty);
		return ResponseEntity.ok(result);
	}

	@PostMapping("/release")
	public ResponseEntity<Void> releaseStock(@RequestParam String item, @RequestParam int qty) {

		log.warn("Release stock (compensation) request: {} {}", item, qty);
		inventoryService.releaseStock(item, qty);
		return ResponseEntity.ok().build();
	}
}
