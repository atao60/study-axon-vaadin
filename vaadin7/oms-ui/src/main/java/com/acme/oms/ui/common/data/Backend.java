package com.acme.oms.ui.common.data;

import org.axonframework.commandhandling.gateway.CommandGateway;

import com.vaadin.data.util.BeanItemContainer;

public interface Backend<E> {

    CommandGateway getCommandGateway();
    BeanItemContainer<E> getContainer();

}
