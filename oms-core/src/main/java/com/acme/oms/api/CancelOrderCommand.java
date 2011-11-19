package com.acme.oms.api;

/**
 * @author Allard Buijze
 */
public class CancelOrderCommand {

    private String orderId;

    public CancelOrderCommand(String orderId) {
        this.orderId = orderId;
    }

    public String getOrderId() {
        return orderId;
    }
}
