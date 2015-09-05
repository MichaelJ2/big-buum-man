package core.server;

import java.io.*;
import java.net.Socket;

public class RequestProcessor implements Runnable{

    protected Socket clientSocket = null;

    public RequestProcessor(final Socket paramSocket) {
        this.clientSocket = paramSocket;
    }

    public void run() {
        try {

            // get request
            BufferedReader in = new BufferedReader(new InputStreamReader(this.clientSocket.getInputStream()));
            String line;

            // print every line of the request
            StringBuilder stringBuilder = new StringBuilder();
            while (null != (line = in.readLine()) && !line.isEmpty()) {
                stringBuilder.append(line).append("\n");
            }

            final String request[] = stringBuilder.toString().split("\n");

            System.out.println(String.format("METHOD:\t%s", request[0]));
            System.out.println(String.format("LENGTH:\t%s", request[1]));
            System.out.println(String.format("TYPE:\t%s", request[2]));
            System.out.println(String.format("DATA:\t%s", request[3]));

            // write response
            BufferedWriter out = new BufferedWriter(new OutputStreamWriter(this.clientSocket.getOutputStream(), "UTF-8"));

            final String response = "HTTP/1.1 200 OK\r\n";

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
}