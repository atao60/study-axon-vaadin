package com.acme.oms.commandhandling;

import com.acme.oms.api.ConfirmOrderCommand;
import com.acme.oms.api.CreateOrderCommand;
import com.acme.oms.api.OrderCancelledEvent;
import com.acme.oms.api.OrderConfirmedEvent;
import com.acme.oms.api.OrderCreatedEvent;
import org.axonframework.test.FixtureConfiguration;
import org.axonframework.test.Fixtures;
import org.junit.*;

/**
 * @author Allard Buijze
 */
public class OrderCommandHandlerTest {

    private FixtureConfiguration<Order> fixture;

    @Before
    public void setUp() throws Exception {
        fixture = Fixtures.newGivenWhenThenFixture(Order.class);
        OrderCommandHandler commandHandler = new OrderCommandHandler();
        commandHandler.setOrderRepository(fixture.getRepository());
        fixture.registerAnnotatedCommandHandler(commandHandler);
    }

    @Test
    public void testCreateOrder() {
        fixture.given()
                .when(new CreateOrderCommand("123", "Chair1"))
                .expectEvents(new OrderCreatedEvent("123", "Chair1"));
    }

    @Test
    public void testConfirmOrder_Open() {
        fixture.given(new OrderCreatedEvent("123", "Chair1"))
                .when(new ConfirmOrderCommand("123"))
                .expectEvents(new OrderConfirmedEvent("123"));
    }

    @Test
    public void testConfirmOrder_WasAlreadyCancelled() {
        fixture.given(new OrderCreatedEvent("123", "Chair1"), new OrderCancelledEvent("123"))
                .when(new ConfirmOrderCommand("123"))
                .expectEvents();
    }
}
