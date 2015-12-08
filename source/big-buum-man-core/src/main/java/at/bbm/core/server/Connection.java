package at.bbm.core.server;

import java.io.*;
import java.net.Socket;

public class Connection {

    private Socket socket;
    private BufferedWriter wr = null;
    private BufferedReader rd = null;

    private boolean connected;

    Connection(final Socket paramSocket) {
        this.connect(paramSocket);
    }

    public String getResponse(final String paramRequest) {
        String response = "";
        if (this.connected) {
            try {
                wr.write(paramRequest);
                wr.write("\r\n");
                wr.flush();
                response = rd.readLine();
            } catch (IOException paramE) {
                paramE.printStackTrace();
            }
        }
        return response;
    }

    public void connect(final Socket paramSocket) {
        this.socket = paramSocket;
        try {
            this.wr = new BufferedWriter(new OutputStreamWriter(this.socket.getOutputStream(), "UTF-8"));
            this.rd = new BufferedReader(new InputStreamReader(this.socket.getInputStream()));
            this.connected = true;
        } catch (final IOException e) {
            this.connected = false;
        }
    }

    public void close() {
        try {
            this.wr.close();
            this.rd.close();
        } catch (final IOException e) {
            // NOP
        } finally {
            if (null != this.socket && !this.socket.isClosed()) {
                try {
                    this.socket.close();
                } catch (final IOException e) {
                    // NOP
                }
            }
        }
    }
}
