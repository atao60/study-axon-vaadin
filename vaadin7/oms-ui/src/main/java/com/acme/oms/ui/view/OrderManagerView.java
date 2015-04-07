package com.acme.oms.ui.view;


import com.acme.oms.query.Order;
import com.acme.oms.ui.common.view.ListView;
import com.acme.oms.ui.data.OrderBackend;
import com.vaadin.data.Property.ValueChangeEvent;
import com.vaadin.data.Property.ValueChangeListener;
import com.vaadin.data.util.BeanItemContainer;
import com.vaadin.shared.ui.MarginInfo;
import com.vaadin.ui.Button;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.Button.ClickListener;
import com.vaadin.ui.Component;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.HorizontalSplitPanel;
import com.vaadin.ui.Table;
import com.vaadin.ui.VerticalLayout;

/**
 * Frame with a form to edit an order and a table to list all orders
 */
public class OrderManagerView extends HorizontalSplitPanel implements ListView {

	private static final long serialVersionUID = 1L;
	
	private static final MarginInfo TOP_MARGIN = new MarginInfo(1);

	private final OrderBackend backend;
	private final Table table;
	private final OrderForm form;
	private final Component controls;
	
    private Button validate;
    private Button create;
    private Button cancel;
    private Button confirm;
	
	
	public OrderManagerView(OrderBackend backend) {
		super();
		this.backend = backend;
		this.table = new OrderList(backend);
		this.form = new OrderForm(backend);
		this.controls = buildControls();
		
		update();
		bindFormWithTableRecord();
		
        setFirstComponent(controlsAndForm());
        setSecondComponent(this.table);
        setSplitPosition(20);
        setFormReadOnly(true);
	}

	private Component controlsAndForm() {
		VerticalLayout panel = new VerticalLayout();
		panel.addComponent(form);
		panel.addComponent(controls);
		return panel;
	}

	private void bindFormWithTableRecord() {
	    // handle selection changes
        table.addValueChangeListener(new ValueChangeListener() {
            private static final long serialVersionUID = 1L;
            @Override
            public void valueChange(ValueChangeEvent event) {
                 form.bind((Order) table.getValue());
                 setFormReadOnly(true);
            }
        });
	}

    private Component buildControls() {
        HorizontalLayout tableControls = new HorizontalLayout();
        tableControls.setMargin(TOP_MARGIN);
        tableControls.setSpacing(true);
        validate = new Button("Add", new ClickListener() {
            private static final long serialVersionUID = 1L;
            @Override
            public void buttonClick(ClickEvent event) {
                form.handleSave();
                update();
                form.bind((Order) null);
                setFormReadOnly(true);
            }
        });
        create = new Button("New", new ClickListener() {
            private static final long serialVersionUID = 1L;
            @Override
            public void buttonClick(ClickEvent event) {
                form.createOrder();
                table.select(table.getNullSelectionItemId());
                setFormReadOnly(false);
            }
        });
        cancel = new Button("Cancel", new ClickListener() {
            private static final long serialVersionUID = 1L;
            @Override
            public void buttonClick(ClickEvent event) {
                form.handleCancel();
                update();
                setFormReadOnly(true);
            }
        });
        confirm = new Button("Confirm", new ClickListener() {
            private static final long serialVersionUID = 1L;
            @Override
            public void buttonClick(ClickEvent event) {
                form.handleConfirm();
                update();
                setFormReadOnly(true);
            }
        });
        tableControls.addComponent(validate);
        tableControls.addComponent(create);
        tableControls.addComponent(cancel);
        tableControls.addComponent(confirm);
        return tableControls;
   }
    

    private void setFormReadOnly(boolean readOnly) {
        form.setReadOnly(readOnly);
        validate.setVisible(! readOnly);
        create.setVisible(readOnly);
        cancel.setVisible(readOnly);
        confirm.setVisible(readOnly);
    }

	@Override
	public void update() {
        BeanItemContainer<Order> container = backend.getContainer().refreshContent();
    	table.setContainerDataSource(container);
	}

}
