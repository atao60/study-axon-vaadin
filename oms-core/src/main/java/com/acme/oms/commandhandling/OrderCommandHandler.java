package com.acme.oms.commandhandling;

import com.acme.oms.api.CancelOrderCommand;
import com.acme.oms.api.ConfirmOrderCommand;
import com.acme.oms.api.CreateOrderCommand;
import org.axonframework.commandhandling.annotation.CommandHandler;
import org.axonframework.domain.StringAggregateIdentifier;
import org.axonframework.repository.Repository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * @author Allard Buijze
 */
@Component
public class OrderCommandHandler {

    private Repository<Order> orderRepository;

    @CommandHandler
    public void createOrder(CreateOrderCommand command) {
        orderRepository.add(new Order(command.getOrderId(), command.getProductId()));
    }

    @CommandHandler
    public void confirmOrder(ConfirmOrderCommand command) {
        Order order = orderRepository.load(new StringAggregateIdentifier(command.getOrderId()));
        order.confirm();
    }

    @CommandHandler
    public void cancelOrder(CancelOrderCommand command) {
        Order order = orderRepository.load(new StringAggregateIdentifier(command.getOrderId()));
        order.cancel();
    }

    @Autowired
    public void setOrderRepository(Repository<Order> orderRepository) {
        this.orderRepository = orderRepository;
    }
}
