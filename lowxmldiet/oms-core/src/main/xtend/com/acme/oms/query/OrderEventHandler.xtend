package com.acme.oms.query

import org.springframework.stereotype.Component
import javax.persistence.PersistenceContext
import javax.persistence.EntityManager
import org.axonframework.eventhandling.annotation.EventHandler
import com.acme.oms.api.OrderCreatedEvent
import com.acme.oms.api.OrderCancelledEvent
import com.acme.oms.api.OrderConfirmedEvent

@Component
class OrderEventHandler {
	
    @PersistenceContext
    private EntityManager entityManager;

    @EventHandler
    def handleOrderCreated(extension OrderCreatedEvent event) {
        val order = new Order(orderId, productId)
        order.status = "We're working on your order"
        entityManager.persist(order)
    }

    @EventHandler
    def handleOrderCancelled(extension OrderCancelledEvent event) {
        val order = entityManager.find(Order, orderId)
        order.status = "Your order was cancelled"
    }

    @EventHandler
    def handleOrderConfirmed(extension OrderConfirmedEvent event) {
        val order = entityManager.find(Order, orderId)
        order.status = "Your order was confirmed. You'll get it soon."
    }
}