/*
 * Gerald Schreiber
 * <schreibergerald1@gmail.com>
 */

package at.big_buum_man.common.network;

import java.net.InetAddress;

// TODO new name for interface
public interface ClientMethods {

	// TODO switch from string to JSON object
	public void processMessage(InetAddress client, String message);

	public void processFoundServer(InetAddress address);

	// TODO define other methods called by clients
}
