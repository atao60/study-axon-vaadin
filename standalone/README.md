Sample Axon project as a standalone application
======

What is worse than a code sample failing to work as soon as it is downloaded! Wrong or incomplete help instructions, wrong code or configuration, old versions with repository unavailable, IDE or Maven not installed, ... it doesn't matter. With a runnable war, no more such issues: a JVM is enough.

There are a lot more about the rationale of runnable war (see documentation below), but this point worth it in itself. 

This sample application is build upon the previous ["low xml diet" one](../lowxmldiet). It provides a runnable war out of the boxe with an embedded Jetty server. 

The [Spring Boot Maven Plugin](http://docs.spring.io/spring-boot/docs/current/maven-plugin) is used here to create the runnable war. It's still a true war: there is no need to keep two versions, one standard and one runnable.

Icing on the cake: as a main class is required, it can be used to launch the application without creating the war file.

Documentation
-------

[Why and how to use Jetty in mission-critical production](http://johannesbrodwall.com/2010/03/08/why-and-how-to-use-jetty-in-mission-critical-production/)


Transformation
-------

The project "oms-ui* is kept with *war* as pom packaging. 

The plugin *spring-boot-maven-plugin* is added to the Maven build section.

The class *OrderEmbeddedServer* is created to start the embedded server.

Building
------

JRE 1.7/1.8 and Maven 3.3.1 are required.

Under Eclipse:

1. Import the project *standalone* from GitHub repository, see [here](../README.md)
1. Configure the root project *study-axon-vaadin-standalone* as Maven project:
  `Configure > Convert to Maven Project`  
1. Import the sub-folders *oms-parent*, *oms-core* and *oms-ui* as Eclipse projects:  
`Import... > General > Existing Projects into Workspace`
1. Build the root project:
        `Run as > Maven build...`  
 and set goal to: clean package.
 
Running
------

#### Under a console ####

The packages must have been built as described above. 

##### Headless application #####

Go to the sub-folder *oms-core* that contains the project *study-axon-vaadin-standalone-core* and run:

        java -jar target/oms-core-1.0.1-SNAPSHOT-standalone.jar
    
##### Web application #####

Go to the sub-folder *oms-ui* that contains the project *study-axon-vaadin-standalone-ui* and run:  

        java -jar target/oms-ui-1.0.1-SNAPSHOT-standalone.war
    
Once the launching is successful, check the application from a browser by opening:

        http://localhost:7080/vaadin
    

#### Under Eclipse ####

There is no need to build the packages before running the application as described below.

##### Headless application #####

Create a launch configuration with:

        Run as > Java Application
and:

- set project to: study-axon-vaadin-standalone-core
- set main class to: com.acme.oms.OrderAppRunner
- set Execution environment to: JavaSE-1.7/1.8

##### Web application #####

##### Without Maven #####

Create a launch configuration with:

        Run as > Java Application
and:

- set project to: study-axon-vaadin-standalone-ui
- set main class to: com.acme.oms.server.jetty.OrderEmbeddedServer
- set Execution environment to: JavaSE-1.7/1.8


##### With Maven #####

Create a launch configuration with:

        Run as > Maven Build... 
and:

- set base directory to: ${workspace_loc:/study-axon-vaadin-standalone-ui}
- set goal to: jetty-run
- check "Resolve Workspace artifacts"
- select a Maven runtime with version 3.3.1 or above
- set Execution environment to: JavaSE-1.7/1.8
- add to the list of VM arguments: -Xms1024m -Xmx1024m -Xss2m -Dmaven.multiModuleProjectDirectory=

>Notes.  

>- With the option "Resolve Workspace artifacts", no need to package the projects before running "jetty-run".
>- Under Eclipse, with Xtend, `jetty:run` needs more space. Then the memory arguments.
>- Maven 3.3.1 under Eclipse requires the argument `-Dmaven.multiModuleProjectDirectory=`, even empty.
 
##### Display #####
 
Once the launching is successful, check the application from a browser by opening:

        http://localhost:7080/vaadin
        

