Sample Axon projects
=====

Purpose
-----

This repository aims to gather together a lot of *up-to-date* details spread out on the Internet about technologies from [Axon Framework](https://code.google.com/p/study-axon-vaadin/) to Maven Polyglot through [Xtend](https://eclipse.org/xtend/) and other ones.

A policy of *Divide and rule* will be used: each main technology will be presented through a new project.

Tools
------

* JDK 1.7/1.8

* [Maven](https://maven.apache.org/) (3.2.1 or 3.3.1)

* [Eclipse](https://eclipse.org/) Luna (SR1 or SR2)

* [Jetty](http://eclipse.org/jetty/) 9.2.10

* m2e 1.5.1 (The Maven Integration for Eclipse (m2eclipse)

* Egit 3.7.0

* WikiText 2.3.2

Key Features
-----

* All the compile/build work is done through Eclipse (m2e). Should you prefer the maven command line, fine.

* Maven Multi-Module with a parent pom as module of the aggregator project.

* Skip Vaadin Widgetset compilation.

* No package installed by those projects in the local m2 repository.

* No specific remote m2 repositories. Maven Central should be enough.

Steps
------

#### [Legacy project](legacy) ####

It's not really the original [study-axon-vaadin](https://code.google.com/p/study-axon-vaadin/):

- the dependencies have been updated but the Vaadin Framework still with a version 6.x,
- the root project has been refactored with e.g. the creation of a Maven module "parent". 

#### [Vaadin 7 project](vaadin7) ####

To upgrade to Vaadin 7 requires many changes by itself. It deserves its own project.

#### [Xtend project](xtend) ####

Use Xtend in place of Java.

#### LowXmlDiet project ####

Maven Polyglot, Spring Java Configuration, ...

TBD

Checkout
------

Under Eclipse:

        File > Import... > Git > Projects from Git > Clone URI
using the URL:

        https://github.com/atao60/study-axon-vaadin
        
and the wizard "Import existing Eclipse projects", not the "Import as general project" one.       
        
Building
------        
        
See the README.md file of each module.        

References
------

An article using the original code: [Tutorial - Getting started with CQRS and Axon Framework](http://blog.trifork.com/2010/11/12/tutorial-getting-started-with-cqrs-and-axon-framework/)