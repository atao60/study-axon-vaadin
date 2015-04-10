package com.acme.oms.ui

import com.acme.oms.ui.data.OrderBackend
import com.acme.oms.ui.view.OrderManagerView
import com.vaadin.server.VaadinRequest
import com.vaadin.ui.HorizontalLayout
import com.vaadin.ui.UI

/**
 * The Application's "main" class
 */
class OrderApplication extends UI {
    
    override init(VaadinRequest request)
    {
        
        val backend = new OrderBackend()
        val manager = new OrderManagerView(backend)
        
        val mainLayout = new HorizontalLayout
        mainLayout.setSpacing(true)
        mainLayout.setMargin(true)

        mainLayout.addComponent(manager)
        mainLayout.setSizeFull
        setContent(mainLayout)
        getPage.setTitle("Web Application with Axon 2, Vaadin 7 and Xtend")
        
    }
    
}