package com.acme.oms.api;

/**
 * @author Allard Buijze
 */
public class ConfirmOrderCommand {

    private String orderId;

    public ConfirmOrderCommand(String orderId) {
        this.orderId = orderId;
    }

    public String getOrderId() {
        return orderId;
    }
}
