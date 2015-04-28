package com.acme.oms.ui

import com.acme.oms.ui.data.OrderBackend
import com.acme.oms.ui.view.OrderManagerView
import com.vaadin.server.VaadinRequest
import com.vaadin.ui.HorizontalLayout
import com.vaadin.ui.UI

/**
 * The Application UI "main" class
 */
class OrderWebUI extends UI {
    
    override init(VaadinRequest request)
    {
        
        val backend = new OrderBackend
        val manager = new OrderManagerView(backend)
        
        content = new HorizontalLayout => [
            spacing = true
            margin = true
            addComponent(manager)
            setSizeFull
        ]

        getPage.title = "Web Application with Axon 2, Vaadin 7 and Xtend"
        
    }
    
}