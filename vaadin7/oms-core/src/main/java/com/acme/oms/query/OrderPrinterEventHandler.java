package com.acme.oms.query;

import com.acme.oms.api.OrderCancelledEvent;
import com.acme.oms.api.OrderConfirmedEvent;
import com.acme.oms.api.OrderCreatedEvent;
import org.axonframework.eventhandling.annotation.EventHandler;
import org.springframework.stereotype.Component;

/**
 * @author Allard Buijze
 */
@Component
public class OrderPrinterEventHandler {

    @EventHandler
    public void handleOrderCreated(OrderCreatedEvent event) {
        System.out.println(String.format("An order is created: [%s] for product: [%s]",
                                         event.getOrderId(),
                                         event.getProductId()));
    }

    @EventHandler
    public void handleOrderCancelled(OrderCancelledEvent event) {
        System.out.println(String.format("An order is cancelled: [%s]", event.getOrderId()));
    }
    
    @EventHandler
    public void handleOrderConfirmed(OrderConfirmedEvent event) {
        System.out.println(String.format("An order is confirmed: [%s]", event.getOrderId()));
    }
}
