package com.acme.oms.ui.data;

import java.io.Serializable;

import com.acme.oms.query.Order;

public class OrderFormBean implements Serializable {
 
	private static final long serialVersionUID = 1L;
	
	private static final String EMPTY_STRING = "";
	
	private String orderId;
    private String productId;
    private String status;

    public OrderFormBean() {
    	this(EMPTY_STRING, EMPTY_STRING, EMPTY_STRING);
    }

    public OrderFormBean(Order order) {
        this();
        if (order != null) { 
            this.orderId = order.getOrderId();
            this.productId = order.getProductId();
            this.status = order.getStatus();
        } 
    }

    public OrderFormBean(String orderId, String productId, String status) {
        this.orderId = orderId == null ? EMPTY_STRING : orderId;
        this.productId = productId == null ? EMPTY_STRING : productId;
        this.status = status == null ? EMPTY_STRING : status;
    }
    
	public String getOrderId() {
		return orderId;
	}

	public void setOrderId(String orderId) {
		this.orderId = orderId;
	}

	public String getProductId() {
		return productId;
	}

	public void setProductId(String productId) {
		this.productId = productId;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}
}
