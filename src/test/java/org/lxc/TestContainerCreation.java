package org.lxc;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class TestContainerCreation extends AbstractLxcTest{
	
	
	@Before
	public void setUp() throws LxcException {
		
		lxc = new Lxc("192.168.1.108","waseem","rnkun");

		lxc.connect();
		
	}
	
	@Test
	public void test() {
		//lxc.createContainer("test100", "ubuntu");
	}

	
	@After
	public void tearDown() throws LxcException {
		
		lxc.disconnect();
	}
}
