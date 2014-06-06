lxc-java
========

Java API for LXC.
Supports most LXC operations.
Note that this library uses LXC's command line interface. It is NOT based on binding of LXC C library to Java.

Usage
========

API can connect to LXC host on local machine or to a remote machine using SSH.

**Connection Initialization:**

Local LXC host:

	Lxc lxc = new Lxc(); //init local LXC instance

Remote LXC host:

	Lxc remoteLxc = new Lxc("192.168.22.9","root","password"); //init SSH connection to remote LXC host

- LXC commands are available only to previliged users by default. To execute commands as a super user, you should set initliaze connection with sudo support.

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

The best way to explore all operations is to actually use the API.