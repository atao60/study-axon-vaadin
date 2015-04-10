Sample Axon project with JPA and Vaadin 7
======

This sample application is build upon the previous [*legacy* one](../legacy) but it uses Vaadin 7 in place of Vaadin 6.

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

Launch the web application from project study-axon-vaadin-v7-ui:

        Run as > Maven Build... 
and:

- set goal to "jetty-run"
- check "Resolve Workspace artifacts"

>Note. With the option "Resolve Workspace artifacts", no need to package the projects before running "jetty-run".
 
Once the launching is successful, check the application by opening from a browser:

        http://localhost:7080/vaadin
