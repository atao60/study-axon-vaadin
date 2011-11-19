package com.acme.oms.api;

/**
 * @author Allard Buijze
 */
public class OrderCreatedEvent extends AbstractOrderEvent {

    private final String productId;

    public OrderCreatedEvent(String productId) {
        this.productId = productId;
    }

    public String getProductId() {
        return productId;
    }
}
