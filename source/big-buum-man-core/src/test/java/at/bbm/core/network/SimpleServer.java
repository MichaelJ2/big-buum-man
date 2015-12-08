package at.bbm.core.network;

import java.io.*;
import java.net.ServerSocket;

public class SimpleServer extends Thread {

    private static SimpleServer instance = null;

    private ServerSocket server = null;

    private boolean running = true;

    private SimpleServer() {
        try {
            server = new ServerSocket(1337);
        } catch (final Exception e) {
            e.printStackTrace();
        }
    }

    public static SimpleServer getInstance() {
        if (null == instance) {
            instance = new SimpleServer();
        }
        return instance;
    }

    @Override
    public void run() {
        System.out.println("Server started");
        try {

            Connection connection = new Connection(server.accept());
            System.out.println("Client accepted");

            String request;
            String response;

            // receive request
            while (true) {
                request = connection.getRequest();
                System.out.println("client> " + request);

                // prepare response
                if (Connection.EOM.equals(request)) {
                    break;
                }

                response = "Echo: " + request;
                connection.sendResponse(response);
            }
        } catch (final IOException e) {
            e.printStackTrace();
        }
    }

    public void stopServer() {
        this.running = false;
        this.interrupt();
        System.out.println("Serve stopped");
    }

}
