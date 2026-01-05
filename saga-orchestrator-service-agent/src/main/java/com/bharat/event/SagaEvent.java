package com.bharat.event;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class SagaEvent {

	private String sagaId; // e.g. orderId as String
	private String step; // ORDER_CREATED, PAYMENT, INVENTORY, SHIPPING
	private String status; // STARTED, SUCCESS, FAILED, COMPENSATING
	private String details; // free text / reason
}
