package com.acme.oms.api;

/**
 * @author Allard Buijze
 */
public class CreateOrderCommand {

    private final String orderId;
    private final String productId;

    public CreateOrderCommand(String orderId, String productId) {
        this.orderId = orderId;
        this.productId = productId;
    }

    public String getOrderId() {
        return orderId;
    }

    public String getProductId() {
        return productId;
    }
}
