/*
 * Gerald Schreiber
 * <schreibergerald1@gmail.com>
 */

package at.big_buum_man.android.network;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.InetAddress;
import java.net.MulticastSocket;
import java.net.Socket;

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

	private boolean clientConnected = false;
	private boolean listenAnnounce = false;

	public NetworkClient(AndroidMethods main) {
		this.main = main;
	}

	public void connectToServer(String addressString) throws Exception {
		if (clientConnected) {
			throw new Exception("Client already connected!");
		}

		InetAddress address = InetAddress.getByName(addressString);
		socketUnicast = new Socket(address, unicastPort);

		inputStream = new BufferedReader(new InputStreamReader(
				socketUnicast.getInputStream()));
		outputStream = new PrintWriter(socketUnicast.getOutputStream());

		listenThread = new ListenThread(inputStream, this);
		listenThread.start();

		clientConnected = true;
	}

	public void disconnectFromServer() throws Exception {
		if (!clientConnected) {
			throw new Exception("Client already disconnected!");
		}

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

		clientConnected = false;
	}

	public void startListenAnnounce() throws Exception {
		if (listenAnnounce) {
			throw new Exception("Listen announce already started!");
		}

		socketMulticast = new MulticastSocket(multicastPort);
		socketMulticast.joinGroup(InetAddress.getByName(multicastAddress));
		announceThread = new AnnounceListenThread(socketMulticast, this);
		announceThread.start();

		listenAnnounce = true;
	}

	public void stopListenAnnounce() throws Exception {
		if (!listenAnnounce) {
			throw new Exception("Listen announce already stopped!");
		}

		announceThread.stopThread();
		try {
			announceThread.join();
		} catch (InterruptedException e) {
			// TODO implement
			e.printStackTrace();
		}
		socketMulticast.leaveGroup(InetAddress.getByName(multicastAddress));
		socketMulticast.close();

		listenAnnounce = false;
	}

	// TODO switch from string to JSON object
	public void processMessage(String message) {
		main.processMessage(socketUnicast.getInetAddress(), message);
	}

	// TODO switch from string to JSON object
	// TODO check if client is already connected
	public void sendMessage(String message) {
		outputStream.println(message);
		outputStream.flush();
	}

	public void processFoundServer(InetAddress address) {
		main.processFoundServer(address);
	}
}