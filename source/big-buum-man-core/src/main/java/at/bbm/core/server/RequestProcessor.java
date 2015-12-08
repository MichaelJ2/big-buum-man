package at.bbm.core.server;

import at.bbm.core.objects.players.Player;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

import java.io.*;
import java.net.Socket;

public class RequestProcessor implements Runnable{

    public static final Logger LOGGER = LogManager.getLogger(RequestProcessor.class.getName());

    private final JSONParser parser;
    private final Socket socket;

    public RequestProcessor(final Socket paramSocket, final JSONParser paramJSONParser) {
        this.socket = paramSocket;
        this.parser = paramJSONParser;
    }

    @Override
    public void run() {
        try {

            if (LOGGER.isDebugEnabled()) {
                LOGGER.debug("New request processor started");
            }

            // get request
            final BufferedReader in = new BufferedReader(new InputStreamReader(this.socket.getInputStream()));
            final String rawJson = in.readLine();

            if (LOGGER.isDebugEnabled()) {
                LOGGER.debug("Processing request: {}", rawJson);
            }

            String id = null;
            String type = null;
            JSONObject data = null;

            try {
                final JSONObject jsonObject = (JSONObject) this.parser.parse(rawJson);
                id = jsonObject.get("id").toString();
                type = jsonObject.get("type").toString();
                data = (JSONObject) jsonObject.get("data");
            } catch (final Exception e) {
                LOGGER.error("Unable to parse JSON Request: \"{}\", request data: {}", e.getMessage(), rawJson);
            }

            Player player = PlayerMapper.getPlayer(id);

            if (null == player && !"reg".equals(type)) {
                LOGGER.error("Unknown controller");
                player = new Player();
            }

            String response = "200\r\n";

            if (null != type && null != data) {
                switch (type) {
                    case "reg":
                        // register new controller
                        final String name = data.get("name").toString();
                        final String uuid = PlayerMapper.addPlayer(id, name);
                        response = uuid + "\r\n";
                        break;
                    case "mov":
                        // move player
                        final String dir = data.get("dir").toString();
                        if (LOGGER.isDebugEnabled()) {
                            LOGGER.debug("Moving player \"{}\" to \"{}\"", player.getName(), dir);
                        }
                        PlayerMapper.getPlayer(id).move(dir);
                        break;
                    case "btn":
                        // handle button
                        final String btn = data.get("btn").toString();
                        if (LOGGER.isDebugEnabled()) {
                            LOGGER.debug("Player \"{}\" pressed button \"{}\"", player.getName(), btn);
                        }
                        break;
                    default:
                        // unknown request
                        LOGGER.error("Unknown request type: {} with data: {}", type, data.toJSONString());
                        response = "500\r\n";
                        break;
                }
            } else {
                LOGGER.error("Error parsing request");
            }

            // write response
            BufferedWriter out = new BufferedWriter(new OutputStreamWriter(this.socket.getOutputStream(), "UTF-8"));
            out.write(response);
            out.write("\r\n");

            out.flush();

            out.close();
            in.close();
        } catch (final IOException e) {
            //report exception somewhere.
            e.printStackTrace();
        }
    }
    /**
     * Convert raw IP address to string.
     *
     * @param rawBytes raw IP address.
     * @return a string representation of the raw ip address.
     */
    public static String getIpAddress(byte[] rawBytes) {
        int i = 4;
        String ipAddress = "";
        for (byte raw : rawBytes)
        {
            ipAddress += (raw & 0xFF);
            if (--i > 0)
            {
                ipAddress += ".";
            }
        }

        return ipAddress;
    }
}