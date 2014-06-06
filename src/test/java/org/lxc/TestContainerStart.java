package org.lxc;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.lxc.LxcEnums.ContainerState;

public class TestContainerStart extends AbstractLxcTest {
	
	final String containerName = "test";
	
	@Before
	public void setUp() throws LxcException {
		
		lxc = new Lxc(LXC_HOST,LXC_PORT,LXC_USERNAME,LXC_PASSWORD,true);
		
	}
	
	@Test
	public void test() throws LxcException {
		Container testContainer = lxc.container(containerName);
		ContainerState state = testContainer.state();
		System.out.println("Current State: " + state);
		if(testContainer.isStopped())
		{
			System.out.println("Container is being started...");
			testContainer.start();
		
		}
		state = testContainer.state();
		System.out.println("New State: " + state);
		System.out.println("MEM USG: " + testContainer.memoryUsage());
		System.out.println("PID: " + testContainer.pid());
		System.out.println("IP: " + testContainer.ip());
		System.out.println("MEM LIM: " + testContainer.memoryLimit());
	}

	
	@After
	public void tearDown() throws LxcException {
		
		lxc.disconnect();
	}
}
