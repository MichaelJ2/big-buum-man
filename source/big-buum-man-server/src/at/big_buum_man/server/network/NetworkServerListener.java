/*
 * Gerald Schreiber
 * <schreibergerald1@gmail.com>
 */

package at.big_buum_man.server.network;

import java.net.InetAddress;

public interface NetworkServerListener {

	public void registerNewClient(InetAddress client);
}
