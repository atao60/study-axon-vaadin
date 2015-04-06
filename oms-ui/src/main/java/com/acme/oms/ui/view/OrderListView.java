package com.acme.oms.ui.view;

import com.acme.oms.ui.data.OrderContainer;
import com.vaadin.ui.HorizontalSplitPanel;
//import com.vaadin.ui.VerticalSplitPanel;

/**
 * @author Mustafa Erdogan
 */
@SuppressWarnings("serial")
public class OrderListView extends HorizontalSplitPanel {
    public OrderListView(OrderList orderList, OrderForm orderForm) {
		orderForm.setOrderContainer((OrderContainer)orderList.getContainerDataSource());
        setFirstComponent(orderForm);
        setSecondComponent(orderList);
        setSplitPosition(20);
    }
}