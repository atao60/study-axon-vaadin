package com.acme.oms.api;

/**
 * @author Allard Buijze
 */
public abstract class AbstractOrderEvent {

   private final String orderId;
    
    public AbstractOrderEvent(String orderId) {
    	this.orderId = orderId;
    }

    public String getOrderId() {
        return orderId;
    }
}
