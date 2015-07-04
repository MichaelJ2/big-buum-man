/*
 * Gerald Schreiber
 * <schreibergerald1@gmail.com>
 */

package at.big_buum_man.android.network.test;

import java.net.InetAddress;

import at.big_buum_man.android.network.NetworkClient;
import at.big_buum_man.common.network.ClientMethods;

public class TestNetworkClient implements ClientMethods {

	public TestNetworkClient() throws Exception {
		NetworkClient client = new NetworkClient(this);
		// client.startListenAnnounce();
		// client.stopListenAnnounce();
		client.connectToServer("192.168.1.2");
		// while (true) {
		// client.sendMessage("Test");
		// }
		client.disconnectFromServer();
	}

	public static void main(String[] args) throws Exception {
		new TestNetworkClient();
	}

	@Override
	public void processMessage(InetAddress client, String message) {
		// TODO Auto-generated method stub
		System.out.println(message);
	}

	@Override
	public void processFoundServer(InetAddress address) {
		// TODO Auto-generated method stub
		System.out.println(address.getHostAddress());
	}
}
