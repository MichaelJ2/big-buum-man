package at.bbm.core.network;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.*;
import java.net.Socket;
import java.net.SocketTimeoutException;

public class Connection {

    public static final Logger LOGGER = LogManager.getLogger(Connection.class.getName());

    public static final String EOM = "/EOM";

    private Socket socket = null;
    private BufferedWriter writer = null;
    private BufferedReader reader = null;

    private final StringBuilder stringBuilder = new StringBuilder();
    private String lastInput = "";
    private String lastOutput = "";
    private String in = "";

    private boolean connected = false;

    public Connection(final Socket paramSocket) {
        connect(paramSocket);
    }

    private final void send(final String paramMessage) {
        if (connected) {
            try {
                writer.write(paramMessage);
                writer.write("\n");
                writer.write(EOM);
                writer.write("\n");
                writer.flush();
                lastOutput = paramMessage;
            } catch (final IOException e) {
                LOGGER.error("Error sending data: {}", e.getMessage());
            }
        }
    }

    private final String receive() {
            try {
                stringBuilder.setLength(0);
                while (true) {
                    in = reader.readLine();
                    if (null == in || EOM.equals(in)) {
                        break;
                    }
                    stringBuilder.append(in).append("\n");
                }
                lastInput = stringBuilder.toString().trim();
            } catch (final SocketTimeoutException e) {
                LOGGER.error("Connection timed out: {}", e.getMessage());
                // TODO: check if player is still connected, otherwise disconnect him
                //close();
            } catch (final IOException e) {
                LOGGER.error("Error receiving data: {}", e.getMessage());
            }
        return lastInput;
    }

    public synchronized final String getResponse(final String paramRequest) {
        if (connected) {
            send(paramRequest);
            receive();
        }
        return lastInput;
    }

    public synchronized final String getRequest() {
        if (connected) {
            receive();
        }
        return lastInput;
    }

    public synchronized final void sendResponse(final String paramResponse) {
        if (connected) {
            send(paramResponse);
        }
    }

    public final String getLastInput() {
        return lastInput;
    }

    public final String getLastOutput() {
        return lastOutput;
    }

    public final void connect(final Socket paramSocket) {
        socket = paramSocket;
        try {
            socket.setSoTimeout(2000);
            writer = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream(), "UTF-8"));
            reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            connected = true;
        } catch (final Exception e) {
            LOGGER.error("Error opening Socket: {}", e.getMessage());
            connected = false;
        }
    }

    public final void close() {
        connected = false;
        try {
            if (null != writer) {
                writer.close();
            }
        } catch (final IOException e) {
            LOGGER.error("Error closing OutputStream: {}", e.getMessage());
        }

        try {
            if (null != reader) {
                reader.close();
            }
        } catch (final IOException e) {
            LOGGER.error("Error closing InputStream: {}", e.getMessage());
        }

        if (null != socket && !socket.isClosed()) {
            try {
                socket.close();
            } catch (final IOException e) {
                LOGGER.error("Error closing Socket: {}", e.getMessage());
            }
        }
    }
}
