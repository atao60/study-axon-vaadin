package com.acme.oms.config;

public enum DataSourceLabel {url, driverClassName, username, password;
	public String label() {return "dataSource." + this.name();}
}
