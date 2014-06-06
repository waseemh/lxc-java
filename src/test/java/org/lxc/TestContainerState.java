package org.lxc;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.lxc.LxcEnums.ContainerState;

public class TestContainerState extends AbstractLxcTest{


	@Before
	public void setUp() throws LxcException {

		lxc = new Lxc(LXC_HOST,LXC_PORT,LXC_USERNAME,LXC_PASSWORD,true);

	}

	@Test
	public void test() throws LxcException {

		for(Container container : lxc.containers()) {
			ContainerState state = container.state();
			if(state.equals(ContainerState.RUNNING))
				System.out.println(container.memoryUsage());
			System.out.println("Container #" + container.getName() + " - State: " + state);
		}
	}

	@After
	public void tearDown() throws LxcException {

		lxc.disconnect();
	}

}
