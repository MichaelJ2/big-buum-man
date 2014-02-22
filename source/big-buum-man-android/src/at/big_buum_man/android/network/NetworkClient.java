/*
 * Gerald Schreiber
 * <schreibergerald1@gmail.com>
 */

package at.big_buum_man.android.network;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.InetAddress;
import java.net.MulticastSocket;
import java.net.Socket;
import java.net.UnknownHostException;

import at.big_buum_man.android.AndroidMethods;

public class NetworkClient {

	private AndroidMethods main;

	private Socket socketUnicast;
	private MulticastSocket socketMulticast;

	private int unicastPort = 1337;

	private String multicastAddress = "239.1.2.3";
	private int multicastPort = 1337;

	private BufferedReader inputStream;
	private PrintWriter outputStream;

	private AnnounceListenThread announceThread;
	private ListenThread listenThread;

	public NetworkClient(AndroidMethods main) {
		this.main = main;
	}

	public void connectToServer(String addressString) throws IOException {
		InetAddress address = InetAddress.getByName(addressString);
		socketUnicast = new Socket(address, unicastPort);

		inputStream = new BufferedReader(new InputStreamReader(
				socketUnicast.getInputStream()));
		outputStream = new PrintWriter(socketUnicast.getOutputStream());

		listenThread = new ListenThread(inputStream, this);
		listenThread.start();
	}

	public void disconnectFromServer() throws IOException {
		listenThread.stopThread();
		try {
			listenThread.join();
		} catch (InterruptedException e) {
			// TODO implement
			e.printStackTrace();
		}
		inputStream.close();
		outputStream.close();
		socketUnicast.close();
	}

	public void startListenAnnounce() throws IOException {
		socketMulticast = new MulticastSocket(multicastPort);
		socketMulticast.joinGroup(InetAddress.getByName(multicastAddress));
		announceThread = new AnnounceListenThread(socketMulticast, this);
		announceThread.start();
	}

	public void stopListenAnnounce() throws UnknownHostException, IOException {
		announceThread.stopThread();
		try {
			announceThread.join();
		} catch (InterruptedException e) {
			// TODO implement
			e.printStackTrace();
		}
		socketMulticast.leaveGroup(InetAddress.getByName(multicastAddress));
		socketMulticast.close();
	}

	// TODO switch from string to JSON object
	public void processMessage(String message) {
		main.processMessage(socketUnicast.getInetAddress(), message);
	}

	// TODO switch from string to JSON object
	public void sendMessage(String message) {
		outputStream.println(message);
		outputStream.flush();
	}

	public void processFoundServer(InetAddress address) {
		main.processFoundServer(address);
	}
}