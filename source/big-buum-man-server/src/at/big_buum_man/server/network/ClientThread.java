/*
 * Gerald Schreiber
 * <schreibergerald1@gmail.com>
 */

package at.big_buum_man.server.network;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class ClientThread extends NetworkThread {

	private Socket socket;
	private NetworkServer server;

	private BufferedReader inputStream;
	private PrintWriter outputStream;

	public ClientThread(Socket socket, NetworkServer server) throws IOException {
		this.socket = socket;
		this.server = server;

		inputStream = new BufferedReader(new InputStreamReader(
				socket.getInputStream()));
		outputStream = new PrintWriter(socket.getOutputStream());
	}

	@Override
	public void run() {
		while (super.runThread()) {
			String input = null;
			try {
				input = inputStream.readLine();
			} catch (IOException e) {
				e.printStackTrace();
				continue;
			}

			if (input == null) {
				break;
			}

			server.processMessage(socket.getInetAddress(), input);
		}
		try {
			inputStream.close();
			outputStream.close();
			socket.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void sendMessage(String message) {
		outputStream.println(message);
	}
}