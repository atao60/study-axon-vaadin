package com.acme.oms.api;

import org.axonframework.domain.DomainEvent;

/**
 * @author Allard Buijze
 */
public abstract class AbstractOrderEvent extends DomainEvent {

    public String getOrderId() {
        return getAggregateIdentifier().asString();
    }

}
