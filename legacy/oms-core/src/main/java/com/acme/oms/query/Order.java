package com.acme.oms.query;

import javax.persistence.Basic;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * @author Allard Buijze
 */
@Entity
@Table(name = "Orders")
public class Order {

    @Id
    private String orderId;

    @Basic
    private String productId;

    @Basic
    private String status;

    public Order(String orderId, String productId) {
        this.orderId = orderId;
        this.productId = productId;
    }

    Order() {
        // needed by JPA
    }

    public String getOrderId() {
        return orderId;
    }

    public String getProductId() {
        return productId;
    }

    public String getStatus() {
        return status;
    }

    void setStatus(String status) {
        this.status = status;
    }
}
