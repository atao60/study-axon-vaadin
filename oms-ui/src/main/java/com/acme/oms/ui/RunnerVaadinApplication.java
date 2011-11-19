/*
 * Copyright 2009 IT Mill Ltd.
 *
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not
 * use this file except in compliance with the License. You may obtain a copy of
 * the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the
 * License for the specific language governing permissions and limitations under
 * the License.
 */
package com.acme.oms.ui;


import org.axonframework.commandhandling.CommandBus;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.acme.oms.query.JpaOrderQueryRepository;
import com.acme.oms.query.OrderQueryRepository;
import com.acme.oms.ui.data.OrderContainer;
import com.acme.oms.ui.view.OrderForm;
import com.acme.oms.ui.view.OrderList;
import com.acme.oms.ui.view.OrderListView;
import com.vaadin.Application;
import com.vaadin.ui.Window;

/**
 * The Application's "main" class
 */
@SuppressWarnings("serial")
public class RunnerVaadinApplication extends Application
{
    private Window window;
    
    private CommandBus commandBus;
    private OrderQueryRepository queryRepository;
    
    private OrderForm orderForm; 
    private OrderList orderList;
    private OrderListView orderListView;

    @Override
    public void init()
    {
        ApplicationContext appCtx = new ClassPathXmlApplicationContext("META-INF/spring/application-context.xml");
        queryRepository = appCtx.getBean(JpaOrderQueryRepository.class);
        commandBus = appCtx.getBean(CommandBus.class);
        
        window = new Window("Order Management System");
        
        orderForm = new OrderForm(commandBus);
        orderList = new OrderList(new OrderContainer(queryRepository));
        orderListView = new OrderListView(orderList, orderForm);
        
        window.addComponent(orderListView);
        window.getContent().setSizeFull();
        setMainWindow(window);
    }
    
    
}
