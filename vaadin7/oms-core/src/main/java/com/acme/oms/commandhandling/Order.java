package com.acme.oms.commandhandling;

import com.acme.oms.api.OrderCancelledEvent;
import com.acme.oms.api.OrderConfirmedEvent;
import com.acme.oms.api.OrderCreatedEvent;

import org.axonframework.eventsourcing.annotation.AbstractAnnotatedAggregateRoot;
import org.axonframework.eventsourcing.annotation.AggregateIdentifier;
import org.axonframework.eventhandling.annotation.EventHandler;

/**
 * @author Allard Buijze
 */
class Order extends AbstractAnnotatedAggregateRoot<String> {

	private static final long serialVersionUID = 1L;

	private static enum Status {

        OPEN, CANCELLED, CONFIRMED
    }

    @AggregateIdentifier
    private String aggregateIdentifier;

    private Status status;
//    private String productId;

    // constructor required for reconstruction
    protected Order() {
    }
    
    public Order(String orderId, String productId) {
        apply(new OrderCreatedEvent(orderId, productId));
    }

    public String getIdentifier() {
        return aggregateIdentifier;
    }
    
    public void confirm() {
    	// we can only confirm an open order.
        if (status == Status.OPEN) {
            apply(new OrderConfirmedEvent(getIdentifier()));
        }
    }

    public void cancel() {
    	// don't raise an event if order is already cancelled
        if (status != Status.CANCELLED) {
            apply(new OrderCancelledEvent(getIdentifier()));
        }
    }

    @EventHandler
    private void onCreate(OrderCreatedEvent event) {
        status = Status.OPEN;
        aggregateIdentifier = event.getOrderId();
//        productId = event.getProductId();
    }

    @EventHandler
    private void onConfirm(OrderConfirmedEvent event) {
        status = Status.CONFIRMED;
    }

    @EventHandler
    private void onCancel(OrderCancelledEvent event) {
        status = Status.CANCELLED;
    }
}
