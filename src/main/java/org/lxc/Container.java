package org.lxc;

import java.math.BigInteger;

import org.lxc.LxcEnums.BackendStore;
import org.lxc.LxcEnums.ContainerState;

public interface Container {

	 String getName();

	 void setName(String name);

	/**
	 * Get container's current state.
	 * @return container state
	 * @throws LxcException 
	 */
	 ContainerState state() throws LxcException;

	/**
	 * Get container's PID.
	 * If container is not running, -1 will be returned.
	 * @return container PID
	 * @throws LxcException 
	 */
	 int pid() throws LxcException;

	/**
	 * Get container's IP.
	 * If container is not running, null will be returned.
	 * @return container PID
	 * @throws LxcException 
	 */
	 String ip() throws LxcException;

	/**
	 * Run container as a daemon.
	 * @throws LxcException 
	 */
	 void start() throws LxcException;

	/**
	 * Run container as a daemon using a configuration file.
	 * @param configFile path of configuration file
	 * @throws LxcException 
	 */
	 void start(String configFile) throws LxcException;

	/**
	 * Freeze all the container's processes.
	 * @throws LxcException 
	 */
	 void freeze() throws LxcException;

	/**
	 * Thaw all the container's processes.
	 * @throws LxcException 
	 */
	 void unfreeze() throws LxcException;

	/**
	 * Stop the application running inside a container .
	 * @throws LxcException 
	 */
	 void stop() throws LxcException;

	/**
	 * Stop and start the container.
	 * @throws LxcException 
	 */
	 void restart() throws LxcException;

	/**
	 * Wait for container to reach a specific state with a timeout.
	 * @param state desired state
	 * @param timeout wait timeout
	 * @throws LxcException 
	 */
	 boolean wait(ContainerState state, int timeout) throws LxcException;

	 void clone(String destName) throws LxcException;

	 void delete() throws LxcException;

	 void create(String template) throws LxcException;

	 void create(String template, String config) throws LxcException;

	void create(String template, String config, String... templateOptions) throws LxcException;

	void create(String template, String config, BackendStore store) throws LxcException;

	void snapshot() throws LxcException;
	
	void restoreSnapshot(String snapshot, String newName) throws LxcException;

	/**
	 * Destroy the container.
	 * @throws LxcException 
	 */
	 void destroy() throws LxcException;

	/**
	 * Destroy the container.
	 * @param force force to destroy a running container. 
	 * @throws LxcException 
	 */
	 void destroy(boolean force) throws LxcException;

	/**
	 * Set the value of a state-object (e.g., 'cpuset.cpus') in the container's cgroup.
	 * @param stateObject
	 * @param value new state-object value
	 * @throws LxcException 
	 */
	 void setCgroup(String stateObject, String value) throws LxcException;

	/**
	 * Get the value of a state-object (e.g., 'cpuset.cpus') in the container's cgroup.
	 * @param stateObject
	 * @return current value of stateObject
	 * @throws LxcException 
	 */
	 String getCgroup(String stateObject) throws LxcException;

	/**
	 * Get container memory usage in bytes.
	 * @return memory usage in bytes
	 * @throws LxcException 
	 */
	 long memoryUsage() throws LxcException;

	/**
	 * Get container memory limit in bytes.
	 * @return memory limit in bytes
	 * @throws LxcException 
	 */
	 BigInteger memoryLimit() throws LxcException;

	/**
	 * Check if container is currently running.
	 * @return true if container is running, otherwise false.
	 * @throws LxcException 
	 */
	 boolean isRunning() throws LxcException;

	/**
	 * Check if container is currently stopped.
	 * @return true if container is stopped, otherwise false.
	 * @throws LxcException 
	 */
	 boolean isStopped() throws LxcException;

	/**
	 * Check if container is currently starting.
	 * @return true if container is starting, otherwise false.
	 * @throws LxcException 
	 */
	 boolean isStarting() throws LxcException;

	/**
	 * Check if container is currently stopping.
	 * @return true if container is stopping, otherwise false.
	 * @throws LxcException 
	 */
	 boolean isStopping() throws LxcException;

}