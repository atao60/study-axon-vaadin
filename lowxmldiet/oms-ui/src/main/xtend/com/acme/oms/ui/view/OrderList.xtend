package com.acme.oms.ui.view

import com.acme.oms.ui.data.DataDescriptor
import com.acme.oms.ui.data.OrderBackend
import com.acme.oms.ui.data.OrderContainer
import com.google.common.primitives.Booleans
import com.vaadin.ui.Table

class OrderList extends Table {

    val static PX_UNIT = "px"

    val static COLUMN_SORTERS = newLinkedHashMap(
        DataDescriptor.orderId.name -> true,
        DataDescriptor.productId.name -> true
    )
    
    val OrderContainer container

    new(OrderBackend backend) {
        container = backend.container
        containerDataSource = container

        initDataLayout
        initGlobalBehavior
    }

    def initDataLayout() {
        visibleColumns = DataDescriptor.names as Object[]
        columnHeaders = DataDescriptor.labels
        sort(COLUMN_SORTERS.keySet.toArray, Booleans.toArray(COLUMN_SORTERS.values))
    }

    def initGlobalBehavior() {
        allowColumnResizing
        selectable = true
        immediate = true
        nullSelectionAllowed = false
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
