package com.acme.oms.ui.view;

import com.acme.oms.ui.data.OrderContainer;
import com.vaadin.ui.Table;


/**
 * @author Mustafa Erdogan
 */
//@SuppressWarnings("serial")
public class OrderList extends Table {

	private static final long serialVersionUID = 1L;

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
