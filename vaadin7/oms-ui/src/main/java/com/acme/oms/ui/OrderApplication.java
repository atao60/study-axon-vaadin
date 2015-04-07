package com.acme.oms.ui;


import com.acme.oms.ui.data.OrderBackend;
import com.acme.oms.ui.view.OrderManagerView;
import com.vaadin.server.VaadinRequest;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.UI;

/**
 * The Application's "main" class
 */
@SuppressWarnings("serial")
public class OrderApplication extends UI
{
    @Override
    public void init(VaadinRequest request)
    {
    	
        OrderBackend backend = new OrderBackend();
        OrderManagerView manager = new OrderManagerView(backend);
    	
        HorizontalLayout mainLayout = new HorizontalLayout();
        mainLayout.setSpacing(true);
        mainLayout.setMargin(true);

        mainLayout.addComponent(manager);
        mainLayout.setSizeFull();
        setContent(mainLayout);
        getPage().setTitle("Web Application with Axon 2 and Vaadin 7");
    	
    }
}
