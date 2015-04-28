project (modelVersion: '4.0.0')
{		
	groupId 'com.acme.oms.standalone'
	artifactId 'oms-aggregator'
	version '1.0.1-SNAPSHOT'
	packaging 'pom'
	
	name 'Order Management System - Aggregator (Standalone)'
	description '''Wrap a server Jetty inside the application'''
	
	modules {
		module 'oms-parent'
		module 'oms-core'
		module 'oms-ui'
	}
}
