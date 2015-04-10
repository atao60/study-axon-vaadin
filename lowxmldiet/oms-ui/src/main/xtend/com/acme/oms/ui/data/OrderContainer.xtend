package com.acme.oms.ui.data

import com.acme.oms.query.Order
import com.acme.oms.query.OrderQueryRepository
import com.vaadin.data.util.BeanItemContainer
import java.io.Serializable

class OrderContainer extends BeanItemContainer<Order> implements Serializable {

    val OrderQueryRepository queryRepository

    new(OrderQueryRepository queryRepository) {
        super(Order)
        this.queryRepository = queryRepository
    }

    def refreshContent() {
        val allContacts = queryRepository.findOrders
        removeAllItems
        addAll(allContacts)
        return this
    }
}
