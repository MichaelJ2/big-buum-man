/*
 * Gerald Schreiber
 * <schreibergerald1@gmail.com>
 */

package at.big_buum_man.server.network.test;

import java.net.InetAddress;

import at.big_buum_man.common.network.ServerMethods;
import at.big_buum_man.server.network.NetworkServer;
import at.big_buum_man.server.network.NetworkServerListener;

public class TestNetworkServer implements ServerMethods, NetworkServerListener {

	public TestNetworkServer() throws Exception {
		NetworkServer server = new NetworkServer(this);
		server.startServer(this);
		// server.startAnnounce();
		// server.stopAnnounce();
		// while (true) {
		// server.sendMessage("Test");
		// }
		server.stopServer();
	}

	public static void main(String[] args) throws Exception {
		new TestNetworkServer();
	}

	@Override
	public void processMessage(InetAddress client, String message) {
		// TODO Auto-generated method stub
		System.out.println(client.getHostAddress() + ": " + message);
	}

	@Override
	public void registerNewClient(InetAddress client) {
		// TODO Auto-generated method stub
		System.out.println(client.getHostAddress());
	}
}
