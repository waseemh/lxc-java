package org.lxc.internal.connection;

import org.lxc.connection.LxcConnection;

/**
 * @author Waseem Hamshawi (hamshawi@gmail.com)
 *
 */
public abstract class AbstractCliLxcConnection implements LxcConnection {
	
	protected boolean isSudo;
	
	protected int exitValue;

	public boolean isSudo() {
		return isSudo;
	}

	public void setSudo(boolean isSudo) {
		this.isSudo = isSudo;
	}

	public int getExitValue() {
		return exitValue;
	}


}