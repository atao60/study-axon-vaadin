package com.acme.oms.ui.data;

/**
 * ATM Xtend can't deal with complex enums.
 */
public enum DataDescriptor {
	
	orderId("Order Id"), productId("Product Id"), status("Status");
	
	final private String label;
	private DataDescriptor(String label) {
		this.label = label;
	}
	public static String[] names() {
		String[] names = new String[values().length];
		for(int i = 0; i < values().length;i++) {
			names[i] = values()[i].name();
		}
		return names;
	}
	public static String[] labels() {
		String[] labels = new String[values().length];
		for(int i = 0; i < values().length;i++) {
			labels[i] = values()[i].label;
		}
		return labels;
	}
	public String label() {
		return label;
	}

}
