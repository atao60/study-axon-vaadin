package com.acme.oms.ui.common.data

import org.axonframework.commandhandling.gateway.CommandGateway
import com.vaadin.data.util.BeanItemContainer

interface Backend<E> {
    def CommandGateway getCommandGateway()
    def BeanItemContainer<E> getContainer()
}