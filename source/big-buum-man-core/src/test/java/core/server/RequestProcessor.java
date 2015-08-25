package core.server;

import com.eclipsesource.json.Json;
import com.eclipsesource.json.JsonObject;
import com.eclipsesource.json.WriterConfig;

import java.io.*;
import java.math.BigInteger;
import java.net.Socket;
import java.util.Scanner;

/**
 * Created by admin on 25.08.2015.
 */
public class RequestProcessor implements Runnable{

    protected Socket clientSocket = null;
    protected String serverText   = null;

    public RequestProcessor(final Socket paramSocket, final String paramServerText) {
        this.clientSocket = paramSocket;
        this.serverText   = paramServerText;
    }

    public void run() {
        try {
            BufferedReader br = new BufferedReader(new InputStreamReader(this.clientSocket.getInputStream()));
            String content = br.readLine();
            //final Scanner in = new Scanner(this.clientSocket.getInputStream());
            final OutputStream out = this.clientSocket.getOutputStream();

            System.out.println(content);

            JsonObject json = Json.parse(content).asObject();

            final String response = String.format("HTTP/1.1 200 OK\n%s", json.toString(WriterConfig.PRETTY_PRINT));

            out.write(response.getBytes());

            out.close();
            br.close();
        } catch (final IOException e) {
            //report exception somewhere.
            e.printStackTrace();
        }
    }
}