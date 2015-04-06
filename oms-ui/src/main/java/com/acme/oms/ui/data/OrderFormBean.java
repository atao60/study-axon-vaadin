package com.acme.oms.ui.data;

import java.io.Serializable;

//@SuppressWarnings("serial")
public class OrderFormBean implements Serializable {
 
	private static final long serialVersionUID = 1L;
	private String orderId;
    private String productId;
    private String status;

    public OrderFormBean() {
    	this(null, null, null);
    }

    public OrderFormBean(String orderId, String productId, String status) {
        this.orderId = orderId;
        this.productId = productId;
        this.status = status;
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
