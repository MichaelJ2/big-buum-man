/*
 * Gerald Schreiber
 * <schreibergerald1@gmail.com>
 */

package at.big_buum_man.server.network;

import java.net.InetAddress;
import java.net.MulticastSocket;
import java.net.ServerSocket;
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

	private boolean announceRunning = false;
	private boolean serverRunning = false;

	public NetworkServer(ServerMethods main) {
		this.main = main;
	}

	public void startServer() throws Exception {
		if (serverRunning) {
			throw new Exception("Server already running!");
		}

		serverUnicast = new ServerSocket(unicastPort);
		acceptThread = new AcceptThread(serverUnicast, this);
		acceptThread.start();

		serverRunning = true;
	}

	public void stopServer() throws Exception {
		if (!serverRunning) {
			throw new Exception("Server already stopped!");
		}

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

		serverRunning = false;
	}

	public void startAnnounce() throws Exception {
		if (announceRunning) {
			throw new Exception("Announce already started!");
		}

		serverMulticast = new MulticastSocket(multicastPort);
		serverMulticast.joinGroup(InetAddress.getByName(multicastAddress));
		announceThread = new AnnounceThread(serverMulticast,
				InetAddress.getByName(multicastAddress), multicastPort);
		announceThread.start();

		announceRunning = true;
	}

	public void stopAnnounce() throws Exception {
		if (!announceRunning) {
			throw new Exception("Announce already stopped!");
		}

		announceThread.stopThread();
		try {
			announceThread.join();
		} catch (InterruptedException e) {
			// TODO implement
			e.printStackTrace();
		}
		serverMulticast.leaveGroup(InetAddress.getByName(multicastAddress));
		serverMulticast.close();

		announceRunning = false;
	}

	// TODO switch from string to JSON object
	public synchronized void processMessage(InetAddress client, String message) {
		main.processMessage(client, message);
	}

	// TODO switch from string to JSON object
	// TODO check if server runs already
	public void sendMessage(InetAddress client, String message) {
		clients.get(client).sendMessage(message);
	}

	// TODO switch from string to JSON object
	// TODO check if server runs already
	public void sendMessage(String message) {
		for (InetAddress key : clients.keySet()) {
			clients.get(key).sendMessage(message);
		}
	}

	public void registerNewClient(InetAddress address, ClientThread thread) {
		clients.put(address, thread);
		main.registerNewClient(address);
	}
}