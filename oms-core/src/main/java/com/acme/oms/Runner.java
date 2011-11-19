package com.acme.oms;

import com.acme.oms.api.CancelOrderCommand;
import com.acme.oms.api.ConfirmOrderCommand;
import com.acme.oms.api.CreateOrderCommand;
import com.acme.oms.query.JpaOrderQueryRepository;
import com.acme.oms.query.Order;
import com.acme.oms.query.OrderQueryRepository;
import org.axonframework.commandhandling.CommandBus;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.util.List;

/**
 * @author Allard Buijze
 */
public class Runner {

    public static void main(String... args) {
        ApplicationContext appCtx = new ClassPathXmlApplicationContext("META-INF/spring/application-context.xml");
        OrderQueryRepository queryRepository = appCtx.getBean(JpaOrderQueryRepository.class);
        CommandBus commandBus = appCtx.getBean(CommandBus.class);

        commandBus.dispatch(new CreateOrderCommand("1", "Chair"));
        commandBus.dispatch(new CancelOrderCommand("1"));
        commandBus.dispatch(new ConfirmOrderCommand("1"));

        commandBus.dispatch(new CreateOrderCommand("2", "Table"));
        commandBus.dispatch(new ConfirmOrderCommand("2"));
        commandBus.dispatch(new CancelOrderCommand("2"));

        commandBus.dispatch(new CreateOrderCommand("3", "Lamp"));
        commandBus.dispatch(new ConfirmOrderCommand("3"));

        commandBus.dispatch(new CreateOrderCommand("4", "Sofa"));

        List<Order> orders = queryRepository.findOrders();
        for (Order order : orders) {
            System.out.println(String.format("Order [%s] for a [%s] has status: %s",
                                             order.getOrderId(),
                                             order.getProductId(),
                                             order.getStatus()));
        }
    }

}
