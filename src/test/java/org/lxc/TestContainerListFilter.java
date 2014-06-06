package org.lxc;

import java.util.List;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class TestContainerListFilter extends AbstractLxcTest{

	@Before
	public void setUp() throws LxcException {

		lxc = new Lxc(LXC_HOST,LXC_PORT,LXC_USERNAME,LXC_PASSWORD,true);

	}

	@Test
	public void test() throws LxcException {

		System.out.println("RUNNING CONTAINERS:");

		printContainers(lxc.runningContainers());

		System.out.println("STOPPED CONTAINERS:");

		printContainers(lxc.stoppedContainers());

		System.out.println("FROZEN CONTAINERS:");

		printContainers(lxc.frozenContainers());
		
		System.out.println("Active CONTAINERS:");

		printContainers(lxc.activeContainers());
	
	}

	private void printContainers(List<Container> containers) {
		if(containers==null)
		{
			System.out.println("No Containers Found");
			return;
		}
		for(Container container : containers) {
			System.out.println("Container #" + container.getName());
		}
	}


	@After
	public void tearDown() throws LxcException {

		lxc.disconnect();
	}

}
