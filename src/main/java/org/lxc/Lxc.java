package org.lxc;

import java.util.List;

import org.lxc.LxcEnums.ContainerListFilter;
import org.lxc.connection.LxcConnection;
import org.lxc.internal.LxcCliImpl;
import org.lxc.internal.connection.LocalLxcConnection;
import org.lxc.internal.connection.RemoteSshExecLxcConnection;

/**
 * Main API class which delegates to main LXC instance and exposes main functionality to end-user.
 * @author Waseem Hamshawi (hamshawi@gmail.com)
 *
 */
public class Lxc implements ILxc{
	
	private ILxc lxc;
	
	/**
	 * Initialize LXC instance with a remote SSH connection to an LXC host.
	 * @param host
	 * @param username
	 * @param password
	 * @param sudo
	 * @throws LxcException 
	 */
	public Lxc(String host,String username,String password,boolean sudo) throws LxcException {
		LxcConnection connection = new RemoteSshExecLxcConnection(host,username,password,sudo);
		connection.connect();
		lxc = new LxcCliImpl(connection);
	}
	
	/**
	 * Initialize LXC instance with a remote SSH connection to an LXC host with non default port.
	 * @param host
	 * @param username
	 * @param password
	 * @param sudo
	 * @param port
	 * @throws LxcException 
	 */
	public Lxc(String host,int port,String username,String password,boolean sudo) throws LxcException {
		LxcConnection connection = new RemoteSshExecLxcConnection(host,port,username,password,sudo);
		connection.connect();
		lxc = new LxcCliImpl(connection);
	}
	
	/**
	 * Initialize LXC instance with a remote SSH connection to an LXC host.
	 * @param host
	 * @param username
	 * @param password
	 * @throws LxcException 
	 */
	public Lxc(String host,String username,String password) throws LxcException {
		this(host,username,password,false);
	}

	/**
	 * Initialize LXC instance with a local LXC host connection.
	 */
	public Lxc() {
		this(false);
	}
	
	/**
	 * Initialize LXC instance with a local LXC host connection.
	 */
	public Lxc(boolean isSudo) {
		LxcConnection connection = new LocalLxcConnection(isSudo);
		lxc = new LxcCliImpl(connection);
	}

	@Override
	public List<Container> containers(ContainerListFilter filter) throws LxcException {
		return lxc.containers(filter);
	}

	@Override
	public List<Container> containers() throws LxcException {
		return lxc.containers();
	}

	@Override
	public List<Container> activeContainers() throws LxcException {
		return lxc.activeContainers();
	}

	@Override
	public List<Container> runningContainers() throws LxcException {
		return lxc.runningContainers();
	}

	@Override
	public List<Container> stoppedContainers() throws LxcException {
		return lxc.stoppedContainers();
	}

	@Override
	public List<Container> frozenContainers() throws LxcException {
		return lxc.frozenContainers();
	}

	@Override
	public Container container(String name) throws LxcException {
		return lxc.container(name);
	}

	@Override
	public String version() throws LxcException {
		return lxc.version();
	}

	@Override
	public void connect() throws LxcException {
		lxc.connect();
	}

	@Override
	public void disconnect() throws LxcException {
		lxc.disconnect();
	}

}