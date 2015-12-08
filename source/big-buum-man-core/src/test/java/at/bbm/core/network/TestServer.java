package at.bbm.core.network;

import at.bbm.core.GlobalProperties;
import at.bbm.core.network.json.Move;
import at.bbm.core.network.json.Register;
import at.bbm.core.network.server.ThreadPooledServer;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.core.config.Configurator;
import org.junit.Before;
import org.junit.Test;

import java.io.*;
import java.net.*;
import java.util.Collections;
import java.util.Enumeration;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class TestServer {

    public static final Logger LOGGER = LogManager.getLogger(TestServer.class.getName());

    private static final String HOST = "localhost";
    private static final int PORT = 8888;

    private Connection connection = null;

    private static final String UUID_REGEX = "[0-9a-f]{8}-[0-9a-f]{4}-[0-9a-f]{4}-[0-9a-f]{4}-[0-9a-f]{12}";
    private final Pattern pattern = Pattern.compile(UUID_REGEX);
    private Matcher matcher;

    private final String[] dir = {"N", "E", "S", "W"};

    @Before
    public void setup() {
        Configurator.setRootLevel(Level.DEBUG);
    }

    @Test
    public void testClientServerCommunication() {

        final ThreadPooledServer server = ThreadPooledServer.getInstance();
        server.start(PORT);

        waitTime(100);

        try {
            InetAddress address = InetAddress.getByName(HOST);
            connection = new Connection(new Socket(address, PORT));

            LOGGER.debug("Sending request ...");

            // register 2 times to test if controller is not be re-registered
            for (int i = 0; i < 2; i++) {

                // send request to register new controller

                System.out.println();

                final String request = new Register("Pali").getJSON();
                final String response = connection.getResponse(request);

                connection.connect(new Socket(address, PORT));

                LOGGER.debug("Response was: {}", response);
                if ((matcher = pattern.matcher(response)).matches()) {
                    LOGGER.debug("Found UUID");
                    // set UUID for this controller for identification
                    GlobalProperties.UUID = response;
                }

                // request sent and got response
            }

            // test all direction commands
            for (int i = 0; i < 4; i++) {
                System.out.println();
                final String request = new Move(dir[i]).getJSON();
                final String response = connection.getResponse(request);

                connection.connect(new Socket(address, PORT));

                LOGGER.debug("Response was: {}", response);
            }

            connection.close();
            connection = null;

            System.out.println();
        } catch (final Exception e) {
            e.printStackTrace();
        }

        waitTime(3000);
        server.stop();
    }

    private void waitTime(final int millis) {
        try {
            Thread.sleep(millis);
        } catch (final InterruptedException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void testIP() {
        System.out.println("IP0: " + getIP0());
        System.out.println("IP1: " + getIP1());
        System.out.println("IP2: " + getIP2());
        System.out.println("pIP: " + getPrivateIP());
    }

    public String getIP0() {
        InetAddress ip;
        try {
            ip = InetAddress.getLocalHost();
            return ip.getHostAddress();
        } catch (UnknownHostException e) {
            e.printStackTrace();
        }
        return null;
    }

    public String getIP1() {
        try {
            return Collections.list(NetworkInterface.getNetworkInterfaces()).stream()
                    .flatMap(i -> Collections.list(i.getInetAddresses()).stream())
                    .filter(ip -> ip instanceof Inet4Address && ip.isSiteLocalAddress())
                    .findFirst().orElseThrow(RuntimeException::new)
                    .getHostAddress();
        } catch (SocketException e) {
            return null;
        }
    }

    public static String getIP2(){
        String ipAddress = null;
        Enumeration<NetworkInterface> net = null;
        try {
            net = NetworkInterface.getNetworkInterfaces();
        } catch (SocketException e) {
            throw new RuntimeException(e);
        }
        while(net.hasMoreElements()){
            NetworkInterface element = net.nextElement();
            Enumeration<InetAddress> addresses = element.getInetAddresses();
            while (addresses.hasMoreElements()){
                InetAddress ip = addresses.nextElement();
                if (ip instanceof Inet4Address){

                    if (ip.isSiteLocalAddress()){
                        ipAddress = ip.getHostAddress();
                    }

                }

            }
        }
        return ipAddress;
    }

    public String getPrivateIP() {
        String ip = null;
        BufferedReader in = null;
        try {
            URL whatismyip = new URL("http://checkip.amazonaws.com");
            in = new BufferedReader(new InputStreamReader(
                    whatismyip.openStream()));

            ip = in.readLine(); //you get the IP as a String
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (in != null) {
                try {
                    in.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return ip;
    }
}