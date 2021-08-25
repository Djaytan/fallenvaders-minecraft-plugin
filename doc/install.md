# Installation

## Prerequisites

For process installation, you must have installed in your environment these
following programs:
* [Java 16](https://www.oracle.com/java/technologies/javase-jdk16-downloads.html) or above
* [Maven 3](https://maven.apache.org/download.cgi#)
* An IDE *([IntelliJ IDEA](https://www.jetbrains.com/fr-fr/idea/) recommended)*

## Initialization

After cloning sources, run this command at the root folder of the project:
```shell
$ mvn initialize
```
Then run the compilation command presented below.

## Compile project

After each code update, run this command:
```shell
$ mvn clean install
```

## Launch server

For launching the test server, you have two options:
* Running from the corresponding script (`start.bat` on Windows, `start.sh` on Linux)
* Launch a configuration from your IDE

### Launch server from IDE configuration

> Configuration name: *test-server*  
> Classpath (or -cp): *test-server*  
> Main class: *fr.fallenvaders.minecraft.test_server.FallenVadersTestServer*  
> Working directory: *path\to\mc-fallenvaders\test-server*
> Shorten command line: *JAR manifest*
