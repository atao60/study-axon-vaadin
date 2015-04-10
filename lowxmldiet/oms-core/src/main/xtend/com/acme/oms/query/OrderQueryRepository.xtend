package com.acme.oms.query

import java.util.List

interface OrderQueryRepository {
	def List<Order> findOrders()
}