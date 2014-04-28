/*
 * Gerald Schreiber
 * <schreibergerald1@gmail.com>
 */

package at.big_buum_man.client;

import java.net.InetAddress;

/**
 * Deprecated interface, use interface {@link at.big_buum_man.common.network.ClientMethods} instead
 */
@Deprecated
public interface ClientMethods extends at.big_buum_man.common.network.ClientMethods {

	/**
	 * Deprecated method, use interface {@link at.big_buum_man.common.network.ClientMethods} instead
	 */
	@Deprecated
	public void processMessage(InetAddress client, String message);

	/**
	 * Deprecated method, use interface {@link at.big_buum_man.common.network.ClientMethods} instead
	 */
	@Deprecated
	public void processFoundServer(InetAddress address);
}
