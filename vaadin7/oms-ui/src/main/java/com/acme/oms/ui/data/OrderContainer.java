package com.acme.oms.ui.data;

import com.acme.oms.query.Order;
import com.acme.oms.query.OrderQueryRepository;
import com.vaadin.data.util.BeanItemContainer;

import java.io.Serializable;
import java.util.List;

/**
 * @author Mustafa Erdogan
 */
public class OrderContainer extends BeanItemContainer<Order> implements Serializable {

	private static final long serialVersionUID = 1L;

	public enum DataDescriptor {orderId("Order Id"), productId("Product Id"), status("Status");
	final private String label;
	private DataDescriptor(String label) {
		this.label = label;
	}
	public static String[] names() {
		String[] names = new String[values().length];
		for(int i = 0; i < values().length;i++) {
			names[i] = values()[i].name();
		}
		return names;
	}
	public static String[] labels() {
		String[] labels = new String[values().length];
		for(int i = 0; i < values().length;i++) {
			labels[i] = values()[i].label;
		}
		return labels;
	}
	public String label() {
		return label;
	}
	}

    private OrderQueryRepository queryRepository;

    public OrderContainer(OrderQueryRepository queryRepository) {
        super(Order.class);
        this.queryRepository = queryRepository;
    }

    public OrderContainer refreshContent() {
        List<Order> allContacts = queryRepository.findOrders();
        removeAllItems();
        addAll(allContacts);
        return this;
    }
}