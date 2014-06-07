package org.lxc.internal;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.lxc.Container;
import org.lxc.ILxc;
import org.lxc.LxcCommand;
import org.lxc.LxcEnums.ContainerListFilter;
import org.lxc.LxcException;
import org.lxc.connection.LxcConnection;

public class LxcCliImpl implements ILxc{
	
	/**
	 * Holds connection to LXC host for running commands.
	 */
	private LxcConnection connection;
	
	
	public LxcCliImpl(LxcConnection connection) {
		this.connection = connection;
	}

	/**
	 * Return all containers based on a filter.
	 * @param filter
	 * @return list of containers filtered by a given filter.
	 * null is returned when no containers available.
	 * @throws LxcException 
	 */
	public List<Container> containers(ContainerListFilter filter) throws LxcException {
		
		LxcCommand command = new LxcCommandCliImpl("ls");
		
		if(!filter.equals(ContainerListFilter.All))
			command.addArg("--"+filter);
		
		connection.execute(command);
		
		String output = connection.getOutput();
		
		if(output.isEmpty())
			return null;
		
		List<String> containerNames = Arrays.asList(output.split("\n|\\s+"));
		
		List<Container> containers = new ArrayList<Container>();
		
		for(String containerName : containerNames) {
			containers.add(new ContainerCliImpl(connection,containerName));
		}
		
		return containers;
	}
	
	/**
	 * Return all containers existing on the system.
	 * @return list of containers
	 * @throws LxcException 
	 */
	public List<Container> containers() throws LxcException {
		return this.containers(ContainerListFilter.All);
	}
	
	/**
	 * Return all active containers existing on the system.
	 * @return list of containers.
	 * @throws LxcException 
	 */
	public List<Container> activeContainers() throws LxcException {
		return this.containers(ContainerListFilter.Active);
	}
	
	/**
	 * Return all running containers existing on the system.
	 * @return list of running containers.
	 * @throws LxcException 
	 */
	public List<Container> runningContainers() throws LxcException {
		return this.containers(ContainerListFilter.Running);
	}
	
	/**
	 * Return all stopped containers existing on the system.
	 * @return list of stopped containers.
	 * @throws LxcException 
	 */
	public List<Container> stoppedContainers() throws LxcException {
		return this.containers(ContainerListFilter.Stopped);
	}
	
	/**
	 * Return all frozen containers existing on the system.
	 * @return list of frozen containers.
	 * @throws LxcException 
	 */
	public List<Container> frozenContainers() throws LxcException {
		return this.containers(ContainerListFilter.Frozen);
	}
	
	/**
	 * Get container by its name
	 * @param name
	 * @return container instance
	 * @throws LxcException 
	 */
	public Container container(String name) throws LxcException {
		return new ContainerCliImpl(connection,name);
	}
	
	/**
	 * Return version of LXC
	 * @return lxc version
	 * @throws LxcException 
	 */
	public String version() throws LxcException {
		LxcCommand command = new LxcCommandCliImpl("info");
		command.addArg("--version");
		connection.execute(command);
		return connection.getOutput();
	}

	@Override
	public void connect() throws LxcException {
		connection.connect();
	}

	@Override
	public void disconnect() throws LxcException {
		connection.disconnect();
	}

}
