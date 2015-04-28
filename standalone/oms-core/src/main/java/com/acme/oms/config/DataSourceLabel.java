package com.acme.oms.config;

/**
 * ATM Xtend can't deal with complex enums.
 */
public enum DataSourceLabel {url, driverClassName, username, password;
	public String label() {return "dataSource." + this.name();}
}
