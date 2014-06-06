package org.lxc.connection;

import org.lxc.LxcCommand;
import org.lxc.LxcException;

/**
 * Connection to LXC host
 * @author Waseem Hamshawi (hamshawi@gmail.com)
 *
 */
public interface LxcConnection {
	
	/**
	 * Connect to LXC host
	 * @throws LxcException 
	 * @throws Exception 
	 */
	void connect() throws LxcException;
	
	/**
	 * Disconnect from LXC host
	 * @throws LxcException 
	 * @throws Exception 
	 */
	void disconnect() throws LxcException;
	
	/**
	 * Execute LXC command on host
	 * @param command
	 * @return output of executed command on LXC host
	 * @throws LxcException 
	 * @throws Exception 
	 */
	void execute(LxcCommand command) throws LxcException;
	
	/**
	 * Get standard output of executed command
	 * @return command output
	 * @throws LxcException 
	 */
	String getOutput() throws LxcException;
	
	/**
	 * Get error output of executed command
	 * @return command output
	 */
	String getErrorOutput();

}
