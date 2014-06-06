package org.lxc.internal.connection;

import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintStream;

import org.lxc.LxcCommand;
import org.lxc.LxcException;

import com.jcraft.jsch.Channel;
import com.jcraft.jsch.JSch;
import com.jcraft.jsch.JSchException;
import com.jcraft.jsch.Session;
import com.jcraft.jsch.UserInfo;


public class RemoteSshLxcConnection extends AbstractCliLxcConnection {

	PrintStream ps;

	DataInputStream dataIn;

	Channel channel;

	Session session;

	String endLineStr = ".*#\\s*|.*~\\$\\s*|.*~\\s*|.*\\$\\s*";

	String host;

	String user;

	String password;

	final static int defaultPort = 22;

	int port = defaultPort;

	final static String COMMAND_NOT_FOUND = "command not found";

	/**
	 * @param host
	 * @param port
	 * @param username
	 * @param password
	 */
	public RemoteSshLxcConnection(String host, int port, String username, String password, boolean isSudo) {
		this.host = host;
		this.user = username;
		this.password = password;
		this.isSudo = isSudo;
	}

	/**
	 * @param host
	 * @param username
	 * @param password
	 */
	public RemoteSshLxcConnection(String host, String username, String password) {
		this(host,defaultPort,username,password,false);
	}

	/**
	 * @param host
	 * @param username
	 * @param password
	 * @param isSudo
	 */
	public RemoteSshLxcConnection(String host, String username, String password, boolean isSudo) {
		this(host,defaultPort,username,password,isSudo);
	}

	@Override
	public void connect() throws LxcException {

		JSch shell = new JSch(); 

		try {

			session = shell.getSession(user, host, port);
			session.setUserInfo(new SSHUserInfo(password)); 
			session.connect();

			channel = session.openChannel("shell");
			channel.connect();

			try {
				Thread.sleep(2000);
			} catch (InterruptedException e) {
			}

			dataIn = new DataInputStream(channel.getInputStream());  
			ps = new PrintStream(channel.getOutputStream(),true);

		} catch (JSchException e) {
			throw new LxcException("Unable to connect to host");
		} catch (IOException e) {
			throw new LxcException("Unable to retrieve channel I/O streams");
		}

	}

	@Override
	public void disconnect() throws LxcException {
		try {
			dataIn.close();
		} catch (IOException e) {
			throw new LxcException("Error closing data input stream");
		}
		ps.close();
		channel.disconnect(); 
		session.disconnect(); 
	}

	@Override
	public void execute(LxcCommand command) {
		if(isSudo) {
			ps.println("echo '"+password+"' | sudo -S -p '' " + command.toString());
			//ps.println("sudo -S " + command.toString());
			ps.println(password);
			//ps.println("sudo" + command.toString());
		}
		else {
			ps.println(command.toString());
		}

		ps.flush();
	}

	@Override
	public String getOutput() throws LxcException {
		InputStreamReader inReader= new InputStreamReader(dataIn);
		BufferedReader reader = new BufferedReader(inReader);
		StringBuilder sb = new StringBuilder();
		try {

			String line = reader.readLine(); //skip first line (command)
			System.out.println(line); 
			line = reader.readLine();
			while(!line.matches(endLineStr)) { 
				System.out.println(line); 
				sb.append(line);
				sb.append('\n');
				line = reader.readLine(); 
			}

		} catch(IOException e) {
			throw new LxcException("Error while reading command output.",e);
		}

		return sb.toString().trim();
	}

	@Override
	public String getErrorOutput() {
		return null;
	}

	static class SSHUserInfo implements UserInfo { 

		private String password; 

		SSHUserInfo(String password) { 
			this.password = password; 
		} 

		public String getPassphrase() { 
			return null; 
		} 

		public String getPassword() { 
			return password; 
		} 

		public boolean promptPassword(String arg0) { 
			return true; 
		} 

		public boolean promptPassphrase(String arg0) { 
			return true; 
		} 

		public boolean promptYesNo(String arg0) { 
			return true; 
		} 

		public void showMessage(String arg0) { 
			System.out.println(arg0); 
		} 
	} 


}
