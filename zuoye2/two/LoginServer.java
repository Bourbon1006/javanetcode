package zuoye2.two;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.HashMap;
import java.util.Map;

public class LoginServer {
    private static final int PORT = 10086;
    private static final Map<String, String> userCredentials = new HashMap<>();

    static {
        // 添加预设的用户名和密码
        userCredentials.put("xsyu", "xsyu");
    }

    public static void main(String[] args) {
        try (ServerSocket serverSocket = new ServerSocket(PORT)) {
            System.out.println("服务器已开启");

            while (true) {
                Socket clientSocket = serverSocket.accept();
                System.out.println("客户连接: " + clientSocket);

                Thread clientThread = new Thread(() -> {
                    try (
                            PrintWriter out = new PrintWriter(clientSocket.getOutputStream(), true);
                            BufferedReader in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()))
                    ) {
                        out.println("已连接，请输入用户名:");

                        String username = in.readLine();
                        out.println("请输入密码:");

                        String password = in.readLine();

                        boolean loggedIn = checkCredentials(username, password);

                        if (loggedIn) {
                            out.println("登录成功，请按回车退出！");
                        } else {
                            out.println("登录失败，请按回车退出！");

                        }

                    } catch (IOException e) {
                        e.printStackTrace();
                    } finally {
                        try {
                            clientSocket.close();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                });
                clientThread.start();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static boolean checkCredentials(String username, String password) {
        String storedPassword = userCredentials.get(username);
        return storedPassword != null && storedPassword.equals(password);
    }
}
