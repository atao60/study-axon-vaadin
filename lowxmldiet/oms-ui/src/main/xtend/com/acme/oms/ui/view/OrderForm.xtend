package com.acme.oms.ui.view

import static java.lang.String.format;

import com.acme.oms.api.CreateOrderCommand
import com.acme.oms.query.Order
import com.acme.oms.ui.data.DataDescriptor
import com.acme.oms.ui.data.OrderBackend
import com.acme.oms.ui.data.OrderFormBean
import com.vaadin.data.fieldgroup.FieldGroup.CommitException;
import com.vaadin.ui.GridLayout
import com.vaadin.ui.Notification;
import com.vaadin.ui.TextField
import org.axonframework.commandhandling.gateway.CommandGateway
import com.acme.oms.api.CancelOrderCommand
import com.acme.oms.api.ConfirmOrderCommand

class OrderForm extends GridLayout
{
    
    val static UNABLE_TO_COMMIT_THE_ORDER = "Unable to commit the order."
    val static CREATED_NEW_ORDER = "Created new order with id '%s'"
    val static CANCEL_THE_ORDER = "Cancel the order with id %s"
    val static CONFIRM_THE_ORDER = "Confirm the order with id %s"

    val CommandGateway commandGateway
    val OrderBinder orderBinder

    new(OrderBackend backend) {
        this.commandGateway = backend.commandGateway
        orderBinder = new OrderBinder()

        initLayout
        initFieldGroup
    }
    
    def bind(OrderFormBean order) {
        orderBinder.bind(order)
    }
    
    def bind(Order order) {
        orderBinder.bindEntity(order)
    }

    override setReadOnly(boolean readOnly) {
        super.setReadOnly(readOnly)
        orderBinder.setReadOnly(readOnly)
    }
    
    def createOrder() {
        bind(null as Order);
    }
    
    def handleSave() {
        if (!isValid) {
            return
        }
        
        var String message = null;
        try{
            orderBinder.commit
            val order = bean
            val command = new CreateOrderCommand(order.orderId, order.productId)
            commandGateway.send(command)
            message = format(CREATED_NEW_ORDER, order.orderId)
            
        } catch (CommitException ce) {
            message = UNABLE_TO_COMMIT_THE_ORDER;
        }
        
        Notification.show(message, Notification.Type.TRAY_NOTIFICATION)
    }
    
    def handleCancel() {
        
        val order = bean
        val command = new CancelOrderCommand(order.orderId)
        commandGateway.send(command)
        val message = format(CANCEL_THE_ORDER, order.orderId)
        Notification.show(message, Notification.Type.TRAY_NOTIFICATION)
   }

    def handleConfirm() {
        
        val order = bean
        val command = new ConfirmOrderCommand(order.orderId)
        commandGateway.send(command)
        val message = format(CONFIRM_THE_ORDER, order.orderId)
        Notification.show(message, Notification.Type.TRAY_NOTIFICATION)
    }

    def getBean() {
        return orderBinder.itemDataSource.bean
    }

    private def isValid() {
        return orderBinder.isValid
    }
    
    private def initLayout() {
        setColumns(1)
        setRows(DataDescriptor.values.length)
    }
    
    private def initFieldGroup() {
        for(DataDescriptor dd:DataDescriptor.values){
            val f = new TextField(dd.label)
            orderBinder.bind(f, dd.name)
            addComponent(f)
            f.setEnabled(!DataDescriptor.status.equals(dd))
        }
    }
    
}
