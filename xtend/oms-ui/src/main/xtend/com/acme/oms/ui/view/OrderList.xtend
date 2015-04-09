package com.acme.oms.ui.view

import com.acme.oms.ui.data.DataDescriptor
import com.acme.oms.ui.data.OrderBackend
import com.acme.oms.ui.data.OrderContainer
import com.google.common.primitives.Booleans
import com.vaadin.ui.Table
import java.util.LinkedHashMap

class OrderList extends Table {

    val static PX_UNIT = "px"

    val static COLUMN_SORTERS = newLinkedHashMap(
        DataDescriptor.orderId.name -> true,
        DataDescriptor.productId.name -> true
    )
    
    val OrderContainer container;

    new(OrderBackend backend) {
        container = backend.container
        setContainerDataSource(container)

        initDataLayout
        initGlobalBehavior
    }

    def initDataLayout() {
        setVisibleColumns(DataDescriptor.names as Object[])
        setColumnHeaders(DataDescriptor.labels)
        sort(COLUMN_SORTERS.keySet.toArray, Booleans.toArray(COLUMN_SORTERS.values))
    }

    def initGlobalBehavior() {
        allowColumnResizing
        setSelectable(true)
        setImmediate(true)
        setNullSelectionAllowed(false)
        setSizeFull
    }

    def allowColumnResizing() {
        addColumnResizeListener(
            [ ColumnResizeEvent event |
                val width = event.currentWidth
                val column = event.propertyId
                setColumnFooter(column, String::valueOf(width) + PX_UNIT)
            ])
    }

}
