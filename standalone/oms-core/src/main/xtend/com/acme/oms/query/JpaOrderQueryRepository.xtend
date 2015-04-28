package com.acme.oms.query

import java.util.List
import javax.persistence.EntityManager
import javax.persistence.PersistenceContext
import org.springframework.stereotype.Component

import static java.lang.String.format

@Component("orderQueryRepository")
class JpaOrderQueryRepository implements OrderQueryRepository {
	
	static val String SELECT_QUERY = "SELECT o FROM %s o";

    @PersistenceContext
    extension EntityManager entityManager

    override List<Order> findOrders() {
    	createQuery(format(SELECT_QUERY, Order.simpleName), Order).resultList
    }
}