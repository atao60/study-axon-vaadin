package com.acme.oms;

import java.util.List;

import org.axonframework.commandhandling.CommandBus;
import org.axonframework.commandhandling.gateway.CommandGateway;
import org.axonframework.commandhandling.gateway.DefaultCommandGateway;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.acme.oms.api.CancelOrderCommand;
import com.acme.oms.api.ConfirmOrderCommand;
import com.acme.oms.api.CreateOrderCommand;
import com.acme.oms.query.JpaOrderQueryRepository;
import com.acme.oms.query.Order;
import com.acme.oms.query.OrderQueryRepository;

/**
 * @author Allard Buijze
 */
public class Runner {

    public static void main(String... args) {
        @SuppressWarnings("resource")
        ConfigurableApplicationContext appCtx = new ClassPathXmlApplicationContext("META-INF/spring/application-context.xml");
        appCtx.registerShutdownHook();
        OrderQueryRepository queryRepository = appCtx.getBean(JpaOrderQueryRepository.class);
        CommandBus commandBus = appCtx.getBean(CommandBus.class);
        
        CommandGateway commandGateway = new DefaultCommandGateway(commandBus);

        commandGateway.send(new CreateOrderCommand("1", "Chair"));
        commandGateway.send(new CancelOrderCommand("1"));
        commandGateway.send(new ConfirmOrderCommand("1"));

        commandGateway.send(new CreateOrderCommand("2", "Table"));
        commandGateway.send(new ConfirmOrderCommand("2"));
        commandGateway.send(new CancelOrderCommand("2"));

        commandGateway.send(new CreateOrderCommand("3", "Lamp"));
        commandGateway.send(new ConfirmOrderCommand("3"));

        commandGateway.send(new CreateOrderCommand("4", "Sofa"));

        List<Order> orders = queryRepository.findOrders();
        for (Order order : orders) {
            System.out.println(String.format("Order [%s] for a [%s] has status: %s",
                                             order.getOrderId(),
                                             order.getProductId(),
                                             order.getStatus()));
        }
        
    }

}
