package com.acme.oms.api

import org.eclipse.xtend.lib.annotations.Data

@Data
class CreateOrderCommand {
    val String orderId
    val String productId
}