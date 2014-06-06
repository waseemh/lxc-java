package org.lxc;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.lxc.LxcEnums.ContainerState;

/**
 * Test container snapshot functionality
 * @author waseem
 *
 */
public class TestContainerSnapshot extends AbstractLxcTest {
	
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
		if(!testContainer.isRunning())
		{
			System.out.println("Taking snapshot");
			testContainer.snapshot();
		}
	}

	
	@After
	public void tearDown() throws LxcException {
		
		lxc.disconnect();
	}
}
