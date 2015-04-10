package com.acme.oms.commandhandling

import org.junit.Test
import org.springframework.context.support.ClassPathXmlApplicationContext

import static org.junit.Assert.assertNotNull

class SpringApplicationContextTest {
    
    @Test
    def testSpringContextStarts() {
        val appCtx = new ClassPathXmlApplicationContext("META-INF/spring/application-context.xml")
        assertNotNull(appCtx.getBean("orderRepository"))
        appCtx.close
    }
    
}