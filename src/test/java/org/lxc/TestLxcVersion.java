package org.lxc;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class TestLxcVersion extends AbstractLxcTest{

	@Before
	public void setUp() throws LxcException {

		lxc = new Lxc(LXC_HOST,LXC_PORT,LXC_USERNAME,LXC_PASSWORD,true);

	}

	@Test
	public void test() throws LxcException {
		System.out.println(lxc.version());
	}

	@After
	public void tearDown() throws LxcException {

		lxc.disconnect();
	}
	
}