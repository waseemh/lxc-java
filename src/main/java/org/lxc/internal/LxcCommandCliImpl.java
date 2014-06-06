package org.lxc.internal;

import java.util.ArrayList;
import java.util.List;

import org.lxc.LxcCommand;

/**
 * Class for representing an LXC CLI command and its arguments. 
 * LXC commands are usually prefixed with "lxc" string.
 * @author Waseem Hamshawi (hamshawi@gmail.com)
 *
 */
public class LxcCommandCliImpl implements LxcCommand{
	
	private final static String LXC_COMMAND_PREFIX="lxc-";
	
	/**
	 * String representation of LXC command
	 */
	String command;
	
	/**
	 * Command arguments
	 */
	List<String> args;
	
	public LxcCommandCliImpl(String string) {
		this.command = string;
		this.args = new ArrayList<String>();
	}

	public String getCommand() {
		return command;
	}
	
	public void setCommand(String command) {
		this.command = command;
	}
	
	public List<String> getArgs() {
		return args;
	}

	public void setArgs(List<String> args) {
		this.args = args;
	}

	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append(LXC_COMMAND_PREFIX);
		sb.append(this.command);
		sb.append(" ");
		sb.append(args());
		return sb.toString();
	}
	
	private String args() {
		
		StringBuilder argChain = new StringBuilder();
		
		for(String arg : args) {
			argChain.append(arg);
			argChain.append(" ");
		}
		
		return argChain.toString();
	}
	
	/**
	 * Add argument to command
	 * @param arg
	 */
	public void addArg(String arg) {
		args.add(arg);
	}
	
	/**
	 * Add list of arguments to command
	 * @param args
	 */
	public void addArgs(String... args) {
		for(int i=0; i < args.length; i++)
			this.addArg(args[i]);
	}

}
