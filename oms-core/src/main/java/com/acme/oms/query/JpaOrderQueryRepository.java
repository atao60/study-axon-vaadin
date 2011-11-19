package com.acme.oms.query;

import org.springframework.stereotype.Component;

import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 * @author Allard Buijze
 */
@Component("orderQueryRepository")
public class JpaOrderQueryRepository implements OrderQueryRepository {

    @PersistenceContext
    private EntityManager entityManager;

    public List<Order> findOrders() {
        return entityManager.createQuery("SELECT o FROM Order o").getResultList();
    }
}
