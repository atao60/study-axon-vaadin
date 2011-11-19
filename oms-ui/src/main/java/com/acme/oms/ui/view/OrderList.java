package com.acme.oms.ui.view;

import com.acme.oms.query.Order;
import com.acme.oms.ui.data.OrderContainer;
import com.acme.oms.ui.data.OrderFormBean;
import com.vaadin.data.util.BeanItem;
import com.vaadin.event.ItemClickEvent;
import com.vaadin.event.ItemClickEvent.ItemClickListener;
import com.vaadin.ui.Table;


/**
 * @author Mustafa Erdogan
 */
@SuppressWarnings("serial")
public class OrderList extends Table {
    public OrderList(OrderContainer fromRepository) {
        setContainerDataSource(fromRepository);

        setVisibleColumns(OrderContainer.NATURAL_COL_ORDER);
        setColumnHeaders(OrderContainer.COL_HEADERS_ENGLISH);
        setSelectable(true);
        setImmediate(true);
        setNullSelectionAllowed(false);
        setSizeFull();
    }
}
