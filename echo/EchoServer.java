package echo;

import java.io.*;
import java.net.*;

public class EchoServer {
    private int port = 8001;

    public EchoServer() {
        try (ServerSocket serverSocket = new ServerSocket(port)) {
            System.out.println("Echo Server is running...");
            while (true) {
                try {
                    Socket socket = serverSocket.accept();
                    new Thread(new EchoHandler(socket)).start();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        new EchoServer();
    }

    private static class EchoHandler implements Runnable {
        private Socket socket;

        public EchoHandler(Socket socket) {
            this.socket = socket;
        }

        private PrintWriter getWriter(Socket socket) throws IOException {
            OutputStream socketOut = socket.getOutputStream();
            return new PrintWriter(socketOut, true);
        }

        private BufferedReader getReader(Socket socket) throws IOException {
            InputStream socketIn = socket.getInputStream();
            return new BufferedReader(new InputStreamReader(socketIn));
        }

        @Override
        public void run() {
            try {
                BufferedReader br = getReader(socket);
                PrintWriter pw = getWriter(socket);
                String msg;
                while ((msg = br.readLine()) != null) {
                    System.out.println("Received from client: " + msg);
                    pw.println("echo" + msg);  // Echo back the received message
                    if (msg.equals("bye")) {
                        break;
                    }
                }
            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                try {
                    if (socket != null) {
                        socket.close();
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
