package at.bbm.core.server;

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

            String type = null;
            JSONObject data = null;

            try {
                final JSONObject jsonObject = (JSONObject) this.parser.parse(rawJson);
                type = jsonObject.get("type").toString();
                data = (JSONObject) jsonObject.get("data");
            } catch (final Exception e) {
                LOGGER.error("Unable to parse JSON Request: \"{}\", request data: {}", e.getMessage(), rawJson);
            }

            if (null != type && null != data) {
                switch (type) {
                    case "reg":
                        final String name = data.get("name").toString();
                        // register new player
                        if (LOGGER.isDebugEnabled()) {
                            LOGGER.debug("Registering new player \"{}\" with name \"{}\"", this.getIpAddress(socket.getInetAddress().getAddress()), name);
                        }
                        PlayerMapper.addPlayer(socket.getInetAddress(), name);
                        break;
                    case "mov":
                        // move player
                        if (LOGGER.isDebugEnabled()) {
                            LOGGER.debug("Moving player \"{}\" to \"{}\"", this.getIpAddress(socket.getInetAddress().getAddress()), data.get("dir"));
                        }
                        PlayerMapper.getPlayer(this.socket.getInetAddress());
                    case "btn":
                        // move player
                        if (LOGGER.isDebugEnabled()) {
                            LOGGER.debug("Player \"{}\" pressed button \"\"", this.getIpAddress(socket.getInetAddress().getAddress()), data.get("btn"));
                        }
                    case "res":
                        // move player
                        if (LOGGER.isDebugEnabled()) {
                            LOGGER.debug("Player \"{}\" is connected.", this.getIpAddress(socket.getInetAddress().getAddress()), data.get("res"));
                        }
                    break;
                    default:
                        // unknown request
                        LOGGER.error("Unknown request type: {} with data: {}", type, data.toJSONString());
                        break;
                }
            } else {
                LOGGER.error("Error parsing request");
            }

            // write response
            BufferedWriter out = new BufferedWriter(new OutputStreamWriter(this.socket.getOutputStream(), "UTF-8"));

            final String response = "200\r\n";
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