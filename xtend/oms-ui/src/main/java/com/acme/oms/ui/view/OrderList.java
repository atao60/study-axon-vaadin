package com.acme.oms.ui.view;

import static com.acme.oms.ui.data.OrderContainer.DataDescriptor.orderId;
import static com.acme.oms.ui.data.OrderContainer.DataDescriptor.productId;

import java.util.LinkedHashMap;
import java.util.Map;

import com.acme.oms.ui.data.OrderBackend;
import com.acme.oms.ui.data.OrderContainer;
import com.acme.oms.ui.data.OrderContainer.DataDescriptor;
import com.google.gwt.thirdparty.guava.common.primitives.Booleans;
import com.vaadin.ui.Table;

public class OrderList extends Table {

	private static final long serialVersionUID = 1L;
	private static final String PX_UNIT = "px";

	@SuppressWarnings("serial")
	private static final Map<String, Boolean> COLUMN_SORTERS = new LinkedHashMap<String, Boolean>() {
		{
			put(orderId.name(), true);
			put(productId.name(), true);
		}
	};

	private final OrderContainer container;

	public OrderList(OrderBackend backend) {
		container = backend.getContainer();
		setContainerDataSource(container);

		initDataLayout();
		initGlobalBehavior();
	}

	private void initDataLayout() {
		setVisibleColumns((Object[]) DataDescriptor.names());
		setColumnHeaders(DataDescriptor.labels());
		sort(COLUMN_SORTERS.keySet().toArray(), Booleans.toArray(COLUMN_SORTERS.values()));
	}	
	

	private void initGlobalBehavior() {
		allowColumnResizing();
		setSelectable(true);
		setImmediate(true);
		setNullSelectionAllowed(false);
		setSizeFull();
	}

	private void allowColumnResizing() {
		addColumnResizeListener(new Table.ColumnResizeListener(){
			private static final long serialVersionUID = 1L;
			@Override
			public void columnResize(ColumnResizeEvent event) {
				int width = event.getCurrentWidth();
				String column = (String) event.getPropertyId();
				setColumnFooter(column, String.valueOf(width) + PX_UNIT);
			}
		});
	}
}