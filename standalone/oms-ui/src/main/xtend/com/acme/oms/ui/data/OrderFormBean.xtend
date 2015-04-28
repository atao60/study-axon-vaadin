package com.acme.oms.ui.data

import java.io.Serializable
import com.acme.oms.query.Order
import org.eclipse.xtend.lib.annotations.Accessors

@Accessors
class OrderFormBean implements Serializable {

    val static EMPTY_STRING = ""

    var String orderId
    var String productId
    var String status

    new() {
        this(EMPTY_STRING, EMPTY_STRING, EMPTY_STRING)
    }

    new(Order order) {
        this();
        if(order === null) return;

        this.orderId = order.orderId
        this.productId = order.productId
        this.status = order.status

    }

    new(String orderId, String productId, String status) {
        this.orderId = orderId ?: EMPTY_STRING
        this.productId = productId ?: EMPTY_STRING
        this.status = status ?: EMPTY_STRING
    }

}
