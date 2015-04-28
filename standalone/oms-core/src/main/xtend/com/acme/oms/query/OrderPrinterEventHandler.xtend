package com.acme.oms.query

import static java.lang.String.format

import org.springframework.stereotype.Component
import org.axonframework.eventhandling.annotation.EventHandler
import com.acme.oms.api.OrderCreatedEvent
import com.acme.oms.api.OrderCancelledEvent
import com.acme.oms.api.OrderConfirmedEvent

@Component
class OrderPrinterEventHandler {

    @EventHandler
    def void handleOrderCreated(OrderCreatedEvent event) {
        println(format("An order is created: [%s] for product: [%s]",
                                         event.orderId,
                                         event.productId))
    }

    @EventHandler
    def void handleOrderCancelled(OrderCancelledEvent event) {
        println(format("An order is cancelled: [%s]", event.orderId))
    }
    
    @EventHandler
    def void handleOrderConfirmed(OrderConfirmedEvent event) {
        println(format("An order is confirmed: [%s]", event.orderId))
    }
	
}