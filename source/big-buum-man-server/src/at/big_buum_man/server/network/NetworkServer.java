/*
 * Gerald Schreiber
 * <schreibergerald1@gmail.com>
 */

package at.big_buum_man.server.network;

import java.io.IOException;
import java.net.InetAddress;
import java.net.MulticastSocket;
import java.net.ServerSocket;
import java.net.UnknownHostException;
import java.util.HashMap;

import at.big_buum_man.server.ServerMethods;

public class NetworkServer {

	private ServerMethods main;

	private ServerSocket serverUnicast;
	private MulticastSocket serverMulticast;

	private int unicastPort = 1337;

	private String multicastAddress = "239.1.2.3";
	private int multicastPort = 1337;

	private HashMap<InetAddress, ClientThread> clients = new HashMap<InetAddress, ClientThread>();
	private AnnounceThread announceThread;
	private AcceptThread acceptThread;

	public NetworkServer(ServerMethods main) {
		this.main = main;
	}

	public void startServer() throws IOException {
		serverUnicast = new ServerSocket(unicastPort);
		acceptThread = new AcceptThread(serverUnicast, this);
		acceptThread.start();
	}

	public void stopServer() throws IOException {
		acceptThread.stopThread();
		try {
			acceptThread.join();
		} catch (InterruptedException e) {
			// TODO implement
			e.printStackTrace();
		}
		for (InetAddress key : clients.keySet()) {
			clients.get(key).stopThread();
		}
		serverUnicast.close();
	}

	public void startAnnounce() throws IOException {
		serverMulticast = new MulticastSocket(multicastPort);
		serverMulticast.joinGroup(InetAddress.getByName(multicastAddress));
		announceThread = new AnnounceThread(serverMulticast,
				InetAddress.getByName(multicastAddress), multicastPort);
		announceThread.start();
	}

	public void stopAnnounce() throws UnknownHostException, IOException {
		announceThread.stopThread();
		try {
			announceThread.join();
		} catch (InterruptedException e) {
			// TODO implement
			e.printStackTrace();
		}
		serverMulticast.leaveGroup(InetAddress.getByName(multicastAddress));
		serverMulticast.close();
	}

	// TODO switch from string to JSON object
	public synchronized void processMessage(InetAddress client, String message) {
		main.processMessage(client, message);
	}

	// TODO switch from string to JSON object
	public void sendMessage(InetAddress client, String message) {
		clients.get(client).sendMessage(message);
	}

	// TODO switch from string to JSON object
	public void sendMessage(String message) {
		for (InetAddress key : clients.keySet()) {
			clients.get(key).sendMessage(message);
		}
	}

	public void registerNewClient(InetAddress address, ClientThread thread) {
		clients.put(address, thread);
	}
}