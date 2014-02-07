/*
 * Gerald Schreiber
 * <schreibergerald1@gmail.com>
 */

package at.big_buum_man.client.network;

public abstract class NetworkThread extends Thread {

	private boolean runThread = true;

	public void stopThread() {
		runThread = false;
	}

	public boolean runThread() {
		return runThread;
	}

	@Override
	public abstract void run();
}
