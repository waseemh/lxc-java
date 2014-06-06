package org.lxc;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.lxc.LxcEnums.ContainerState;

/**
 * Test container freeze functionality
 * @author waseem
 *
 */
public class TestContainerFreeze extends AbstractLxcTest {
	
	final String containerName = "test";
	
	@Before
	public void setUp() throws LxcException {
		
		lxc = new Lxc(LXC_HOST,LXC_PORT,LXC_USERNAME,LXC_PASSWORD,true);
		
	}
	
	/**
	 * Freeze and unfreeze running containers.
	 * Check if container state is being updated accordingly.
	 * @throws LxcException
	 */
	@Test
	public void test() throws LxcException {
		Container testContainer = lxc.container(containerName);
		ContainerState state = testContainer.state();
		System.out.println("Current State: " + state);
		if(testContainer.isRunning())
		{
			System.out.println("Container is being freeze...");
			testContainer.freeze();
			System.out.println("New State: " + testContainer.state());
			System.out.println("Container is being unfreeze...");
			testContainer.unfreeze();
			System.out.println("New State: " + testContainer.state());
		}
	}

	
	@After
	public void tearDown() throws LxcException {
		
		lxc.disconnect();
	}
}
