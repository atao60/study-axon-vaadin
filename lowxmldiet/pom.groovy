project (modelVersion: '4.0.0')
{		
	groupId 'com.acme.oms.lowxmldiet'
	artifactId 'oms-aggregator'
	version '1.0.1-SNAPSHOT'
	packaging 'pom'
	
	name 'Order Management System - Aggregator (Low XML diet)'
	description '''Wrap the Maven modules for Order Management System
    Use Maven Polyglot, Spring Java Config, ...'''
	
	modules {
		module 'oms-parent'
		module 'oms-core'
		module 'oms-ui'
	}
}
