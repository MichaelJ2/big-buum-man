/*
 * Gerald Schreiber
 * <schreibergerald1@gmail.com>
 */

package at.big_buum_man.android.network;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.InetAddress;
import java.net.MulticastSocket;

import at.big_buum_man.common.network.NetworkThread;

public class AnnounceListenThread extends NetworkThread {

	private MulticastSocket socket;
	private NetworkClientListener listener;

	private int sleepTime = 10;

	public AnnounceListenThread(MulticastSocket socket, NetworkClientListener listener) {
		this.socket = socket;
		this.listener = listener;
	}

	@Override
	public void run() {
		byte[] buffer = new byte[1024];

		DatagramPacket packet = new DatagramPacket(buffer, buffer.length);

		while (super.runThread()) {
			try {
				socket.receive(packet);

				// TODO check if server is a BigBuumManServer
				// String packetString = new String(packet.getData(), "UTF-8");

				InetAddress address = packet.getAddress();
				listener.processFoundServer(address);

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