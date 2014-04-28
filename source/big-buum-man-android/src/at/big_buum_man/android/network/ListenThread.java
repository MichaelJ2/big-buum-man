/*
 * Gerald Schreiber
 * <schreibergerald1@gmail.com>
 */

package at.big_buum_man.android.network;

import java.io.BufferedReader;
import java.io.IOException;

import at.big_buum_man.common.network.NetworkThread;

public class ListenThread extends NetworkThread {

	private BufferedReader inputStream;
	private NetworkClient client;

	public ListenThread(BufferedReader inputStream, NetworkClient client)
			throws IOException {
		this.inputStream = inputStream;
		this.client = client;
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

			client.processMessage(input);
		}
	}
}