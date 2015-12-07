package at.bbm.core.server;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.json.simple.parser.JSONParser;

import java.io.IOException;
import java.net.ServerSocket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class ThreadPooledServer {

    public static final Logger LOGGER = LogManager.getLogger(ThreadPooledServer.class.getName());

    private static ThreadPooledServer instance = null;

    private ServerSocket server = null;
    private RequestListener listener = null;

    public static ThreadPooledServer getInstance() {
        if (null == instance) {
            instance = new ThreadPooledServer();
        }
        return instance;
    }

    public void start(final int paramPort) {
        if (LOGGER.isDebugEnabled()) {
            LOGGER.debug("Starting server on port {} ...", paramPort);
        }
        if (null == this.server) {
            try {
                this.server = new ServerSocket(paramPort);
                if (null == this.listener) {
                    this.listener = RequestListener.getInstance(server);
                }
                this.listener.startListener();
                if (LOGGER.isDebugEnabled()) {
                    LOGGER.debug("Server started");
                }
            } catch (final IOException e) {
                e.printStackTrace();
            }
        }
    }

    public boolean isRunning() {
        return ((null != this.server) && !this.server.isClosed());
    }

    public void stop() {
        if (LOGGER.isDebugEnabled()) {
            LOGGER.debug("Stopping server ...");
        }
        if (this.isRunning() && null != this.server) {
            if (!this.server.isClosed()) {
                if (null != this.listener && this.listener.isAlive()) {
                    this.listener.stopListener();
                }
                try {
                    this.server.close();
                    this.server = null;
                    if (LOGGER.isDebugEnabled()) {
                        LOGGER.debug("Server stopped");
                    }
                } catch (final IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}