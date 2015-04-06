package com.acme.oms.api;

/**
 * @author Allard Buijze
 */
public class OrderConfirmedEvent extends AbstractOrderEvent {

    public OrderConfirmedEvent(String orderId) {
    	super(orderId);
    }
}
