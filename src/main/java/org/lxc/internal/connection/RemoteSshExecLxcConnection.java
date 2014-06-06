package org.lxc.internal.connection;

import java.io.IOException;
import java.io.InputStream;

import org.lxc.LxcCommand;
import org.lxc.LxcException;

import com.jcraft.jsch.ChannelExec;
import com.jcraft.jsch.JSch;
import com.jcraft.jsch.JSchException;
import com.jcraft.jsch.Session;
import com.jcraft.jsch.UserInfo;

public class RemoteSshExecLxcConnection extends AbstractCliLxcConnection {

	JSch jsch;

	ChannelExec channel;

	Session session;

	String host;

	String user;

	String password;

	final static int defaultPort = 22;

	int port = defaultPort;

	InputStream in;

	InputStream es;

	String output;

	String errorOutput;

	/**
	 * @param host
	 * @param port
	 * @param username
	 * @param password
	 */
	public RemoteSshExecLxcConnection(String host, int port, String username, String password, boolean isSudo) {
		this.host = host;
		this.port = port;
		this.user = username;
		this.password = password;
		this.isSudo = isSudo;
	}

	/**
	 * @param host
	 * @param username
	 * @param password
	 */
	public RemoteSshExecLxcConnection(String host, String username, String password) {
		this(host,defaultPort,username,password,false);
	}

	/**
	 * @param host
	 * @param username
	 * @param password
	 * @param isSudo
	 */
	public RemoteSshExecLxcConnection(String host, String username, String password, boolean isSudo) {
		this(host,defaultPort,username,password,isSudo);
	}

	@Override
	public void connect() throws LxcException {
		try {
			jsch = new JSch();
			session=jsch.getSession(user, host, port);
			session.setUserInfo(new SSHUserInfo(password)); 
			session.connect();
		}
		catch (JSchException e) {
			throw new LxcException("Error while connecting to SSH service.",e);
		}

	}

	@Override
	public void disconnect() {
		session.disconnect();

	}

	@Override
	public void execute(LxcCommand command) throws LxcException {
		try {
			channel=(ChannelExec) session.openChannel("exec");
		} catch (JSchException e) {
			throw new LxcException("Error while opening SSH channel.",e);
		}
		((ChannelExec)channel).setCommand("sudo " + command.toString());
		//((ChannelExec)channel).setCommand("echo '"+password+"' | sudo -S -p '' " + command.toString());
		channel.setInputStream(null);
		((ChannelExec)channel).setErrStream(System.err);

		try {
			channel.connect();
		} catch (JSchException e) {
			throw new LxcException("Error while connecting to SSH channel.",e);
		}

		try {
			output = readOutputFromInputStream(channel.getInputStream());
			errorOutput = readOutputFromInputStream(channel.getErrStream());
			exitValue = channel.getExitStatus();
			if(exitValue>0) {
				throw new LxcException("Error in executing command.");
			}
		} catch (IOException e) {
			throw new LxcException("Error reading channel input stream.",e);
		}

		channel.disconnect();
	}

	private String readOutputFromInputStream(InputStream in) {
		StringBuilder sb = new StringBuilder();

		try {

			byte[] tmp=new byte[1024];
			while(true){
				while(in.available()>0){
					int i=in.read(tmp, 0, 1024);
					if(i<0)break;
					sb.append((new String(tmp, 0, i)));
				}
				if(channel.isClosed()){
					if(in.available()>0) continue;
					//System.out.println("exit-status: "+channel.getExitStatus());
					break;
				}
				try{Thread.sleep(1000);}catch(Exception ee){}
			}
		} catch (Exception e) {}

		return sb.toString();
	}

	@Override
	public String getOutput() {
		return output.trim();

	}

	@Override
	public String getErrorOutput() {
		return errorOutput.trim();
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
