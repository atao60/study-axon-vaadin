package com.acme.oms.api

import org.eclipse.xtend.lib.annotations.Data

@Data
class ConfirmOrderCommand {
    val String orderId
}