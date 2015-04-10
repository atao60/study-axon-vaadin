package com.acme.oms.api

import org.eclipse.xtend.lib.annotations.Data

@Data
class CancelOrderCommand {
    val String orderId
}