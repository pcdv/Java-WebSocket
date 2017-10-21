package org.java_websocket.util;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

import org.java_websocket.client.WebSocketClient;
import org.java_websocket.handshake.ServerHandshake;

/**
 * Dummy client for testing.
 */
public class DummyClient extends WebSocketClient {

	private final CountDownLatch open = new CountDownLatch( 1 );

	public DummyClient( int port ) throws URISyntaxException, InterruptedException {
		super( new URI( "ws://localhost:" + port ) );
		super.connect();
		if( ! open.await( 10, TimeUnit.SECONDS ) ) {
			throw new IllegalStateException( "connection timeout on " + uri );
		}
	}

	@Override public void onOpen( ServerHandshake data ) {
		open.countDown();
	}

	@Override public void onMessage( String msg ) {}

	@Override public void onClose( int code, String reason, boolean remote ) {}

	@Override public void onError( Exception ex ) {}

}