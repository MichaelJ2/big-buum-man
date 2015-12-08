package at.bbm.core.network;

import org.junit.Test;

import java.io.*;
import java.net.InetAddress;
import java.net.Socket;

public class TestSimpleServer {

    private final String userInput = "hello\n" +
            "how are you?\n" +
            "bye\n";

    private final InputStream alt = new ByteArrayInputStream(userInput.getBytes());


    private final SimpleServer server = SimpleServer.getInstance();

    @Test
    public void testSimpleServer2() {
        try {
            final BufferedReader user = new BufferedReader(new InputStreamReader(alt));
            server.start();

            final InetAddress address = InetAddress.getByName("localhost");
            final Socket client = new Socket(address, 1337);

            final Connection connection = new Connection(client);

            String request;
            String response;

            while (true) {

                // prepare request
                request = user.readLine();

                if (null == request) {
                    break;
                }

                // receive response
                response = connection.getResponse(request);
                System.out.println("server> " + response);
                System.out.println();

                // check response
                if ("Echo: bye".equals(response)) {
                    break;
                }

                // simulate user
                Thread.sleep(100);
            }

            user.close();
            server.stopServer();
        } catch (final Exception  e) {
            e.printStackTrace();
            server.stopServer();
        }
    }

}
