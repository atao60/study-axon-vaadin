package com.acme.oms.api;

/**
 * @author Allard Buijze
 */
public class OrderCancelledEvent extends AbstractOrderEvent {

	   public OrderCancelledEvent(String orderId) {
	    	super(orderId);
	    }
}
