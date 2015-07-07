/*
 * Gerald Schreiber
 * <schreibergerald1@gmail.com>
 */

package at.big_buum_man.server.network;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.InetAddress;
import java.net.MulticastSocket;

public class AnnounceThread extends NetworkThread {

	private MulticastSocket socket;
	private InetAddress multicastAddress;
	private int multicastPort;

	private int sleepTime = 10;

	public AnnounceThread(MulticastSocket socket, InetAddress multicastAddress, int multicastPort) {
		this.socket = socket;
		this.multicastAddress = multicastAddress;
		this.multicastPort = multicastPort;
	}

	@Override
	public void run() {
		String packetString = "BigBuumManServer";
		byte[] buffer = packetString.getBytes();
		
		DatagramPacket packet = new DatagramPacket(buffer, buffer.length,
				multicastAddress, multicastPort);

		while (super.runThread()) {
			try {
				socket.send(packet);
				try {
					Thread.sleep(sleepTime * 1000);
				} catch (InterruptedException e) {
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
}