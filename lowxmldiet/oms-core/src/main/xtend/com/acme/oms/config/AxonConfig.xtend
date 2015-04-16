package com.acme.oms.config

import com.acme.oms.commandhandling.Order
import javax.persistence.EntityManager
import javax.persistence.PersistenceContext
import org.axonframework.commandhandling.SimpleCommandBus
import org.axonframework.commandhandling.annotation.AggregateAnnotationCommandHandler
import org.axonframework.commandhandling.annotation.AnnotationCommandHandlerBeanPostProcessor
import org.axonframework.common.jpa.ContainerManagedEntityManagerProvider
import org.axonframework.eventhandling.SimpleEventBus
import org.axonframework.eventhandling.annotation.AnnotationEventListenerBeanPostProcessor
import org.axonframework.eventsourcing.EventSourcingRepository
import org.axonframework.eventstore.jpa.JpaEventStore
import org.axonframework.unitofwork.SpringTransactionManager
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.transaction.PlatformTransactionManager

@Configuration
class AxonConfig {
    
    @Autowired var PlatformTransactionManager transactionManager
    @PersistenceContext var EntityManager entityManager
    
    /* <axon:annotation-config /> */
    @Bean
    def annotationEventListenerBeanPostProcessor() {
        val listener = new AnnotationEventListenerBeanPostProcessor
        listener.eventBus = eventBus
        return listener
    }
    
    @Bean
    def annotationCommandHandlerBeanPostProcessor() {
        val handler = new AnnotationCommandHandlerBeanPostProcessor
        handler.commandBus = commandBus
        return handler
    }
    
    @Bean
    def aggregateAnnotationCommandHandler() {
        return new AggregateAnnotationCommandHandler<Order>(Order, orderRepository) 
    }
     
    /*
        <axon:event-sourcing-repository id="orderRepository"
            aggregate-type="com.acme.oms.commandhandling.Order" />
    */
    @Bean
    def orderRepository() {
        val repository = new EventSourcingRepository<Order>(Order, eventStore)
        repository.eventBus = eventBus
        return repository
    }

    // <axon:jpa-event-store id="eventStore" />
    @Bean
    def eventStore() {
        val entityManagerProvider = new ContainerManagedEntityManagerProvider
        entityManagerProvider.entityManager = entityManager
        return new JpaEventStore(entityManagerProvider)
    }

    // <axon:event-bus id="eventBus" />
    @Bean
    def eventBus() {
        return new SimpleEventBus
    }

    // <axon:command-bus id="commandBus"/>
    @Bean
    def commandBus() {
        val commandBus = new SimpleCommandBus
        commandBus.transactionManager = new SpringTransactionManager(transactionManager)
        return commandBus;
    }

}