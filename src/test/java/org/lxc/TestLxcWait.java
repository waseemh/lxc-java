package org.lxc;


import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.lxc.LxcEnums.ContainerState;

public class TestLxcWait extends AbstractLxcTest{
	
	@Before
	public void setUp() throws LxcException {

		lxc = new Lxc(LXC_HOST,LXC_PORT,LXC_USERNAME,LXC_PASSWORD,true);

	}

	@Test
	public void test() throws LxcException {
		Container testContainer = lxc.container("test2");
		boolean eventOccur = testContainer.wait(ContainerState.STOPPED, 10);
		if(eventOccur)
			System.out.println("Event occured");
	}

	@After
	public void tearDown() throws LxcException {

		lxc.disconnect();
	}
	
}
