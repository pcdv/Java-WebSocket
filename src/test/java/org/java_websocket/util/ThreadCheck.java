package org.java_websocket.util;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.locks.LockSupport;

import org.junit.Assert;
import org.junit.rules.ExternalResource;
/**
 * Makes test fail if new threads are still alive after tear-down.
 */
public class ThreadCheck extends ExternalResource {
	private Map<Long,Thread> map = new HashMap<Long,Thread>();

	@Override protected void before() throws Throwable {
		map = getThreadMap();
	}

	@Override protected void after() {
		long time = System.currentTimeMillis();
		do {
			LockSupport.parkNanos( 10000000 );
		} while ( checkZombies( true ) && System.currentTimeMillis() - time < 1000 );

		checkZombies( false );
	}

	private boolean checkZombies( boolean testOnly ) {
		Map<Long,Thread> newMap = getThreadMap();

		int zombies = 0;
		for( Thread t : newMap.values() ) {
			Thread prev = map.get( t.getId() );
			if( prev == null ) {
				zombies++;
				if( testOnly )
					return true;

				StringBuilder b = new StringBuilder( 4096 );
				appendStack( t, b.append( "\n" ).append( t.getName() ) );
				System.err.println( b );
			}
		}
		if( zombies > 0 && ! testOnly )
			Assert.fail( "Found " + zombies + " zombie thread(s) " );

		return zombies > 0;
	}

	private Map<Long,Thread> getThreadMap() {
		Map<Long,Thread> map = new HashMap<Long,Thread>();
		Thread[] threads = new Thread[ Thread.activeCount() * 2 ];
		int actualNb = Thread.enumerate( threads );
		for( int i = 0; i < actualNb; i++ ) {
			map.put( threads[ i ].getId(), threads[ i ] );
		}
		return map;
	}

	private static void appendStack( Thread th, StringBuilder s ) {
		StackTraceElement[] st = th.getStackTrace();
		for( int i = 0; i < st.length; i++ ) {
			s.append( "\n    at " );
			s.append( st[ i ] );
		}
	}
}
