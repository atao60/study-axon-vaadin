package com.acme.oms.api

class OrderCancelledEvent extends AbstractOrderEvent {
    new(String orderId) {
            super(orderId)
        }
}