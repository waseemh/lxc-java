package org.lxc.io;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintWriter;

public class StreamGobbler extends Thread{

	InputStream is;
	OutputStream os;

	public StreamGobbler(InputStream is, OutputStream os) {
		this.is = is;
		this.os = os;
	}

	public StreamGobbler(InputStream is) {
		this.is = is;
	}

	public void run() {
		try {
			PrintWriter pw = null;
			if(os!=null)
				pw = new PrintWriter(os);
			InputStreamReader isr = new InputStreamReader(is);
			BufferedReader br = new BufferedReader(isr);
			String line = null;
			while( (line = br.readLine()) != null ) {
				if(pw!=null)
					pw.println(line);
				//System.out.println(line);
			}
			if(pw != null) 
				pw.flush();
		} catch(IOException ioe) {
			ioe.printStackTrace();
		}
	}


}