package com.acme.oms.config

import static org.hamcrest.CoreMatchers.*
import static org.junit.Assert.assertThat

import javax.annotation.Resource
import org.axonframework.eventsourcing.EventSourcingRepository
import org.junit.Test
import org.junit.runner.RunWith
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.core.env.Environment
import org.springframework.test.context.ContextConfiguration
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner
import org.springframework.test.context.support.AnnotationConfigContextLoader
import org.springframework.transaction.PlatformTransactionManager
import com.acme.oms.commandhandling.Order

@RunWith(SpringJUnit4ClassRunner)
@ContextConfiguration(classes=MainConfig, loader=AnnotationConfigContextLoader)
class RootAppConfigTest {
  
    @Resource
    var Environment environment
    
    @Autowired
    package var PlatformTransactionManager transactionManager

    @Autowired
    package var EventSourcingRepository<Order> orderRepository
    
    @Test
    def testPropertiesAreAvailable() { 
        val driverClassName = environment.getProperty("dataSource.driverClassName")
        assertThat(driverClassName,is(notNullValue))
        assertThat(driverClassName.length,is(not(0)))
        assertThat(driverClassName, is("org.hsqldb.jdbcDriver"))
    }
    
    @Test
    def testPersistenceContextIsAvailable() {
        assertThat(transactionManager,is(notNullValue))
    }

    @Test
    def testAxonContextIsAvailable() {
        assertThat(orderRepository,is(notNullValue))
    }
    
}