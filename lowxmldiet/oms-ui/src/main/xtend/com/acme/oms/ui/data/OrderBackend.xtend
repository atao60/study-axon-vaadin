package com.acme.oms.ui.data

import com.acme.oms.query.JpaOrderQueryRepository
import com.acme.oms.query.Order
import com.acme.oms.query.OrderQueryRepository
import com.acme.oms.ui.common.data.Backend
import org.axonframework.commandhandling.CommandBus
import org.axonframework.commandhandling.gateway.CommandGateway
import org.axonframework.commandhandling.gateway.DefaultCommandGateway
import org.springframework.context.support.ClassPathXmlApplicationContext
import org.eclipse.xtend.lib.annotations.Accessors

/**
 * A simple wrapper of command and query persistence tools
 */
 @Accessors
class OrderBackend implements Backend<Order> {
    
    static val SPRING_CONTEXT_FILE = "META-INF/spring/application-context.xml"
    
    @Accessors(NONE)
    val OrderQueryRepository queryRepository
    val CommandGateway commandGateway
    val OrderContainer container

    new() {
        extension val appCtx = new ClassPathXmlApplicationContext(SPRING_CONTEXT_FILE)
        registerShutdownHook
        queryRepository = getBean(JpaOrderQueryRepository)
        container = new OrderContainer(queryRepository)
        val commandBus = getBean(CommandBus)
        commandGateway = new DefaultCommandGateway(commandBus)
    }
    
}