Sample Axon project with JPA and Vaadin
=====

Tools: Eclipse with M2Eclipse, Egit, WikiText, ...

Building
------

1. Check out the project:  
`File > Import... > Git > Projects from Git > Clone URI`  
with  
`https://github.com/atao60/study-axon-vaadin`
1. Configure oms-aggregator project as Maven project:
  `Configure > Convert to Maven Project`  
1. Import oms-parent, oms-core and oms-ui as Eclipse projects:  
`Import... > General > Existing Projects into Workspace`
1. Build oms-aggregator:  
`Run as > Maven build...`  
 and set goal to "package"  
 
Running
------

Launch oms-ui:

        Run as > Maven Build... 
and:

- set goal to "jetty-run"
- check "Resolve Workspace artifacts"

>Note. With the option "Resolve Workspace artifacts", no need to package the projects before running "jetty-run".
 
Once the launching is successful, check the application by opening from a browser:

        http://localhost:7080/vaadin

References
------

Original source: [study-axon-vaadin](https://code.google.com/p/study-axon-vaadin/)  

An article using the original code: [Tutorial - Getting started with CQRS and Axon Framework](http://blog.trifork.com/2010/11/12/tutorial-getting-started-with-cqrs-and-axon-framework/)