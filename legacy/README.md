Sample Axon project with JPA and Vaadin
=====

This sample application is almost the same as the original [study-axon-vaadin](https://code.google.com/p/study-axon-vaadin/) one, i.e. using JPA, Hibernate, Axon and Vaadin. Almost, that is to say:

- the dependencies have been updated but the Vaadin Framework which is still with a version 6.x,
- the root project has been refactored with, for example, the creation of a Maven module "parent". 


Building
------

Under Eclipse:

1. Configure the root project as Maven project:
  `Configure > Convert to Maven Project`  
1. Import the sub-folders oms-parent, oms-core and oms-ui as Eclipse projects:  
`Import... > General > Existing Projects into Workspace`
1. Build the root project:  
`Run as > Maven build...`  
 and set goal to "clean package"  
 
Running
------

Launch the web application from project study-axon-vaadin-legacy-ui:

        Run as > Maven Build... 
and:

- set goal to "jetty-run"
- check "Resolve Workspace artifacts"

>Note. With the option "Resolve Workspace artifacts", no need to package the projects before running "jetty-run".
 
Once the launching is successful, check the application by opening from a browser:

        http://localhost:7080/vaadin
