package com.acme.oms.query

import javax.persistence.Entity
import javax.persistence.Id
import javax.persistence.Table
import org.eclipse.xtend.lib.annotations.Accessors

@Entity
@Table(name = "Orders")
@Accessors(PUBLIC_GETTER)
class Order {
	@Id
	var String orderId
	var String productId
	@Accessors(PUBLIC_GETTER, PACKAGE_SETTER)
	var String status
	
	// required by JPA
	package new() {}
	
	new(String orderId, String productId) {
        this.orderId = orderId
        this.productId = productId
    }
	
}