package com.acme.oms

import static java.lang.String.format

import com.acme.oms.api.CancelOrderCommand
import com.acme.oms.api.ConfirmOrderCommand
import com.acme.oms.api.CreateOrderCommand
import com.acme.oms.query.JpaOrderQueryRepository
import com.acme.oms.query.Order
import org.axonframework.commandhandling.CommandBus
import org.axonframework.commandhandling.gateway.DefaultCommandGateway
import org.springframework.context.annotation.AnnotationConfigApplicationContext
import com.acme.oms.config.MainConfig

class OrderAppRunner {

    val static ORDER_WITH_PRODUCT_QUERY = "Order [%s] for a [%s] has status: %s"

    def static main(String... args) {

        val appCtx = new AnnotationConfigApplicationContext(MainConfig)
        appCtx.registerShutdownHook
        val queryRepository = appCtx.getBean(JpaOrderQueryRepository)
        val commandBus = appCtx.getBean(CommandBus)

        extension val commandGateway = new DefaultCommandGateway(commandBus)

        send(new CreateOrderCommand("1", "Chair"))
        send(new CancelOrderCommand("1"))
        send(new ConfirmOrderCommand("1"))

        send(new CreateOrderCommand("2", "Table"))
        send(new ConfirmOrderCommand("2"))
        send(new CancelOrderCommand("2"))

        send(new CreateOrderCommand("3", "Lamp"))
        send(new ConfirmOrderCommand("3"))

        send(new CreateOrderCommand("4", "Sofa"))

        val orders = queryRepository.findOrders
        for (extension Order order : orders) {
            println(format(ORDER_WITH_PRODUCT_QUERY, orderId, productId, status))
        }

    }

}
