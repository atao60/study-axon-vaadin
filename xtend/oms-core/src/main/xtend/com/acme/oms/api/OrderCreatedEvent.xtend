package com.acme.oms.api

import org.eclipse.xtend.lib.annotations.Data

@Data
class OrderCreatedEvent extends AbstractOrderEvent {
    val String productId
}