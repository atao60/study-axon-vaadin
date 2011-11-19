package com.acme.oms.commandhandling;

import com.acme.oms.api.OrderCancelledEvent;
import com.acme.oms.api.OrderConfirmedEvent;
import com.acme.oms.api.OrderCreatedEvent;
import org.axonframework.domain.AggregateIdentifier;
import org.axonframework.domain.StringAggregateIdentifier;
import org.axonframework.eventhandling.annotation.EventHandler;
import org.axonframework.eventsourcing.annotation.AbstractAnnotatedAggregateRoot;

/**
 * @author Allard Buijze
 */
class Order extends AbstractAnnotatedAggregateRoot {

    private static enum Status {

        OPEN, CANCELLED, CONFIRMED
    }

    private Status status;
    private String productId;

    public Order(String orderId, String productId) {
        super(new StringAggregateIdentifier(orderId));
        apply(new OrderCreatedEvent(productId));
    }

    Order(AggregateIdentifier identifier) {
        super(identifier);
    }

    public void confirm() {
// we can only confirm an open order.
        if (status == Status.OPEN) {
            apply(new OrderConfirmedEvent());
        }
    }

    public void cancel() {
//        don't raise an event if order is already cancelled
        if (status != Status.CANCELLED) {
            apply(new OrderCancelledEvent());
        }
    }

    @EventHandler
    private void onCreate(OrderCreatedEvent event) {
        status = Status.OPEN;
        productId = event.getProductId();
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
