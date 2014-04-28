/*
 * Gerald Schreiber
 * <schreibergerald1@gmail.com>
 */

package at.big_buum_man.common.network;

import java.net.InetAddress;

public interface ClientMethods {

	public void processMessage(InetAddress client, String message);

	// TODO define other methods called by clients

	/**
	 * Deprecated method, use interface NetworkClientListener instead
	 */
	@Deprecated
	public void processFoundServer(InetAddress address);
}
