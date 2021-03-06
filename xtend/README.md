Sample Axon project with JPA, Vaadin 7 and Xtend
======

This sample application is build upon the previous [*vaadin7* one](../vaadin7), but it uses [Xtend](https://eclipse.org/xtend/) in place of [Java](https://www.java.com).

Building
------

Under Eclipse:

1. Configure the root project *study-axon-vaadin-xtend* as Maven project:
  `Configure > Convert to Maven Project`  
1. Import the sub-folders *oms-parent*, *oms-core* and *oms-ui* as Eclipse projects:  
`Import... > General > Existing Projects into Workspace`
1. Build the root project:  
`Run as > Maven build...`  
 and set goal to "clean package"  
 
Running
------

Launch the web application from project *study-axon-vaadin-xtend-ui*:

        Run as > Maven Build... 
and:

- set goal to "jetty-run"
- check "Resolve Workspace artifacts"

>Note. With the option "Resolve Workspace artifacts", no need to package the projects before running "jetty-run".
 
Once the launching is successful, check the application by opening, from a browser:

            http://localhost:7080/vaadin
        
>Note. Under Eclipse, jetty:run needs more space. Add to the launch configuration something like:  
>>JRE > VM arguments: `-XX:MaxPermSize=256m -Xms1024m -Xmx1024m -Xss2m`
