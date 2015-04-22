project(modelVersion: '4.0.0')
{
	parent('com.acme.oms.lowxmldiet:oms-parent:1.0.1-SNAPSHOT')
	{
		relativePath '../oms-parent'
	}
	artifactId 'oms-core'
	
	name 'Order Management System - Core'
	description 'A low XML diet'

	build {
		plugins {
			plugin('org.eclipse.xtend:xtend-maven-plugin')
			plugin('org.apache.maven.plugins:maven-enforcer-plugin')
		}
	}

	dependencies {
		dependency('org.eclipse.xtend:org.eclipse.xtend.lib')
		dependency('org.axonframework:axon-core')
		dependency('org.springframework:spring-context-support')
		dependency('org.hibernate:hibernate-validator')
		dependency('org.springframework:spring-aspects')
		dependency('org.aspectj:aspectjweaver')

		/* persistence */
		dependency('org.hibernate:hibernate-entitymanager')
		dependency('org.springframework:spring-orm')
		dependency('org.hsqldb:hsqldb')

		/* logs */
		dependency('ch.qos.logback:logback-classic')
		dependency('org.slf4j:jcl-over-slf4j')
		
		/* tests */
		dependency('junit:junit')
		dependency('org.springframework:spring-test')
		dependency('org.axonframework:axon-test')
				
	}
}
