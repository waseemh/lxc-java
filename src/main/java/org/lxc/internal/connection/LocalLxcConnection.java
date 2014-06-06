package org.lxc.internal.connection;

import java.io.ByteArrayOutputStream;
import java.io.IOException;

import org.lxc.LxcCommand;
import org.lxc.LxcException;
import org.lxc.io.StreamGobbler;

/**
 * LXC connection implementation for local LXC host.
 * Local host is the LXC host which this program is executed from.
 * @author Waseem Hamshawi (hamshawi@gmail.com)
 *
 */
public class LocalLxcConnection extends AbstractCliLxcConnection {

	/**
	 * Error output stream
	 */
	ByteArrayOutputStream eos;

	/**
	 * Standard output stream
	 */
	ByteArrayOutputStream oos;

	Process proc;

	public LocalLxcConnection(boolean isSudo) {
		this.eos = new ByteArrayOutputStream();
		this.oos = new ByteArrayOutputStream();
		this.isSudo = isSudo;
	}

	public LocalLxcConnection() {
		this(false);
	}

	public void connect() {

	}

	public void disconnect() {
		proc.destroy();
	}

	public void execute(LxcCommand command) throws LxcException {

		//clean output streams
		eos.reset();
		oos.reset();

		Runtime rt = Runtime.getRuntime();

		String commandStr = isSudo ? "sudo " + command.toString() : command.toString();

		try {
			proc = rt.exec(commandStr);
		} catch (IOException e) {
			throw new LxcException("Unable to execute command",e);
		}

		StreamGobbler errorGobbler = new StreamGobbler(proc.getErrorStream(), eos);
		StreamGobbler outputGobbler = new StreamGobbler(proc.getInputStream(), oos);

		errorGobbler.start();
		outputGobbler.start();

		try {
			exitValue = proc.waitFor();
		} catch (InterruptedException e) {
			throw new LxcException("Process executing LXC command was interupted",e);
		}

		try {
			eos.flush();
			oos.flush();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}


		if(exitValue>0) {
			throw new LxcException("Error in executing command. Error:"+getErrorOutput());
		}

	}

	public String getOutput() {
		return oos.toString().trim();
	}

	public String getErrorOutput() {
		return eos.toString().trim();
	}

}
