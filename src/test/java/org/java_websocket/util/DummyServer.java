package org.java_websocket.util;

import java.net.InetSocketAddress;

import org.java_websocket.WebSocket;
import org.java_websocket.handshake.ClientHandshake;
import org.java_websocket.server.WebSocketServer;

/**
 * Simple server that just sits there, listening on a port.
 */
public class DummyServer extends WebSocketServer {
	public DummyServer( int port ) {
		super( new InetSocketAddress( port ) );
	}

	@Override public void onOpen( WebSocket conn, ClientHandshake handshake ) {}

	@Override public void onClose( WebSocket conn, int code, String reason, boolean remote ) {}

	@Override public void onMessage( WebSocket conn, String message ) {}

	@Override public void onError( WebSocket conn, Exception ex ) {}

	@Override public void onStart() {}
}
