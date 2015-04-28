package com.acme.oms.commandhandling

import org.axonframework.eventsourcing.annotation.AbstractAnnotatedAggregateRoot
import org.axonframework.eventsourcing.annotation.AggregateIdentifier
import org.axonframework.eventhandling.annotation.EventHandler
import org.eclipse.xtend.lib.annotations.Accessors
import com.acme.oms.api.OrderCreatedEvent
import com.acme.oms.api.OrderConfirmedEvent
import com.acme.oms.api.OrderCancelledEvent

class Order extends AbstractAnnotatedAggregateRoot<String> {

    private enum Status {

        OPEN, CANCELLED, CONFIRMED
    }
    
    @AggregateIdentifier
    @Accessors(PUBLIC_GETTER)
    var String aggregateIdentifier
    var Status status
    
    // constructor required for reconstruction
    protected new() {
    }
    
    new(String orderId, String productId) {
        new OrderCreatedEvent(orderId, productId).apply
    }
    
    
    def confirm() {
        // we can only confirm an open order.
        if (status === Status.OPEN) {
            new OrderConfirmedEvent(getAggregateIdentifier).apply
        }
    }

    def cancel() {
        // don't raise an event if order is already cancelled
        if (status !== Status.CANCELLED) {
            new OrderCancelledEvent(getAggregateIdentifier).apply
        }
    }

    @EventHandler
    def private void onCreate(OrderCreatedEvent event) {
        status = Status.OPEN
        aggregateIdentifier = event.orderId
    }

    @EventHandler
    def private void onConfirm(OrderConfirmedEvent event) {
        status = Status.CONFIRMED
    }

    @EventHandler
    def private void onCancel(OrderCancelledEvent event) {
        status = Status.CANCELLED
    }    
    
}