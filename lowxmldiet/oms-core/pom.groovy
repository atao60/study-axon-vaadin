project(modelVersion: '4.0.0') {
	parent('com.acme.oms.lowxmldiet:oms-parent:1.0.1-SNAPSHOT') { relativePath '../oms-parent' }
	artifactId 'oms-core'

	name 'Order Management System - Core'
	description 'A low XML diet'

	build {
		resources {
			resource { directory("src/main/resources") }
			resource {
				directory("../.")
				includes { include("README.md") }
			}
		}
		plugins {
			plugin('org.eclipse.xtend:xtend-maven-plugin')
			plugin('org.apache.maven.plugins:maven-enforcer-plugin')
			plugin('org.apache.maven.plugins:maven-shade-plugin') {
				executions {
					execution {
						id 'standalone-jar'
						phase 'package'
						goals { goal 'shade' }
						configuration {
							transformers {
								transformer(implementation:'org.apache.maven.plugins.shade.resource.ManifestResourceTransformer') { 
									mainClass 'com.acme.oms.OrderAppRunner' 
								}
							}
							shadedArtifactAttached 'true'
							shadedClassifierName 'standalone'
							createDependencyReducedPom 'false'
						}
					}
				}
			}
		}
	}

	dependencies {
		dependency('org.eclipse.xtend:org.eclipse.xtend.lib')
		dependency('org.axonframework:axon-core'){
			exclusions {
				exclusion('xpp3:xpp3_min')
				exclusion('xmlpull:xmlpull')
			}
		}
		dependency('xpp3:xpp3_min') {
			exclusions {exclusion('xmlpull:xmlpull')}
		}
		dependency('xmlpull:xmlpull')
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
