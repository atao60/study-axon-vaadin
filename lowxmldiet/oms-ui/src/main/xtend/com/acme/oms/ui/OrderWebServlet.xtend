package com.acme.oms.ui

import com.vaadin.annotations.VaadinServletConfiguration
import com.vaadin.server.VaadinServlet
import javax.servlet.annotation.WebInitParam
import javax.servlet.annotation.WebServlet

@WebServlet(
    displayName="Vaadin Web Application",
    name="Vaadin7 Application Servlet",
    value = OrderWebServlet::urlPatterns,
    initParams = #[
        @WebInitParam(
            name = "org.atmosphere.cpr.asyncSupport",
            value = "org.atmosphere.container.JSR356AsyncSupport"
        )]
)
@VaadinServletConfiguration(
    productionMode = OrderWebServlet::productionMode,
    ui = OrderWebUI
)
class OrderWebServlet extends VaadinServlet {
    public static final String urlPatterns = "/*"
    public static final boolean productionMode = false
    
}