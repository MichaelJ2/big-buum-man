package at.bbm.core.server;

import at.bbm.core.server.json.Register;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.core.config.Configurator;
import org.junit.Test;

import java.io.*;
import java.net.InetAddress;
import java.net.Socket;

public class TestServer {

    public static final Logger LOGGER = LogManager.getLogger(TestServer.class.getName());

    private static final String HOST = "localhost";
    private static final int PORT = 8888;

    @Test
    public void testPOST() {
        Configurator.setRootLevel(Level.DEBUG);

        final ThreadPooledServer server = ThreadPooledServer.getInstance();
        server.start(PORT);

        waitTime(100);

        Socket client = null;

        try {
            InetAddress address = InetAddress.getByName(HOST);
            client = new Socket(address, PORT);

            final String request = new Register("Pali").toJSONString();

            LOGGER.debug("Sending request ...");

            // Writer to write to the connection's stream
            BufferedWriter wr = new BufferedWriter(new OutputStreamWriter(client.getOutputStream(), "UTF-8"));

            // Send request
            wr.write(request);
            wr.write("\r\n");
            wr.flush();

            // Reader to reade the connection's stream
            final BufferedReader rd = new BufferedReader(new InputStreamReader(client.getInputStream()));
            final String response = rd.readLine();

            LOGGER.debug("Response was: {}", response);

            wr.close();
            rd.close();
        } catch (final Exception e) {
            e.printStackTrace();
        } finally {
            if (null != client && !client.isClosed()) {
                try {
                    client.close();
                } catch (final IOException e) {
                    e.printStackTrace();
                }
            }
        }

        waitTime(100);
        server.stop();
    }

    private void waitTime(final int millis) {
        try {
            Thread.sleep(millis);
        } catch (final InterruptedException e) {
            e.printStackTrace();
        }
    }
}