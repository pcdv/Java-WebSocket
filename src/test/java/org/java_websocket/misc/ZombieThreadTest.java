package org.java_websocket.misc;
import java.io.IOException;
import java.net.ServerSocket;

import org.java_websocket.server.WebSocketServer;
import org.java_websocket.util.DummyClient;
import org.java_websocket.util.DummyServer;
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
		int port = getAvailablePort();
		WebSocketServer ws = new DummyServer( port );

		ws.start();
		Thread.sleep( 100 );

		DummyClient clt = new DummyClient( port );
		clt.send( "foo" );
		Thread.sleep( 100 );
		clt.close();
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

	private static int getAvailablePort() throws IOException {
		ServerSocket srv = null;
		try {
			srv = new ServerSocket( 0 );
			return srv.getLocalPort();
		} finally {
			if( srv != null ) {
				srv.close();
			}
		}
	}
}
