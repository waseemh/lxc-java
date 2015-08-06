package org.lxc.internal;

import java.math.BigInteger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.lxc.Container;
import org.lxc.LxcCommand;
import org.lxc.LxcEnums.BackendStore;
import org.lxc.LxcEnums.ContainerState;
import org.lxc.LxcException;
import org.lxc.connection.LxcConnection;
import org.lxc.internal.connection.AbstractCliLxcConnection;

public class ContainerCliImpl implements Container{
	
	/**
	 * Holds connection to LXC host for running container commands.
	 */
	private LxcConnection connection;

	/**
	 * Name of container (unique)
	 */
	private String name;

	public ContainerCliImpl(LxcConnection connection, String name) {
		this.connection = connection;
		this.name = name;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	/**
	 * Get container's current state.
	 * @return container state
	 * @throws LxcException 
	 */
	public ContainerState state() throws LxcException {
		String output = executeContainerCommand("info","-s");
		Pattern pattern = Pattern.compile("(state|State):\\s+\\w+");
		Matcher m = pattern.matcher(output);
		if (m.find()) {
			String matched = m.group(0);
			return ContainerState.valueOf(matched.split(":")[1].trim());
		}
		return ContainerState.UNKNOWN;
	}

	/**
	 * Get container's PID.
	 * If container is not running, -1 will be returned.
	 * @return container PID
	 * @throws LxcException 
	 */
	public int pid() throws LxcException {
		String output = executeContainerCommand("info","-p");
		Pattern pattern = Pattern.compile("(pid|PID):\\s+\\w+");
		Matcher m = pattern.matcher(output);
		if (m.find()) {
			String matched = m.group(0);
			return Integer.valueOf(matched.split(":")[1].trim());
		}
		return -1;
	}
	
	/**
	 * Get container's IP.
	 * If container is not running, null will be returned.
	 * @return container PID
	 * @throws LxcException 
	 */
	public String ip() throws LxcException {
		String output = executeContainerCommand("info","-i");
		Pattern pattern = Pattern.compile("IP:\\s+.+");
		Matcher m = pattern.matcher(output);
		if (m.find()) {
			String matched = m.group(0);
			return matched.split(":")[1].trim();
		}
		return null;
	}

	/**
	 * Run container as a daemon.
	 * @throws LxcException 
	 */
	public void start() throws LxcException {
		start(true,null);
	}

	/**
	 * Run container
	 * @param daemon
	 * @param configFile
	 * @throws LxcException 
	 */
	private void start(boolean daemon,String configFile) throws LxcException {
		String[] args = {"",""};
		if(daemon)
			args[0]="-d";
		if(configFile!=null)
			args[1]="-f" + " " + configFile;
		executeContainerCommand("start",args);
	}

	/**
	 * Run container as a daemon using a configuration file.
	 * @param configFile path of configuration file
	 * @throws LxcException 
	 */
	public void start(String configFile) throws LxcException {
		start(true,configFile);
	}

	/**
	 * Freeze all the container's processes.
	 * @throws LxcException 
	 */
	public void freeze() throws LxcException {
		executeContainerCommand("freeze");
	}

	/**
	 * Thaw all the container's processes  
	 * @throws LxcException 
	 */
	public void unfreeze() throws LxcException {
		executeContainerCommand("unfreeze");
	}

	/**
	 * Stop the application running inside a container  
	 * @throws LxcException 
	 */
	public void stop() throws LxcException {
		executeContainerCommand("stop");
	}

	/**
	 * Stop and start the container
	 * @throws LxcException 
	 */
	public void restart() throws LxcException {
		stop();
		start();
	}

	/**
	 * Wait for container to reach a particular state.
	 * @param state State to wait for
	 * @param timeout Timeout in seconds
	 * @throws LxcException 
	 */
	public boolean wait(ContainerState state, int timeout) throws LxcException {
		try { 
			executeContainerCommand("wait","-s",state.toString(),"-t",String.valueOf(timeout));
		}
		catch (LxcException e) {
			if((((AbstractCliLxcConnection) connection).getExitValue() == 1))
				return false;
		}
		return true;
	}

	public void clone(String destName) throws LxcException {
		LxcCommand command = new LxcCommandCliImpl("clone");
		command.addArgs("-o",this.name);
		command.addArgs("-n",destName);
		connection.execute(command);
	}

	public void delete() throws LxcException {
		this.destroy();
	}

	public void create(String template) throws LxcException {
		executeContainerCommand("create","-t",template);
	}

	public void create(String template,String config) throws LxcException {
		executeContainerCommand("create","-f",config);
	}

	public void create(String template, String config, String... templateOptions) throws LxcException {
		executeContainerCommand("create","-f",config,"-t",template);
	}

	public void create(String template, String config, BackendStore store) throws LxcException {
		executeContainerCommand("create","-t",template,"-f",config,"-B",store.toString());
	}

	public void snapshot() throws LxcException {
		executeContainerCommand("snapshot");
	}

	/**
	 * destroy the container
	 * @throws LxcException 
	 */
	public void destroy() throws LxcException {
		destroy(false);
	}

	/**
	 * destroy the container
	 * @param force force to destroy a running container. 
	 * @throws LxcException 
	 */
	public void destroy(boolean force) throws LxcException {
		if(force)
			executeContainerCommand("destroy","-f");
		else executeContainerCommand("destroy");
	}

	/**
	 * Set the value of a state-object (e.g., 'cpuset.cpus') in the container's cgroup.
	 * @param stateObject
	 * @param value new state-object value
	 * @throws LxcException 
	 */
	public void setCgroup(String stateObject, String value) throws LxcException {
		executeContainerCommand("cgroup",stateObject,value);
	}

	/**
	 * Get the value of a state-object (e.g., 'cpuset.cpus') in the container's cgroup.
	 * @param stateObject
	 * @return current value of stateObject
	 * @throws LxcException 
	 */
	public String getCgroup(String stateObject) throws LxcException {
		String output = executeContainerCommand("cgroup",stateObject);
		return output;
	}
	
	/**
	 * Get container memory usage in bytes
	 * @return memory usage in bytes
	 * @throws LxcException 
	 * @throws NumberFormatException 
	 */
	public long memoryUsage() throws LxcException {
		return Long.parseLong(getCgroup("memory.usage_in_bytes"));
	}
	
	/**
	 * Get container memory limit in bytes
	 * @return memory limit in bytes
	 * @throws LxcException 
	 * @throws  
	 */
	public BigInteger memoryLimit() throws LxcException {
		return new BigInteger(getCgroup("memory.limit_in_bytes"));
	}
	
	public boolean isRunning() throws LxcException {
		return checkState(ContainerState.RUNNING);
	}
	
	public boolean isStopped() throws LxcException {
		return checkState(ContainerState.STOPPED);
	}
	
	public boolean isStarting() throws LxcException {
		return checkState(ContainerState.STARTING);
	}
	
	public boolean isStopping() throws LxcException {
		return checkState(ContainerState.STOPPING);
	}
	
	private boolean checkState(ContainerState state) throws LxcException {
		return state().equals(state);
	}

	private String executeContainerCommand(String commandString, String...args) throws LxcException {
		LxcCommand lxcCommand = new LxcCommandCliImpl(commandString);
		lxcCommand.addArgs("-n",this.name);
		lxcCommand.addArgs(args);

		connection.execute(lxcCommand);

		return connection.getOutput();
	}

	public void restoreSnapshot(String snapshot, String newName) throws LxcException {
		executeContainerCommand("snapshot","-r",snapshot,newName);
	}


}