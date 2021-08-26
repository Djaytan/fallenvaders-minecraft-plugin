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

## Debug mode

For debug mode, it's recommended to clone the previously created launch configuration
and rename it "test-server (debug)".
Then, add to "Program arguments" this one: `debug`. It tells the test-server
to run in debug mode.

Then, create a "Remote JVM Debug" launch config with these params:

> Configuration name: *test-server-remote-debug*  
> Debugger mode: *Listen to remote JVM*  
> Transport: *Socket*  
> Host: *localhost*  
> Port: *5005* (or what you want that isn't the server port or other
    one already in use)  
> Classpath: *test-server*

You may obtain a generated "Command line arguments for JVM" like this one:
```
-agentlib:jdwp=transport=dt_socket,server=n,address=DESKTOP-TOT51U5:5005,suspend=y,onthrow=<FQ exception class name>,onuncaught=<y/n>
```

Now, you can run by pressing on the hammer button.

***Important:** First, run the `test-server-remote-debug`, then only after run
the `test-server (debug)` launch config.*
