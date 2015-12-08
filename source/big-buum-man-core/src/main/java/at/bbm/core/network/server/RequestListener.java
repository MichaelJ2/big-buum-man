package at.bbm.core.network.server;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.json.simple.parser.JSONParser;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class RequestListener extends Thread {

    public static final Logger LOGGER = LogManager.getLogger(RequestListener.class.getName());

    private static RequestListener instance = null;

    private final ExecutorService threadPool = Executors.newFixedThreadPool(10);
    private final JSONParser parser = new JSONParser();
    private final ServerSocket server;

    private boolean running = false;

    private RequestListener(final ServerSocket paramServerSocket) {
        this.server = paramServerSocket;
    }

    public static RequestListener getInstance(final ServerSocket paramServerSocket) {
        if (null == instance) {
            instance = new RequestListener(paramServerSocket);
        }
        return instance;
    }

    @Override
    public void run() {
        if (LOGGER.isDebugEnabled()) {
            LOGGER.debug("Request listener started");
        }
        while(this.running){
            Socket clientSocket = null;
            try {
                clientSocket = server.accept();
            } catch (final IOException e) {
                LOGGER.error("Error accepting client connection: {}", e.getMessage());
            }
            if (null != clientSocket) {
                threadPool.execute(new RequestProcessor(clientSocket, parser));
            }
        }
        threadPool.shutdown();
        try {
            this.threadPool.awaitTermination(1, TimeUnit.SECONDS);
        } catch (final InterruptedException e) {
            LOGGER.error("Error executing requests after shutdown: {}", e.getMessage());
        }
        if (LOGGER.isDebugEnabled()) {
            LOGGER.debug("Request listener stopped");
        }
    }

    public void startListener() {
        if (LOGGER.isDebugEnabled()) {
            LOGGER.debug("Starting request listener ...");
        }
        this.running = true;
        this.start();
    }

    public void stopListener() {
        if (LOGGER.isDebugEnabled()) {
            LOGGER.debug("Stopping request listener ...");
        }
        this.running = false;
    }
}
