package com.acme.oms.api

import org.eclipse.xtend.lib.annotations.Data

@Data
abstract class AbstractOrderEvent {  
    val String orderId
}