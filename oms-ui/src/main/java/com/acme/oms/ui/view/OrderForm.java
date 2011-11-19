package com.acme.oms.ui.view;

import com.vaadin.data.Item;
import com.vaadin.data.util.BeanItem;
import com.vaadin.ui.*;
import org.axonframework.commandhandling.CommandBus;

import com.acme.oms.ui.data.*;
import com.acme.oms.api.*;

import java.io.Serializable;

/**
 * <p>Form that can be used to create new orders, confirm or cancel existing orders.
 * </p>
 * <p>The form makes use of the command bus to send commands to the backend</p>
 *
 * @author Mustafa Erdogan
 */
@SuppressWarnings("serial")
public class OrderForm extends Form implements Button.ClickListener {
    private Button create = new Button("New", (Button.ClickListener) this);
    private Button confirm = new Button("Confirm", (Button.ClickListener) this);
    private Button cancel = new Button("Cancel", (Button.ClickListener) this);

    private CommandBus commandBus;
    private OrderContainer orderContainer;

    public OrderForm(CommandBus commandBus) {
        this.commandBus = commandBus;
        createAndSetFooter();
    }

    public OrderContainer getOrderContainer() {
		return orderContainer;
	}

	public void setOrderContainer(OrderContainer orderContainer) {
		this.orderContainer = orderContainer;
	}

	public void buttonClick(Button.ClickEvent event) {
        Button source = event.getButton();
        if (source == create) {
        	if (create.getCaption() == "New") {
        		addOrder();
        	} else {
	            handleSave();
        	}
        } else if (source == cancel) {
            handleCancel();
        } else if (source == confirm) {
            handleConfirm();
        }
        
        orderContainer.refreshContent();
    }

    public void addOrder() {
        setItemDataSource(new BeanItem<OrderFormBean>(new OrderFormBean()));
        setReadOnly(false);
    }
    
    @Override
    public void setItemDataSource(Item newDataSource) {
        if (newDataSource != null) {
            super.setItemDataSource(newDataSource);
            setReadOnly(true);
            getFooter().setVisible(true);
        } else {
            super.setItemDataSource(null);
            getFooter().setVisible(false);
        }
    }

    @Override
    public void setReadOnly(boolean readOnly) {
        super.setReadOnly(readOnly);
        if (readOnly) {
	        create.setCaption("New");
        } else {
	        create.setCaption("Save");
        }
        cancel.setVisible(readOnly);
        confirm.setVisible(readOnly);
    }

    private void handleCancel() {
        setReadOnly(true);
        OrderFormBean order = obtainCreateOrderFormBeanFromDatasource();
        CancelOrderCommand command = new CancelOrderCommand(order.getOrderId());
        commandBus.dispatch(command);
        String message = "Cancel the order with id " + order.getOrderId();
        fireEvent(new FormIsSuccessfullyCommittedEvent(this));
        getApplication().getMainWindow().showNotification(message, Window.Notification.TYPE_TRAY_NOTIFICATION);
    }

    private void handleConfirm() {
        setReadOnly(true);
        OrderFormBean order = obtainCreateOrderFormBeanFromDatasource();
        ConfirmOrderCommand command = new ConfirmOrderCommand(order.getOrderId());
        commandBus.dispatch(command);
        String message = "Confirm the order with id " + order.getOrderId();
        fireEvent(new FormIsSuccessfullyCommittedEvent(this));
        getApplication().getMainWindow().showNotification(message, Window.Notification.TYPE_TRAY_NOTIFICATION);
    }
    
    private void handleSave() {
        if (!isValid()) {
            return;
        }

        OrderFormBean order = obtainCreateOrderFormBeanFromDatasource();

        CreateOrderCommand createCommand = new CreateOrderCommand(order.getOrderId(), order.getProductId());
        String message = "Created new order with id " + order.getOrderId();
        commandBus.dispatch(createCommand);
        
        fireEvent(new FormIsSuccessfullyCommittedEvent(this));
        setReadOnly(true);
        getApplication().getMainWindow().showNotification(message, Window.Notification.TYPE_TRAY_NOTIFICATION);
    }

    @SuppressWarnings("unchecked")
	private OrderFormBean obtainCreateOrderFormBeanFromDatasource() {
        //no inspection unchecked
        return ((BeanItem<OrderFormBean>) getItemDataSource()).getBean();
    }


    private void createAndSetFooter() {
        HorizontalLayout footer = new HorizontalLayout();
        footer.setSpacing(true);
        footer.addComponent(create);
        footer.addComponent(cancel);
        footer.addComponent(confirm);
        footer.setVisible(true);
        setFooter(footer);
    }


    /*
        EVENTS
     */
    public class FormIsSuccessfullyCommittedEvent extends Component.Event {
        private String orderId;
        private String productId;
        private String status;

        /**
         * Constructs a new event with the specified source component.
         *
         * @param source the source component of the event
         */
        public FormIsSuccessfullyCommittedEvent(Component source) {
            super(source);
            OrderFormBean order = obtainCreateOrderFormBeanFromDatasource();
            orderId = order.getOrderId();
            productId = order.getProductId();
            status = order.getStatus();
        }

		public String getOrderId() {
			return orderId;
		}

		public String getProductId() {
			return productId;
		}

		public String getStatus() {
			return status;
		}

    }

    public interface CommitListener extends Serializable {
        public void formIsCommitted(FormIsSuccessfullyCommittedEvent event);
    }

    public void addListener(CommitListener listener) {
        addListener(FormIsSuccessfullyCommittedEvent.class, listener, "formIsCommitted");
    }

    public void removeListener(CommitListener listener) {
        removeListener(FormIsSuccessfullyCommittedEvent.class, listener, "formIsCommitted");
    }
}
