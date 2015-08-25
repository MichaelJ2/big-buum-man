package core.server;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class ThreadPooledServer {

    private static ThreadPooledServer instance = null;
    private ServerSocket server = null;
    protected Thread listener = null;
    protected ExecutorService threadPool = Executors.newFixedThreadPool(10);

    public static ThreadPooledServer getInstance() {
        if (null == instance) {
            instance = new ThreadPooledServer();
        }
        return instance;
    }

    public synchronized void start(final int paramPort) {
        if (null == this.server) {
            try {
                this.server = new ServerSocket(paramPort);
                this.startSocketListener();
            } catch (final IOException e) {
                e.printStackTrace();
            }
        }
    }

    private void startSocketListener() {
        if (null == this.listener) {
            this.listener = new Thread(new Runnable() {
                @Override
                public void run() {
                    while(isRunning()){
                        Socket clientSocket = null;

                        try {
                            clientSocket = server.accept();
                        } catch (final IOException e) {
                            if (!isRunning()) {
                                System.out.println("Error accepting client connection");
                                break;
                            }
                        }

                        if (null != clientSocket) {
                            threadPool.execute(new RequestProcessor(clientSocket, "Thread Pooled Server"));
                        }
                    }
                    threadPool.shutdown();
                    System.out.println("Server Stopped.") ;
                }
            });
            this.listener.start();
        }

    }


    public synchronized boolean isRunning() {
        return ((null != this.server) && !this.server.isClosed());
    }

    public synchronized void stop() {
        if (this.isRunning() && null != this.server) {
            if (!this.server.isClosed()) {
                if (null != this.listener && this.listener.isAlive()) {
                    this.listener.interrupt();
                    this.listener = null;
                }
                try {
                    this.server.close();
                    this.server = null;
                } catch (final IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}