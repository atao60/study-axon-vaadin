package com.acme.oms.ui.view

import com.acme.oms.query.Order
import com.acme.oms.ui.common.view.ListView
import com.acme.oms.ui.data.OrderBackend
import com.vaadin.data.Property.ValueChangeEvent
import com.vaadin.shared.ui.MarginInfo
import com.vaadin.ui.Button
import com.vaadin.ui.Button.ClickEvent
import com.vaadin.ui.Component
import com.vaadin.ui.HorizontalLayout
import com.vaadin.ui.HorizontalSplitPanel
import com.vaadin.ui.Table
import com.vaadin.ui.VerticalLayout

/**
 * Frame with a form to edit an order and a table to list all orders
 */
class OrderManagerView extends HorizontalSplitPanel implements ListView {
    
    val static TOP_MARGIN = new MarginInfo(1)

    val OrderBackend backend
    val Table table
    val OrderForm form
    val Component controls
    
    var Button validate
    var Button create
    var Button cancel
    var Button confirm
    
    
    new(OrderBackend backend) {
        super()
        this.backend = backend
        this.table = new OrderList(backend)
        this.form = new OrderForm(backend)
        this.controls = buildControls
        
        update
        bindFormWithTableRecord
        
        setFirstComponent(controlsAndForm)
        setSecondComponent(this.table)
        setSplitPosition(20)
        setFormReadOnly(true)
    }

    private def controlsAndForm() {
        val panel = new VerticalLayout
        panel.addComponent(form)
        panel.addComponent(controls)
        return panel
    }

    private def bindFormWithTableRecord() {
        // handle selection changes
        table.addValueChangeListener([ValueChangeEvent event |
                 form.bind(table.value as Order)
                 setFormReadOnly(true)
        ])
    }

    private def buildControls() {
        val tableControls = new HorizontalLayout
        tableControls.setMargin(TOP_MARGIN);
        tableControls.setSpacing(true)
        validate = new Button("Add", [ClickEvent event |
                form.handleSave
                update
                form.bind(null as Order)
                setFormReadOnly(true)
        ]);
        create = new Button("New",  [ClickEvent event |
                form.createOrder
                table.select(table.nullSelectionItemId)
                setFormReadOnly(false)
        ])
        cancel = new Button("Cancel", [ClickEvent event |
                form.handleCancel
                update
                setFormReadOnly(true)
        ])
        confirm = new Button("Confirm", [ClickEvent event |
                form.handleConfirm
                update
                setFormReadOnly(true)
        ])
        tableControls.addComponent(validate)
        tableControls.addComponent(create)
        tableControls.addComponent(cancel)
        tableControls.addComponent(confirm)
        return tableControls
   }
    

    private def setFormReadOnly(boolean readOnly) {
        form.setReadOnly(readOnly);
        validate.setVisible(! readOnly);
        create.setVisible(readOnly);
        cancel.setVisible(readOnly);
        confirm.setVisible(readOnly);
    }

    override update() {
        val container = backend.container.refreshContent
        table.setContainerDataSource(container)
    }

}
