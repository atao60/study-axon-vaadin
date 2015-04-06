package com.acme.oms.query;

import com.acme.oms.api.OrderCancelledEvent;
import com.acme.oms.api.OrderConfirmedEvent;
import com.acme.oms.api.OrderCreatedEvent;
import org.axonframework.eventhandling.annotation.EventHandler;
import org.springframework.stereotype.Component;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 * @author Allard Buijze
 */
@Component
public class OrderEventHandler {

    @PersistenceContext
    private EntityManager entityManager;

    @EventHandler
    public void handleOrderCreated(OrderCreatedEvent event) {
        Order order = new Order(event.getOrderId(), event.getProductId());
        order.setStatus("We're working on your order");
        entityManager.persist(order);
    }

    @EventHandler
    public void handleOrderCancelled(OrderCancelledEvent event) {
        Order order = entityManager.find(Order.class, event.getOrderId());
        order.setStatus("Your order was cancelled");
    }

    @EventHandler
    public void handleOrderConfirmed(OrderConfirmedEvent event) {
        Order order = entityManager.find(Order.class, event.getOrderId());
        order.setStatus("Your order was confirmed. You'll get it soon.");
    }
}
