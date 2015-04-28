Sample Axon project with a "low XML diet"
======

The purpose of this sample is to reduce as far as possible the number of configuration files using XML. The result is quite satisfactory: no more than one file, the one to switch Maven in Polyglot mode.

This sample application is build upon the previous [xtend one](../xtend). It tries to reduce the use of XML configuration files with the help of:

* Maven Polyglot with Groovy
* Spring Java Configuration
* Logback Java Configuration

There is a price to pay: configuration data are now dispersed across many code files.

Transformation
-------

#### Maven Polyglot ####

The root project and all the modules are configured with [Polyglot for Maven](https://github.com/takari/maven-polyglot) using [Groovy](http://groovy-lang.org/) as language.

Eclipse Luna with m2e provides an embedded runtime of Maven 3.2.1. Maven Polyglot requires a version 3.3.1 or above of [Maven](https://maven.apache.org/). Such an external Maven runtime must be available on the working station.

Under [Eclipse Luna](https://projects.eclipse.org/releases/luna), [m2e](http://eclipse.org/m2e/) is not aware of [Polyglot](https://github.com/takari/maven-polyglot) yet. At the moment (m2e v. 1.5.1), the only workaround is to use JBoss Tools [m2e-polyglot-poc](https://github.com/jbosstools/m2e-polyglot-poc). This tool automatically generates pom.xml files from the Polyglot ones.

With Maven used in command line, those pom.xml files can be removed, but the parent one (°).

>Note (°). At the moment, there is still a non-blocking issue with Maven multi-module project. With a parent project as module of the root project, the parent pom can't be a polyglot one. At least without using automatic translation with a tool like m2e-polyglot-poc.

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

#### Logback configuration with Java/Xtend ####

Remove any *logback.xml* or *logback.groovy* file from the oms-core. 

An implementation of com.qos.logback.classic.spi.Configurator will be resolved using the ServiceLoader. The main logging properties are set together in a file `META-INF/logback.properties`.

#### JPA configuration with Java/Xtend ####

No more *persistence.xml* file.

Even the transaction type and the entity classes provided by Axon are specified in a file `META-INF/persistence.properties`. Their values are dealt with by an instance of Spring *PersistenceUnitPostProcessor*.

#### Spring configuration with Java/Xtend ####

No more such files as *application-context.xml*, ...

#### Web application configuration ####

Use Servlet 3.0 and annotations to get rid of *web.xml*.

Building
------

JRE 1.8 and Maven 3.3.1 are required.

Under Eclipse:

1. Import the project *lowxmldiet* from GitHub repository, see [here](../README.md)
1. Configure the root project *study-axon-vaadin-lowxmldiet* as Maven project:
  `Configure > Convert to Maven Project`  
1. Import the sub-folders *oms-parent*, *oms-core* and *oms-ui* as Eclipse projects:  
`Import... > General > Existing Projects into Workspace`
1. Build the root project:
        `Run as > Maven build...`  
 and set goal to: clean package.
 
Running
------

#### Under a console ####

##### Headless application #####

The packages must have been built as described above. 

Go to the sub-folder *oms-core* that contains the project *study-axon-vaadin-lowxmldiet-core* and run:

        java -jar target/oms-core-1.0.1-SNAPSHOT-standalone.jar

#### Under Eclipse ####

There is no need to build the packages before running the application as described below.

##### Headless application #####

Create a launch configuration with:

        Run as > Java Application
and:

- set project to: study-axon-vaadin-lowxmldiet-core
- set main class to: com.acme.oms.OrderAppRunner
- set Execution environment to: JavaSE-1.8

##### Web application #####

Create a launch configuration with:

        Run as > Maven Build... 
and:

- set base directory to: ${workspace_loc:/study-axon-vaadin-lowxmldiet-ui}
- set goal to: jetty-run
- check "Resolve Workspace artifacts"
- select a Maven runtime with version 3.3.1 or above
- set Execution environment to: JavaSE-1.8
- add to the list of VM arguments: -Xms1024m -Xmx1024m -Xss2m -Dmaven.multiModuleProjectDirectory=

>Notes.  

>- With the option "Resolve Workspace artifacts", no need to install the package in the local repository before running "jetty-run".
>- Under Eclipse, with Xtend, `jetty:run` needs more space. Then the memory arguments.
>- Maven 3.3.1 under Eclipse requires the argument `-Dmaven.multiModuleProjectDirectory=`, even empty.
 
Once the launching is successful, check the application from a browser by opening:

        http://localhost:7080/vaadin
        

