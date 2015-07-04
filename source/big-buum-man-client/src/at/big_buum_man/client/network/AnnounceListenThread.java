/*
 * Gerald Schreiber
 * <schreibergerald1@gmail.com>
 */

package at.big_buum_man.client.network;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.InetAddress;
import java.net.MulticastSocket;

public class AnnounceListenThread extends NetworkThread {

	private MulticastSocket socket;
	private NetworkClient client;

	private int sleepTime = 10;

	public AnnounceListenThread(MulticastSocket socket, NetworkClient client) {
		this.socket = socket;
		this.client = client;
	}

	@Override
	public void run() {
		byte[] buffer = new byte[1024];

		DatagramPacket packet = new DatagramPacket(buffer, buffer.length);

		while (super.runThread()) {
			try {
				socket.receive(packet);

				String packetString = new String(packet.getData(), "UTF-8");

				if ("BigBuumManServer".equals(packetString)) {
					InetAddress address = packet.getAddress();
					client.processFoundServer(address);
				}

				packet.setLength(buffer.length);

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