package com.acme.oms.ui.view;

import com.acme.oms.query.Order;
import com.acme.oms.ui.common.view.Binder;
import com.acme.oms.ui.common.view.EntityFormBeanFactory;
import com.acme.oms.ui.data.OrderFormBean;

public class OrderBinder extends Binder<Order, OrderFormBean> {

	public OrderBinder() {
		super(new EntityFormBeanFactory<OrderFormBean>() {
			@Override
			public OrderFormBean newBean() {
				return new OrderFormBean();
			}});
	}
	
	@Override
	public void bindEntity(Order order) {
	    bind(new OrderFormBean(order));
	}

}