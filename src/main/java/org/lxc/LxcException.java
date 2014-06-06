package org.lxc;

public class LxcException extends Exception{

	private static final long serialVersionUID = 1L;
	
    public LxcException() {
    }

    public LxcException(String message) {
        super(message);
    }

    public LxcException(String message, Throwable t) {
        super(message, t);
    }

    public LxcException(Throwable t) {
        super(t);
    }


}
