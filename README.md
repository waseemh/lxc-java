lxc-java
========

Java API for LXC.
Supports most LXC operations.
Note that this library uses LXC's command line interface. It is NOT based on binding of LXC C library to Java.

Requirements
========

- LXC 1.0.0 and above. Note that most functionality should be supported also on older versions.
- JDK6+
- Maven (optional for building source)

Usage
========

API can connect to LXC hosts on local machine or to a remote machine using SSH.

**Connection:**

Local LXC host:

```java
Lxc lxc = new Lxc(); //init local LXC instance
```

Remote LXC host:
```java
	Lxc remoteLxc = new Lxc("192.168.22.9","root","password"); //init SSH connection to remote LXC host
```

LXC commands are available only to previliged users by default. To execute commands as root, you should initialize connection with sudo enabled. For example:
```java
Lxc lxc = new Lxc(true); //init local LXC instance with sudo enabled
```

To terminate connection to LXC host:
```java
lxc.disconnect(); //terminate current connection to LXC host (local/remote)
```

**General Operations:**
```java
lxc.version(); //get LXC version

List<Container> allContainers = lxc.containers(); //return list of all containers

List<Container> runningContainers = lxc.runningContainers(); //return list of running containers
```

**Containers:**

```java
Container container = lxc.container("demo"); //get instance of container with name "demo"
```

Get various information about available containers:
```java
container.state(); //get container state (running, stopped, stopping, frozen, ...)

container.pid(); //get container process id (if running)

container.ip(); //get container ip

container.memoryUsage(); //get container memory usage

container.memoryLimit(); //get container memory limit

container.getCgroup(memory.usage_in_bytes); //get cgroup value
```

Execute operations on available containers:
```java	
container.start(); //start container as a daemon

container.freeze(); //freeze container

container.stop(); //stop running container

container.restart(); //restart container

container.snapshot(); //take a snapshot of container

container.clone("NewContainer") //clone current container with a new name

container.wait(ContainerState.STOPPED, 10); //wait for container to reach a specific state with a timeout.
```

For more available functionality and examples, take a look at the unit tests or explore the API by yourself.	

Building from Sources
========

Maven is used as a build system.
In order to produce a package, run maven command `mvn clean package -DskipTests`.
Tests can be executed using command `mvn test`. 

License
========

Copyright 2019 Waseem Hamshawi

	Licensed under the Apache License, Version 2.0 (the "License");
	you may not use this file except in compliance with the License.
	You may obtain a copy of the License at
	
	   http://www.apache.org/licenses/LICENSE-2.0
	
	Unless required by applicable law or agreed to in writing, software
	distributed under the License is distributed on an "AS IS" BASIS,
	WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
	See the License for the specific language governing permissions and
	limitations under the License.
