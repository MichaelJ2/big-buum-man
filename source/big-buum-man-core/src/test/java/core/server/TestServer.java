package core.server;

import org.json.simple.JSONObject;
import org.junit.Before;
import org.junit.Test;

import java.io.*;
import java.net.InetAddress;
import java.net.Socket;
import java.net.URLEncoder;
import java.util.Scanner;

public class TestServer {

    private static final String HOST = "localhost";
    private static final int PORT = 8888;
    private final JSONObject json = new JSONObject();

    private final int[][] mapFields = new int[][] {
            {1, 1, 1},
            {1, 1, 1},
            {1, 1, 1},
            {1, 1, 1},
            {1, 1, 1}
    };

    private final String NL = "\r\n";

    @Before
    public void init() {

    }

    @Test
    public void testJson() {
        ThreadPooledServer server = ThreadPooledServer.getInstance();
        server.start(PORT);
        Socket client = null;

        try {
            client = new Socket(HOST, PORT );

            final Scanner in = new Scanner(client.getInputStream());
            final PrintWriter out = new PrintWriter(client.getOutputStream(), true);

            while (in.hasNext()) {
                final String line = in.nextLine();
                if (!line.isEmpty()) {
                    System.out.println(line);
                }
            }
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

        while (server.isRunning()) {
            try {
                Thread.sleep(1000);
                server.stop();
            } catch (final InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    @Test
    public void testThreadPooledServer() {
        ThreadPooledServer server = ThreadPooledServer.getInstance();
        server.start(PORT);
        Socket client = null;


        try {
            client = new Socket(HOST, PORT );

            final Scanner in = new Scanner(client.getInputStream());
            final PrintWriter out = new PrintWriter(client.getOutputStream(), true);

            out.println("2");
            out.println("4");
            while (in.hasNext()) {
                final String line = in.nextLine();
                if (!line.isEmpty()) {
                    System.out.println(line);
                }
            }
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

        while (server.isRunning()) {
            try {
                Thread.sleep(1000);
                server.stop();
            } catch (final InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    @Test
    public void testPOST() {
        ThreadPooledServer server = ThreadPooledServer.getInstance();

        waitTime(100);

        server.start(PORT);
        Socket client = null;

        System.out.println("x");
        try {
            InetAddress addr = InetAddress.getByName(HOST);
            client = new Socket(addr, PORT );

            final String request = String.format("1001,2001,3,3%s", NL);

            // Send headers
            BufferedWriter wr = new BufferedWriter(new OutputStreamWriter(client.getOutputStream(), "UTF-8"));
            wr.write("POST / HTTP/1.1\r\n");
            wr.write("Content-Length: " + request.length() + "\r\n");
            wr.write("Content-Type: application/x-www-form-urlencoded\r\n");
            wr.write(request);
            wr.write("\r\n");

            System.out.println("2");
            wr.flush();

            System.out.println("3");
            // Get response
            BufferedReader rd = new BufferedReader(new InputStreamReader(client.getInputStream()));
            String line;

            System.out.println("4");

            final StringBuilder stringBuilder = new StringBuilder();
            while (null != (line = rd.readLine())) {
                stringBuilder.append(line).append("\n");
            }

            System.out.println(stringBuilder.toString());

            wr.close();
            rd.close();
            System.out.println("6");
        } catch (final Exception e) {
            e.printStackTrace();
        } finally {
            System.out.println("6");
            if (null != client && !client.isClosed()) {
                try {
                    System.out.println("7");
                    client.close();
                    System.out.println("8");
                } catch (final IOException e) {
                    e.printStackTrace();
                }
            }
        }

        System.out.println("9");

        waitTime(1000);
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
