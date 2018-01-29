package ice.utils;

import java.util.concurrent.TimeUnit;

import org.apache.http.conn.HttpClientConnectionManager;

public class ConnectionMonitorThread extends Thread {
	private final HttpClientConnectionManager connMgr;
	private volatile boolean shutdown;

	public ConnectionMonitorThread(HttpClientConnectionManager connMgr) {
		this.connMgr = connMgr;
	}

	public void run() {
		try {
			while (!(this.shutdown))
				synchronized (this) {
					wait(15000L);

					this.connMgr.closeExpiredConnections();
					this.connMgr.closeIdleConnections(60L, TimeUnit.SECONDS);
				}
		} catch (InterruptedException localInterruptedException) {
		}
	}

	public void shutdown() {
		this.shutdown = true;
		synchronized (this) {
			notifyAll();
		}
	}
}
