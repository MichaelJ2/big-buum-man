package core.server;

import com.eclipsesource.json.Json;
import com.eclipsesource.json.JsonArray;
import com.eclipsesource.json.JsonObject;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Scanner;

public class TestServer {

    private static final int PORT = 8888;

    private final JsonObject json = new JsonObject();;
    private final int[][] mapFields = new int[][] {
            {1, 1, 1},
            {1, 1, 1},
            {1, 1, 1},
            {1, 1, 1},
            {1, 1, 1}
    };

    @Before
    public void init() {

        json.add("type", "map");

        JsonArray mapData = new JsonArray();

        for (int i = 0; i < mapFields.length; i++) {
            mapData.add(Json.array(mapFields[i]));
        }

        JsonObject map = new JsonObject();

        map.add("width", mapFields.length);
        map.add("height", mapFields[0].length);

        map.add("fields", mapData);

        json.add("map", map);
    }

    @Test
    public void testJson() {
        ThreadPooledServer server = ThreadPooledServer.getInstance();
        server.start(PORT);Socket client = null;

        try {
            client = new Socket("localhost", PORT );

            final Scanner in = new Scanner(client.getInputStream());
            final PrintWriter out = new PrintWriter(client.getOutputStream(), true);

            out.println(json.toString());
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
        server.start(PORT);Socket client = null;

        try {
            client = new Socket("localhost", PORT );

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
}
