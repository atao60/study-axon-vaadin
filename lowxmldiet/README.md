Sample Axon project with a "low XML diet"
======

This sample application is build upon the previous [xtend one](../xtend). It tries to reduce the use of XML configuration files with the help of:

* Maven Polyglot with Groovy
* Spring Java Configuration

Transformation
-------

#### Maven Polyglot ####

The root project and all the modules are configured with [Polyglot for Maven](https://github.com/takari/maven-polyglot) using [Groovy](http://groovy-lang.org/) as language.

Eclipse Luna with m2e provides an embedded runtime of Maven 3.2.1. Maven Polyglot requires a version 3.3.1 or above of [Maven](https://maven.apache.org/). Such an external Maven runtime must be available on the working station.

Under [Eclipse Luna](https://projects.eclipse.org/releases/luna), [m2e](http://eclipse.org/m2e/) is not aware of [Polyglot](https://github.com/takari/maven-polyglot) yet. At the moment (m2e v. 1.5.1), the only workaround is to use JBoss Tools [m2e-polyglot-poc](https://github.com/jbosstools/m2e-polyglot-poc). This tool automatically generates pom.xml files from the Polyglot ones.

With Maven used in command line, those pom.xml files can be removed, but the parent one (°).

>Note (°). At the moment, there is still a non-blocking issue with Maven multi-module project. With a parent project as module of the root project, the parent pom can't be a polyglot one. At least without using automatic translation with a tool like Maven Polyglot.

One way to install m2e-polyglot-poc under Eclipse is to use the update site:
         
         http://dl.bintray.com/jbosstools/m2e-polyglot-poc/
         
> Note. This tool requires that a pom.xml file is present before editing the polyglot pom file. The pom.xml must be valid with at least a GAV, even if with non empty arbitrary values.  

With an existing Maven project, the most straightforward way to start is to get a groovy pom from the pom.xml file using the tool provided by [Takari](http://takari.io/), i.e. :

         mvn io.takari.polyglot:polyglot-translate-plugin:translate -Dinput=pom.xml -Doutput=pom.groovy

>Notes:  
- This tool must be run from each project, i.e. the root one and each of the modules.   
- Don't forget to add the .mvn/extensions.xml file in the root project.

Then each Maven Build launch configuration has to specify:

* on the tab "Main", Maven Runtime: `MAVEN (External {your maven path} {3.3.1 or above})`
* on the tab "JRE", VM Arguments: `-Dmaven.multiModuleProjectDirectory=`

#### Logback configuration with Groovy ####

Replace the oms-core logback.xml file by a logback.groovy one. That's all!

#### Spring configuration with Java/Xtend ####

No more such files as application-context.xml...

JPA still needs a file persistence.xml to deal with Axon entities.

#### Web application configuration ####

TBD

Building
------

Under Eclipse:

1. Configure the root project *study-axon-vaadin-lowxmldiet* as Maven project:
  `Configure > Convert to Maven Project`  
1. Import the sub-folders *oms-parent*, *oms-core* and *oms-ui* as Eclipse projects:  
`Import... > General > Existing Projects into Workspace`
1. Build the root project:  
`Run as > Maven build...`  
 and set goal to "clean package"  
 
Running
------

Launch the web application from project *study-axon-vaadin-lowxmldiet-ui*:

        Run as > Maven Build... 
and:

- set goal to "jetty-run"
- check "Resolve Workspace artifacts"

>Note. With the option "Resolve Workspace artifacts", no need to package the projects before running "jetty-run".
 
Once the launching is successful, check the application by opening from a browser:

            http://localhost:7080/vaadin
        
>Note. Under Eclipse, with Xtend, jetty:run needs more space. Add to the launch configuration something like:  
>>>JRE > VM arguments: `-XX:MaxPermSize=256m -Xms1024m -Xmx1024m -Xss2m`
