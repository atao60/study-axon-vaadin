package com.acme.oms.commandhandling

import org.junit.Before
import org.junit.Test
import org.axonframework.test.Fixtures
import org.axonframework.test.FixtureConfiguration
import com.acme.oms.api.CreateOrderCommand
import com.acme.oms.api.OrderCreatedEvent
import com.acme.oms.api.OrderConfirmedEvent
import com.acme.oms.api.ConfirmOrderCommand
import com.acme.oms.api.OrderCancelledEvent

class OrderCommandHandlerTest {
    
    var FixtureConfiguration<Order> fixture

    @Before
    def void setUp() throws Exception {
        fixture = Fixtures::newGivenWhenThenFixture(Order)
        val commandHandler = new OrderCommandHandler
        commandHandler.orderRepository = fixture.repository
        fixture.registerAnnotatedCommandHandler(commandHandler)
    }

    @Test
    def void testCreateOrder() {
        fixture.given
                .when(new CreateOrderCommand("123", "Chair1"))
                .expectEvents(new OrderCreatedEvent("123", "Chair1"))
    }

    @Test
    def void testConfirmOrder_Open() {
        fixture.given(new OrderCreatedEvent("123", "Chair1"))
                .when(new ConfirmOrderCommand("123"))
                .expectEvents(new OrderConfirmedEvent("123"))
    }

    @Test
    def void testConfirmOrder_WasAlreadyCancelled() {
        fixture.given(new OrderCreatedEvent("123", "Chair1"), new OrderCancelledEvent("123"))
                .when(new ConfirmOrderCommand("123"))
                .expectEvents
    }
}