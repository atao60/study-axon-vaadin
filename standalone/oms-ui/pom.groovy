project(modelVersion: '4.0.0') {
    parent('com.acme.oms.standalone:oms-parent:1.0.1-SNAPSHOT') {  relativePath '../oms-parent'  }
    artifactId 'oms-ui'
    packaging 'war'

    name 'Order Management System - Vaadin Web Application'
    description 'The GUI server'

    build {
        plugins {
            plugin('org.eclipse.xtend:xtend-maven-plugin')
            plugin('org.springframework.boot:spring-boot-maven-plugin:1.2.3.RELEASE') {
                configuration {
                    classifier 'standalone'
                    mainClass 'com.acme.oms.server.jetty.OrderEmbeddedServer'
                }
                executions {
                    execution {
                        id 'package-runable-war'
                        goals { goal 'repackage' }
                    }
                }
            }

            plugin('org.apache.maven.plugins:maven-war-plugin') {
                configuration {
                    /*
                     packagingExcludes 'WEB-INF/lib/javax.servlet-api-*.jar'
                     */
                    webResources {
                        resource {
                            directory "../."
                            includes
                            { include "README.md" }
                        }
                    }
                }
            }

            plugin('org.apache.maven.plugins:maven-enforcer-plugin')

            /* As "inplace" GWT compilation is made, ensure the widgetset
             directory is cleaned properly */
            plugin('org.apache.maven.plugins:maven-clean-plugin') {
                configuration {
                    filesets { fileset { directory 'src/main/webapp/VAADIN/widgetsets'
                        } }
                }
            }
            plugin('com.vaadin:vaadin-maven-plugin:${vaadin.plugin.version}') {
                executions {
                    execution {
                        goals {
                            goal 'clean'
                            goal 'resources'
                            goal 'update-theme'
                            goal 'update-widgetset'
                            goal 'compile-theme'
                            goal 'compile'
                        }
                    }
                }
                configuration {
                    extraJvmArgs '-Xmx512M -Xss1024k'
                    /* runTarget 'mobilemail' */
                    /* We are doing "inplace" but into subdir VAADIN/widgetsets. This 
                     way compatible with Vaadin eclipse plugin. */
                    webappDirectory '${basedir}/src/main/webapp/VAADIN/widgetsets'
                    hostedWebapp '${basedir}/src/main/webapp/VAADIN/widgetsets'
                    /* Most Vaadin apps don't need this stuff, guide that to target */
                    persistentunitcachedir '${project.build.directory}'
                    deploy '${project.build.directory}/gwt-deploy'
                    /* Compile report is not typically needed either, saves hundreds of mb on disk */
                    compileReport 'false'
                    noServer 'true'
                    /* Remove draftCompile when project is ready */
                    draftCompile 'false'

                    style 'OBF'
                    strict 'true'
                    runTarget 'http://localhost:${web.port}/'
                }
                /* required when deamond is true for jetty */
                dependencies
                { dependency('org.eclipse.jetty:jetty-util:${jetty.maven.version}') }
            }
            /* A simple Jetty test server can be launched with the Maven goal jetty:run 
             and the content available from:
             http://localhost:7080/vaadin 
             It will be stopped with jetty:stop 
             Note: Jetty 9 requieres Java 1.7 and above.
             */    
            plugin('org.eclipse.jetty:jetty-maven-plugin:${jetty.maven.version}') {
                configuration {
                    /* deamon 'true' */
                    httpConnector { port '${web.port}' }
                    stopPort '9966'
                    stopKey 'vaadin'
                    /* Redeploy every x seconds if changes are detected, 0 for no automatic redeployment */
                    scanIntervalSeconds '0'
                    /* make sure Jetty also finds the widgetset */
                    webAppConfig { contextPath '/vaadin' }
                }
                dependencies {
                    /* required when deamond is true for jetty */
                    /*
                     dependency {
                     groupId 'org.eclipse.jetty'
                     artifactId 'jetty-util'
                     version '${jetty.maven.version}'
                     }
                     */
                }
            }
            plugin('org.codehaus.mojo:exec-maven-plugin:1.4.0') {
                configuration {
                    executable  'java'
                    arguments {
                        argument '-cp'
                        classpath ''
                        argument 'com.acme.oms.server.jetty.OrderEmbeddedServer'
                        argument 'usage'
                    }
                }
            }
        }
        pluginManagement {
            plugins {
                plugin('org.apache.maven.plugins:maven-eclipse-plugin') {
                    configuration {
                        wtpversion '2.0'
                        additionalProjectnatures { projectnature 'com.vaadin.integration.eclipse.widgetsetNature' }
                        additionalBuildcommands {
                            buildcommand 'com.vaadin.integration.eclipse.widgetsetBuilder'
                            buildcommand 'com.vaadin.integration.eclipse.addonStylesBuilder'
                        }
                    }
                }
            }
        }
    }

    dependencies {

        /* Internal dependencies */
        dependency('${project.groupId}:oms-core:${project.version}')

        /* External dependencies */
        dependency('org.eclipse.xtend:org.eclipse.xtend.lib')
        dependency('com.vaadin:vaadin-server')
        dependency('com.vaadin:vaadin-client-compiled')
        dependency('com.vaadin:vaadin-client')
        dependency('com.vaadin:vaadin-push')
        dependency('com.vaadin:vaadin-themes')
        dependency('javax.servlet:javax.servlet-api')
        { scope 'compile' /* required by exec:exec */ }


        dependency('xmlpull:xmlpull')  /* required by exec:exec */



        /* Standalone application */
        dependency('org.eclipse.jetty:jetty-server:${jetty.maven.version}:provided')
        dependency('org.eclipse.jetty:jetty-webapp:${jetty.maven.version}:provided')
        dependency('org.eclipse.jetty:jetty-continuation:${jetty.maven.version}:provided')

        /* Needed when using the widgetset optimizer (custom ConnectorBundleLoaderFactory).
         For widgetset compilation, vaadin-client-compiler is automatically added on the
         compilation classpath by vaadin-maven-plugin so normally there is no need for an
         explicit dependency.
         */
        /*
         dependency {
         groupId 'com.vaadin'
         artifactId 'vaadin-client-compiler'
         exclusions {
         exclusion {
         groupId 'commons-logging'
         artifactId 'commons-logging' 
         }
         }
         }		 
         */

        /* required when deamond is true for jetty */
        /*
         dependency {
         groupId 'org.eclipse.jetty'
         artifactId 'jetty-util'
         version '${jetty.maven.version}'
         } 
         */
    }
}
