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

    private FixtureConfiguration fixture;

    @Before
    public void setUp() throws Exception {
        fixture = Fixtures.newGivenWhenThenFixture();
        OrderCommandHandler commandHandler = new OrderCommandHandler();
        commandHandler.setOrderRepository(fixture.createGenericRepository(Order.class));
        fixture.registerAnnotatedCommandHandler(commandHandler);
    }

    @Test
    public void testCreateOrder() {
        fixture.given()
                .when(new CreateOrderCommand("123", "Chair1"))
                .expectEvents(new OrderCreatedEvent("Chair1"));
    }

    @Test
    public void testConfirmOrder_Open() {
        fixture.given(new OrderCreatedEvent("Chair1"))
                .when(new ConfirmOrderCommand(fixture.getAggregateIdentifier().asString()))
                .expectEvents(new OrderConfirmedEvent());
    }

    @Test
    public void testConfirmOrder_WasAlreadyCancelled() {
        fixture.given(new OrderCreatedEvent("Chair1"), new OrderCancelledEvent())
                .when(new ConfirmOrderCommand(fixture.getAggregateIdentifier().asString()))
                .expectEvents();
    }
}
