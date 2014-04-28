/*
 * Gerald Schreiber
 * <schreibergerald1@gmail.com>
 */

package at.big_buum_man.android;

import java.net.InetAddress;

import at.big_buum_man.common.network.ClientMethods;

/**
 * Deprecated interface, use interface {@link ClientMethods} instead
 */
@Deprecated
public interface AndroidMethods extends ClientMethods {

	/**
	 * Deprecated method, use interface {@link ClientMethods} instead
	 */
	@Deprecated
	public void processMessage(InetAddress client, String message);

	/**
	 * Deprecated method, use interface {@link ClientMethods} instead
	 */
	@Deprecated
	public void processFoundServer(InetAddress address);
}
