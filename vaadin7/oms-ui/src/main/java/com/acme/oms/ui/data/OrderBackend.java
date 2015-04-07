package com.acme.oms.ui.data;

import org.axonframework.commandhandling.CommandBus;
import org.axonframework.commandhandling.gateway.CommandGateway;
import org.axonframework.commandhandling.gateway.DefaultCommandGateway;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.acme.oms.query.JpaOrderQueryRepository;
import com.acme.oms.query.Order;
import com.acme.oms.query.OrderQueryRepository;
import com.acme.oms.ui.common.data.Backend;

/**
 * A simple wrapper of command and query persistence tools
 */
public class OrderBackend implements Backend<Order> {

	private static final String SPRING_CONTEXT_file = "META-INF/spring/application-context.xml";
	
	private final OrderQueryRepository queryRepository;
	private final CommandGateway commandGateway;
	private final OrderContainer container;

	public OrderBackend() {
        @SuppressWarnings("resource")
        ConfigurableApplicationContext appCtx = new ClassPathXmlApplicationContext(SPRING_CONTEXT_file);
        appCtx.registerShutdownHook();
        queryRepository = appCtx.getBean(JpaOrderQueryRepository.class);
        container = new OrderContainer(queryRepository);
        CommandBus commandBus = appCtx.getBean(CommandBus.class);
        this.commandGateway = new DefaultCommandGateway(commandBus);
	}
	
	@Override
    public OrderContainer getContainer() {
        return container;
    }
	
    @Override
	public CommandGateway getCommandGateway() {
		return commandGateway;
	}

}
