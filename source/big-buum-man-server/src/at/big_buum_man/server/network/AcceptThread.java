/*
 * Gerald Schreiber
 * <schreibergerald1@gmail.com>
 */

package at.big_buum_man.server.network;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class AcceptThread extends NetworkThread {

	private ServerSocket socket;
	private NetworkServer server;

	public AcceptThread(ServerSocket socket, NetworkServer server)
			throws IOException {
		this.socket = socket;
		this.server = server;
	}

	@Override
	public void run() {
		while (super.runThread()) {
			ClientThread thread;
			try {
				Socket client = socket.accept();
				thread = new ClientThread(client, server);
				thread.start();
				server.registerNewClient(socket.getInetAddress(), thread);
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
}