lxc-java
========

Java API for LXC.
Supports most LXC operations.
Note that this library uses LXC's command line interface. It is NOT based on binding of LXC C library to Java.

Requirements
========

- LXC 1.0.0 and above. Note that most functionality should be supported also on older versions.

Usage
========

API can connect to LXC hosts on local machine or to a remote machine using SSH.

**Connection:**

Local LXC host:

	Lxc lxc = new Lxc(); //init local LXC instance

Remote LXC host:

	Lxc remoteLxc = new Lxc("192.168.22.9","root","password"); //init SSH connection to remote LXC host

LXC commands are available only to previliged users by default. To execute commands as root, you should initialize connection with sudo enabled. For example:

	Lxc lxc = new Lxc(true); //init local LXC instance with sudo enabled
	
To terminate connection to LXC host:

	lxc.disconnect(); //terminate current connection to LXC host (local/remote)

**General Operations:**

	lxc.version(); //get LXC version

	List<Container> allContainers = lxc.containers(); //return list of all containers

	List<Container> runningContainers = lxc.runningContainers(); //return list of running containers

**Containers:**

	Container container = lxc.container("demo"); //get instance of container with name "demo"
	
	container.state() //get container state
	
	container.pid() //get container process id (if running)
	
	container.ip() //get container ip
	
	container.memoryUsage() //get container memory usage
	
	container.memoryLimit() //get container memory limit
	
	container.start() //start container as a daemon
	
	container.freeze() //freeze container
	

Building from Sources
========

Maven is used as a build system.
In order to produce a package, run maven command `mvn clean package -DskipTests`.
Tests can be executed using command `mvn test`. 

License
========

Copyright 2014 Waseem Hamshawi

	Licensed under the Apache License, Version 2.0 (the "License");
	you may not use this file except in compliance with the License.
	You may obtain a copy of the License at
	
	   http://www.apache.org/licenses/LICENSE-2.0
	
	Unless required by applicable law or agreed to in writing, software
	distributed under the License is distributed on an "AS IS" BASIS,
	WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
	See the License for the specific language governing permissions and
	limitations under the License.