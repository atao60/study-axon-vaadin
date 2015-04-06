package com.acme.oms.ui.data;

import com.acme.oms.query.Order;
import com.acme.oms.query.OrderQueryRepository;
import com.vaadin.data.util.BeanItemContainer;

import java.io.Serializable;
import java.util.List;

/**
 * @author Mustafa Erdogan
 */
//@SuppressWarnings("serial")
public class OrderContainer extends BeanItemContainer<Order> implements Serializable {

	private static final long serialVersionUID = 1L;
	public static final Object[] NATURAL_COL_ORDER = new Object[]{"orderId", "productId", "status"};
    public static final String[] COL_HEADERS_ENGLISH = new String[]{"Order Id", "Product Id", "Status"};

    private OrderQueryRepository queryRepository;

    public OrderContainer(OrderQueryRepository queryRepository) throws IllegalArgumentException {
        super(Order.class);
        this.queryRepository = queryRepository;
    }

    public void refreshContent() {
        List<Order> allContacts = queryRepository.findOrders();
        removeAllItems();
        addAll(allContacts);
    }
}