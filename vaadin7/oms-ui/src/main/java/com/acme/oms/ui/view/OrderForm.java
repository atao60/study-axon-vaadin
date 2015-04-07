package com.acme.oms.ui.view;

import static java.lang.String.format;

import org.axonframework.commandhandling.gateway.CommandGateway;

import com.acme.oms.api.CancelOrderCommand;
import com.acme.oms.api.ConfirmOrderCommand;
import com.acme.oms.api.CreateOrderCommand;
import com.acme.oms.query.Order;
import com.acme.oms.ui.data.OrderBackend;
import com.acme.oms.ui.data.OrderContainer.DataDescriptor;
import com.acme.oms.ui.data.OrderFormBean;
import com.vaadin.data.fieldgroup.FieldGroup.CommitException;
import com.vaadin.ui.GridLayout;
import com.vaadin.ui.Notification;
import com.vaadin.ui.TextField;

public class OrderForm extends GridLayout
{
	
	private static final long serialVersionUID = 1L;

 	private static final String UNABLE_TO_COMMIT_THE_ORDER = "Unable to commit the order.";
	private static final String CREATED_NEW_ORDER = "Created new order with id '%s'";
	private static final String CANCEL_THE_ORDER = "Cancel the order with id %s";
	private static final String CONFIRM_THE_ORDER = "Confirm the order with id %s";

 	private CommandGateway commandGateway;
    private OrderBinder orderBinder;

    public OrderForm(OrderBackend backend) {
    	this.commandGateway = backend.getCommandGateway(); 
    	orderBinder = new OrderBinder();

		initLayout();
		initFieldGroup();
	}
    
    public void bind(OrderFormBean order) {
        orderBinder.bind(order);
    }
    
    public void bind(Order order) {
        orderBinder.bindEntity(order);
    }

    @Override
    public void setReadOnly(boolean readOnly) {
        super.setReadOnly(readOnly);
        orderBinder.setReadOnly(readOnly);
    }
    
    public void createOrder() {
        bind((Order) null);
    }
    
    public void handleSave() {
        if (!isValid()) {
            return;
        }
        
        String message = null;
        try{
            orderBinder.commit();
            OrderFormBean order = getBean();
            CreateOrderCommand command = new CreateOrderCommand(order.getOrderId(), order.getProductId());
            commandGateway.send(command);
            message = format(CREATED_NEW_ORDER, order.getOrderId());
            
        } catch (CommitException ce) {
            message = UNABLE_TO_COMMIT_THE_ORDER;
        }
         Notification.show(message, Notification.Type.TRAY_NOTIFICATION);
    }
    
    public void handleCancel() {
        
        OrderFormBean order = getBean();
        CancelOrderCommand command = new CancelOrderCommand(order.getOrderId());
        commandGateway.send(command);
        String message = format(CANCEL_THE_ORDER, order.getOrderId());
        Notification.show(message, Notification.Type.TRAY_NOTIFICATION);
   }

    public void handleConfirm() {
        
        OrderFormBean order = getBean();
        ConfirmOrderCommand command = new ConfirmOrderCommand(order.getOrderId());
        commandGateway.send(command);
        String message = format(CONFIRM_THE_ORDER, order.getOrderId());
        Notification.show(message, Notification.Type.TRAY_NOTIFICATION);
    }

    public OrderFormBean getBean() {
        return orderBinder.getItemDataSource().getBean();
    }

    private boolean isValid() {
        return orderBinder.isValid();
    }
    
    private void initLayout() {
        setColumns(1);
        setRows(DataDescriptor.values().length);
    }
    
    private void initFieldGroup() {
        for(DataDescriptor dd:DataDescriptor.values()){
        	TextField f = new TextField(dd.label());
        	orderBinder.bind(f, dd.name());
            addComponent(f);
            f.setEnabled(!DataDescriptor.status.equals(dd));
        }
    }
    
}
