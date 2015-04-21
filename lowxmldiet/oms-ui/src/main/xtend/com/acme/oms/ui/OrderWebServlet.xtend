package com.acme.oms.ui

import com.vaadin.server.VaadinServlet
import javax.servlet.annotation.WebServlet
import com.vaadin.annotations.VaadinServletConfiguration
import javax.servlet.annotation.WebInitParam

@WebServlet(
    displayName="Vaadin Web Application",
    name="Vaadin7 Application Servlet",
    value = "/*",
    initParams = #[
        @WebInitParam(
            name = "org.atmosphere.cpr.asyncSupport",
            value = "org.atmosphere.container.JSR356AsyncSupport"
        )]
)
@VaadinServletConfiguration(
    productionMode = false,
    ui = OrderWebUI
)
class OrderWebServlet extends VaadinServlet {}