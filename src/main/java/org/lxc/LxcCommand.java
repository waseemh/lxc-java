package org.lxc;


/**
 * Class for representing an LXC command and its parameters. 
 * @author Waseem Hamshawi (hamshawi@gmail.com)
 *
 */
public interface LxcCommand {
	
	void addArgs(String... args);
	
	void addArg(String arg);
	

}