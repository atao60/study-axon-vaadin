package com.acme.oms.ui.view

import com.acme.oms.ui.common.view.Binder
import com.acme.oms.query.Order
import com.acme.oms.ui.data.OrderFormBean
import com.acme.oms.ui.common.view.EntityFormBeanFactory

class OrderBinder extends Binder<Order, OrderFormBean> {

    new() {
        super(new EntityFormBeanFactory<OrderFormBean>() {
            override newBean() {
                return new OrderFormBean
            }})
    }
    
    override bindEntity(Order order) {
        bind(new OrderFormBean(order))
    }
    
}