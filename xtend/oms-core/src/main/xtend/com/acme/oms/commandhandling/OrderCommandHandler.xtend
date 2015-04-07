package com.acme.oms.commandhandling

import org.axonframework.repository.Repository
import org.springframework.stereotype.Component
import org.axonframework.commandhandling.annotation.CommandHandler
import com.acme.oms.api.CreateOrderCommand
import com.acme.oms.api.ConfirmOrderCommand
import com.acme.oms.api.CancelOrderCommand
import org.springframework.beans.factory.annotation.Autowired

@Component
class OrderCommandHandler {
    var Repository<Order> orderRepository
    
    @CommandHandler
    def createOrder(CreateOrderCommand command) {
        orderRepository.add(new Order(command.orderId, command.productId));
    }

    @CommandHandler
    def confirmOrder(ConfirmOrderCommand command) {
        val order = orderRepository.load(command.orderId)
        order.confirm
    }

    @CommandHandler
    def cancelOrder(CancelOrderCommand command) {
        val order = orderRepository.load(command.orderId)
        order.cancel
    }

    @Autowired
    def setOrderRepository(Repository<Order> orderRepository) {
        this.orderRepository = orderRepository
        return
    }
}