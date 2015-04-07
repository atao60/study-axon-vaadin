package com.acme.oms.query;

import static java.lang.String.format;

import org.springframework.stereotype.Component;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

/**
 * @author Allard Buijze
 */
@Component("orderQueryRepository")
public class JpaOrderQueryRepository implements OrderQueryRepository {
	
	private static final String SELECT_QUERY = "SELECT o FROM %s o";

    @PersistenceContext
    private EntityManager entityManager;

    public List<Order> findOrders() {
    	TypedQuery<Order> query = 
    			entityManager.createQuery(format(SELECT_QUERY, Order.class.getSimpleName()), Order.class);
        return query.getResultList();
    }
}
