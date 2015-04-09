package com.acme.oms

import static java.lang.String.format

import com.acme.oms.api.CancelOrderCommand
import com.acme.oms.api.ConfirmOrderCommand
import com.acme.oms.api.CreateOrderCommand
import com.acme.oms.query.JpaOrderQueryRepository
import com.acme.oms.query.Order
import org.axonframework.commandhandling.CommandBus
import org.axonframework.commandhandling.gateway.DefaultCommandGateway
import org.springframework.context.support.ClassPathXmlApplicationContext

class OrderAppRunner {

    val static CONTEXT_FILE_PATH = "META-INF/spring/application-context.xml"
    val static ORDER_WITH_PRODUCT_QUERY = "Order [%s] for a [%s] has status: %s"

    def static main(String... args) {

        val appCtx = new ClassPathXmlApplicationContext(CONTEXT_FILE_PATH)
        appCtx.registerShutdownHook
        val queryRepository = appCtx.getBean(JpaOrderQueryRepository)
        val commandBus = appCtx.getBean(CommandBus)

        val commandGateway = new DefaultCommandGateway(commandBus)

        commandGateway.send(new CreateOrderCommand("1", "Chair"))
        commandGateway.send(new CancelOrderCommand("1"))
        commandGateway.send(new ConfirmOrderCommand("1"))

        commandGateway.send(new CreateOrderCommand("2", "Table"))
        commandGateway.send(new ConfirmOrderCommand("2"))
        commandGateway.send(new CancelOrderCommand("2"))

        commandGateway.send(new CreateOrderCommand("3", "Lamp"))
        commandGateway.send(new ConfirmOrderCommand("3"))

        commandGateway.send(new CreateOrderCommand("4", "Sofa"))

        val orders = queryRepository.findOrders
        for (extension Order order : orders) {
            println(format(ORDER_WITH_PRODUCT_QUERY, orderId, productId, status));
        }

    }

}
