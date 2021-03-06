project(modelVersion: '4.0.0') {

	groupId 'com.acme.oms.standalone'
	artifactId 'oms-parent'
	version '1.0.1-SNAPSHOT'
	packaging 'pom'

	name 'Order Management System - Parent'
	description 'Gather common parts for Order Management System'

	properties {

		/* Project parameters  */

		'web.port' '7080'
		'xtend.outputDir' '${project.build.directory}/xtend-gen/main'
		'xtend.testOutputDir' '${project.build.directory}/xtend-gen/test'

		/* JVM Management */

		'project.build.sourceEncoding' 'UTF-8'
		'project.reporting.outputEncoding' 'UTF-8'

		'jdk.version' '1.8'
		'maven.compiler.source' '${jdk.version}'
		'maven.compiler.target' '${jdk.version}'
		'maven.compiler.compilerVersion' '${jdk.version}'
		'maven.compiler.debug' 'true'
		'maven.compiler.verbose' 'true'
		'maven.compiler.fork' 'true'
		'maven.compiler.optimize' 'true'

		/* Maven and Plugin Management */

		'maven.minimal.version' '3.3.1' /* Maven 3.3.1 or above required for Polyglot */

		'jetty.maven.version' '9.2.10.v20150310'

		'vaadinVersion' '7.4.4'
		'xtendVersion' '2.7.3' /* jnario-1.0.1 seems unable to use xtend 2.8.0 */

		'vaadin.plugin.version' '${vaadinVersion}'
		'xtend.maven.version' '${xtendVersion}'
		'enforcer.maven.version' '1.4'
		'build.helper.maven.version' '1.9.1'
		'install.maven.version' '2.4'
		'surefire.maven.version' '2.12.4'
		'resources.maven.version' '2.6'
		'site.maven.version' '3.3'
		'jar.maven.version' '2.4'
		'clean.maven.version' '2.5'
		'war.maven.version' '2.6'
		'compiler.maven.version' '2.5.1'
		'deploy.maven.version' '2.7'
		'eclipse.maven.version' '2.9'
		'shade.maven.version' '2.3'

		/* Dependency Management */

		'hamcrest.version' '1.3'
		'hsqldb.version' '2.3.2'
		'hibernate.version' '4.3.8.Final'
		'hibernate.validator.version' '5.1.3.Final'
		'slf4j.version' '1.7.12'
		'logback.version' '1.1.3'
		'junit.version' '4.12'
		'servlet.api.version' '3.1.0'
		'aspectjweaver.version' '1.8.5'
		'cglib.version' '3.1'

		'axon.version' '2.4'
		'vaadin.version' '${vaadinVersion}'
		'xtend.version' '${xtendVersion}'
		'spring.version' '4.1.6.RELEASE'
	}

	build {
		plugins {
			plugin('org.codehaus.mojo:build-helper-maven-plugin')
			plugin('org.apache.maven.plugins:maven-enforcer-plugin')
		}
		pluginManagement {
			plugins {
				/* required to be able to put the xtend classes in a separate source folder */
				plugin('org.codehaus.mojo:build-helper-maven-plugin:${build.helper.maven.version}') {
					executions {
						execution(id: 'get-maven-version') {  goals { goal 'maven-version'
							} }
						execution(id: 'add-source', phase: 'generate-sources') {
							goals { goal 'add-source' }
							configuration { sources { source 'src/main/xtend'
								} }
						}
						execution(id: 'add-test-source', phase: 'generate-test-sources') {
							goals { goal 'add-test-source' }
							configuration { sources { source 'src/test/xtend'
								} }
						}
					}
				}
				plugin('org.eclipse.xtend:xtend-maven-plugin:${xtend.maven.version}') {
					executions {
						execution {
							goals {
								goal 'compile'
								goal 'testCompile'
							}
							configuration {
								outputDirectory '${xtend.outputDir}'
								testOutputDirectory '${xtend.testOutputDir}'
							}
						}
					}
				}
				plugin('org.apache.maven.plugins:maven-enforcer-plugin:${enforcer.maven.version}') {
					executions {
						execution(id: 'enforce-versions') {
							goals { goal 'enforce' }
							configuration {
								fail 'true'
								rules {
									requireMavenVersion {
										version '${maven.minimal.version}'
										message '''[ERROR] OLD MAVEN [${maven.version}] in use. 
                                            Maven ${maven.minimal.version} or newer is required.'''
									}
									requireJavaVersion {
										version '${jdk.version}'
										message '''[ERROR] OLD JDK [${java.version}] in use. 
                                            JDK ${jdk.version} or newer is required.'''
									}
									requirePluginVersions {
										banLatest 'true'
										banRelease 'true'
										banSnapshots 'true'
									}
									bannedDependencies {
										searchTransitive 'true'
										excludes {
											exclude 'commons-logging'
											exclude 'log4j'
											exclude 'org.apache.logging.log4j'
										}
									}
								}
							}
						}
					}
				}
				plugin('org.apache.maven.plugins:maven-compiler-plugin:${compiler.maven.version}')
				plugin('org.apache.maven.plugins:maven-surefire-plugin:${surefire.maven.version}')
				plugin('org.apache.maven.plugins:maven-jar-plugin:${jar.maven.version}')
				plugin('org.apache.maven.plugins:maven-clean-plugin:${clean.maven.version}')
				plugin('org.apache.maven.plugins:maven-install-plugin:${install.maven.version}')
				plugin('org.apache.maven.plugins:maven-site-plugin:${site.maven.version}')
				plugin('org.apache.maven.plugins:maven-resources-plugin:${resources.maven.version}')
				plugin('org.apache.maven.plugins:maven-deploy-plugin:${deploy.maven.version}')
				plugin('org.apache.maven.plugins:maven-war-plugin:${war.maven.version}') {
					configuration { failOnMissingWebXml 'false' // required for XML-less configuration
					}
				}
				plugin('org.apache.maven.plugins:maven-eclipse-plugin:${eclipse.maven.version}')
				plugin('org.apache.maven.plugins:maven-shade-plugin:${shade.maven.version}')
			}
		}
	}

	dependencyManagement {
		dependencies {
			dependency('org.eclipse.xtend:org.eclipse.xtend.lib:${xtend.version}')
			dependency('org.axonframework:axon-core:${axon.version}') {
				exclusions {
					exclusion('xpp3:xpp3_min')
					exclusion('xmlpull:xmlpull')
				}
			}
			dependency('xpp3:xpp3_min:1.1.4c') {
				exclusions {exclusion('xmlpull:xmlpull')}
			}
			dependency('xmlpull:xmlpull:1.1.3.1')
			dependency('org.springframework:spring-context-support:${spring.version}') {
				exclusions {exclusion('commons-logging:commons-logging')}
			}
			dependency('org.springframework:spring-aspects:${spring.version}')
			dependency('org.aspectj:aspectjweaver:${aspectjweaver.version}')
			dependency('cglib:cglib-nodep:${cglib.version}') // required by Axon-core and by Spring for @Configuration
			/* web */
			dependency('com.vaadin:vaadin-server:${vaadin.version}'){
                exclusions {
                    exclusion('com.vaadin.external.google:guava')
                }
            }
			dependency('com.vaadin:vaadin-client-compiled:${vaadin.version}')
			dependency('com.vaadin:vaadin-client-compiler:${vaadin.version}')
			dependency('com.vaadin:vaadin-client:${vaadin.version}')
			dependency('com.vaadin:vaadin-push:${vaadin.version}')
			dependency('com.vaadin:vaadin-themes:${vaadin.version}')
			dependency('javax.servlet:javax.servlet-api:${servlet.api.version}:provided') // Servlet 3.0 required for Java Base Configuration
			/* persistence */
			dependency('org.springframework:spring-orm:${spring.version}')
			{
				exclusions {exclusion('commons-logging:commons-logging')}
			}
			dependency('org.hibernate:hibernate-entitymanager:${hibernate.version}')
			dependency('org.hsqldb:hsqldb:${hsqldb.version}')
			/* validation */
			dependency('org.hibernate:hibernate-validator:${hibernate.validator.version}')
			dependency('javax.validation:validation-api:1.1.0.Final') // hibernate validation 5.x ---> JSR-303 ---> JSR-349 => 1.1.0.Final
			/* logging */
			dependency('ch.qos.logback:logback-classic:${logback.version}')
			dependency('org.slf4j:jcl-over-slf4j:${slf4j.version}')
			/* tests */
			dependency('junit:junit:${junit.version}:test')
			dependency('org.springframework:spring-test:${spring.version}:test')
			dependency('org.axonframework:axon-test:${axon.version}:test')

		}
	}

}
