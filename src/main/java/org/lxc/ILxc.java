package org.lxc;

import java.util.List;

import org.lxc.LxcEnums.ContainerListFilter;

public interface ILxc{
	
	/**
	 * Connect to Lxc host
	 * @throws LxcException 
	 */
	void connect() throws LxcException;
	
	/**
	 * Disconnect from Lxc host
	 * @throws LxcException 
	 */
	void disconnect() throws LxcException;

	/**
	 * Return all containers based on a filter.
	 * @param filter
	 * @return list of containers filtered by a given filter.
	 * null is returned when no containers available.
	 * @throws LxcException 
	 */
	List<Container> containers(ContainerListFilter filter) throws LxcException;

	/**
	 * Return all containers existing on the system.
	 * @return list of containers
	 * @throws LxcException 
	 */
	List<Container> containers() throws LxcException;

	/**
	 * Return all active containers existing on the system.
	 * @return list of containers.
	 * @throws LxcException 
	 */
	List<Container> activeContainers() throws LxcException;

	/**
	 * Return all running containers existing on the system.
	 * @return list of running containers.
	 * @throws LxcException 
	 */
	List<Container> runningContainers() throws LxcException;

	/**
	 * Return all stopped containers existing on the system.
	 * @return list of stopped containers.
	 * @throws LxcException 
	 */
	List<Container> stoppedContainers() throws LxcException;

	/**
	 * Return all frozen containers existing on the system.
	 * @return list of frozen containers.
	 * @throws LxcException 
	 */
	List<Container> frozenContainers() throws LxcException;

	/**
	 * Get container by its name
	 * @param name
	 * @return container instance
	 * @throws LxcException 
	 */
	Container container(String name) throws LxcException;

	/**
	 * Return version of LXC
	 * @return lxc version
	 * @throws LxcException 
	 */
	String version() throws LxcException;

}