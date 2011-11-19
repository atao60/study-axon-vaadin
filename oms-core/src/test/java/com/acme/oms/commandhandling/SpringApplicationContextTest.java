package com.acme.oms.commandhandling;

import org.junit.*;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import static org.junit.Assert.assertNotNull;

/**
 * @author Allard Buijze
 */
public class SpringApplicationContextTest {

    @Test
    public void testSpringContextStarts() {
        ApplicationContext appCtx = new ClassPathXmlApplicationContext("META-INF/spring/application-context.xml");
        assertNotNull(appCtx.getBean("orderRepository"));
    }

}
