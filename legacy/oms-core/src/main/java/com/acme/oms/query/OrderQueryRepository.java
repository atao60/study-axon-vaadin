package com.acme.oms.query;

import java.util.List;

/**
 * @author Allard Buijze
 */
public interface OrderQueryRepository {

    List<Order> findOrders();
}
