package org.java_websocket.misc;

import org.java_websocket.server.WebSocketServer;
import org.java_websocket.util.DummyClient;
import org.java_websocket.util.DummyServer;
import org.java_websocket.util.SocketUtil;
import org.java_websocket.util.ThreadCheck;
import org.junit.Rule;
import org.junit.Test;

/**
 * Checks that the server leaves no active threads behind after it
 * is closed. The test is ran several times because it is not systematic.
 */
public class ZombieThreadTest {

	@Rule public ThreadCheck zombies = new ThreadCheck();


	private void runTestScenario() throws Exception {
		WebSocketServer ws = new DummyServer( SocketUtil.getAvailablePort() );
		ws.start();

		// need to wait a bit otherwise client cannot connect
		Thread.sleep( 50 );

		DummyClient clt = new DummyClient( ws.getPort() );
		clt.send( "foo" );
		// test fails (most of the time) if we remove the closeConnection() call
		// clt.close(); // not required if closeConnection() is called
		clt.closeConnection( 0, "" );
		ws.stop();
	}

	@Test public void test1() throws Exception {
		runTestScenario();
	}

	@Test public void test2() throws Exception {
		runTestScenario();
	}

	@Test public void test3() throws Exception {
		runTestScenario();
	}

	@Test public void test4() throws Exception {
		runTestScenario();
	}

	@Test public void test5() throws Exception {
		runTestScenario();
	}

	@Test public void test6() throws Exception {
		runTestScenario();
	}
}
