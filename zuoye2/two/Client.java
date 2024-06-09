package zuoye2.two;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class Client {
    public static void main(String[] args) {
        final String SERVER_ADDRESS = "localhost";
        final int PORT = 10086;
        int a = 0;

        try (
                Socket socket = new Socket(SERVER_ADDRESS, PORT);
                PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
                BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                BufferedReader userInput = new BufferedReader(new InputStreamReader(System.in))
        ) {
            System.out.println("已连接到10086，输入你的信息:");
            String serverResponse;
            while ((serverResponse = in.readLine()) != null) {
                System.out.println("10086: " + serverResponse);

                String userInputLine = userInput.readLine();
                out.println(userInputLine);

                a++;
                if (a>2) {
                    System.out.println("再见！");
                    break;
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
