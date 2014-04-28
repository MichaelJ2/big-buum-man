/*
 * Gerald Schreiber
 * <schreibergerald1@gmail.com>
 */

package at.big_buum_man.server.network;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

import at.big_buum_man.common.network.NetworkThread;

public class AcceptThread extends NetworkThread {

	private ServerSocket socket;
	private NetworkServer server;
	private NetworkServerListener listener;

	public AcceptThread(ServerSocket socket, NetworkServer server, NetworkServerListener listener)
			throws IOException {
		this.socket = socket;
		this.server = server;
		this.listener = listener;
	}

	@Override
	public void run() {
		while (super.runThread()) {
			ClientThread thread;
			try {
				Socket client = socket.accept();
				thread = new ClientThread(client, server);
				thread.start();
				server.registerNewClient(client.getInetAddress(), thread);
				listener.registerNewClient(client.getInetAddress());
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
}