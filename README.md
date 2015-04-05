Sample Axon project with JPA and Vaadin
=====

Tools: Eclipse with M2Eclipse, Egit, WikiText, ...

Building
------

1. Check out the project:  
`File > Import... > Git > Projects from Git > Clone URI`
2. Configure oms-parent project as Maven project:  
`Configure > Convert to Maven Project`
3. Import oms-core and oms-ui as Eclipse projects:  
`Import... > General > Existing Projects into Workspace`
3. Build oms-parent:  
`Run as > Maven build...`  
 and set goal to "package"  
 
Running
------

Launch oms-ui:

        Run as > Maven Build... 
and:

- set goal to "jetty-run"
- check "Resolve Workspace artifacts"
 
Once the launching is successful, check the application by opening from a browser:

        http://localhost:7080/vaadin

References
------

Original source: [study-axon-vaadin](https://code.google.com/p/study-axon-vaadin/)  

An article using the original code: [Tutorial - Getting started with CQRS and Axon Framework](http://blog.trifork.com/2010/11/12/tutorial-getting-started-with-cqrs-and-axon-framework/)