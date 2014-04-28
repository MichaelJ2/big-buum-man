/*
 * Gerald Schreiber
 * <schreibergerald1@gmail.com>
 */

package at.big_buum_man.server;

import java.net.InetAddress;

/**
 * Deprecated interface, use interface {@link at.big_buum_man.common.network.ServerMethods} instead
 */
@Deprecated
public interface ServerMethods extends at.big_buum_man.common.network.ServerMethods{

	/**
	 * Deprecated method, use interface {@link at.big_buum_man.common.network.ServerMethods} instead
	 */
	@Deprecated
	public void processMessage(InetAddress client, String message);

	/**
	 * Deprecated method, use interface {@link at.big_buum_man.common.network.ServerMethods} instead
	 */
	@Deprecated
	public void registerNewClient(InetAddress client);
}
