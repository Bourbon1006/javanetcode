package zuoye4;

import javax.net.ssl.*;
import java.io.*;
import java.security.KeyStore;

public class EchoSSLClient {
    /**
     * 主函数，用于通过SSL/TLS连接到服务器，并进行简单的交互。
     *
     * @param args 命令行参数（未使用）
     * @throws Exception 如果加载TrustStore、初始化SSL上下文、创建Socket或读写数据过程中发生错误
     */
    public static void main(String[] args) throws Exception {
        // 加载并初始化TrustStore，用于验证服务器的证书
        KeyStore trustStore = KeyStore.getInstance("JKS");
        trustStore.load(new FileInputStream("zuoye4/client.truststore"), "password".toCharArray());

        // 创建TrustManagerFactory并用上述TrustStore初始化
        TrustManagerFactory trustManagerFactory = TrustManagerFactory.getInstance(TrustManagerFactory.getDefaultAlgorithm());
        trustManagerFactory.init(trustStore);

        // 初始化SSLContext，用于创建安全的Socket连接
        SSLContext sslContext = SSLContext.getInstance("TLS");
        sslContext.init(null, trustManagerFactory.getTrustManagers(), null);

        // 使用SSLContext创建SSLSocket并连接到服务器
        SSLSocketFactory ssf = sslContext.getSocketFactory();
        SSLSocket socket = (SSLSocket) ssf.createSocket("localhost", 8081);

        // 通过SSLSocket建立的连接进行读写操作
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
             PrintWriter writer = new PrintWriter(socket.getOutputStream(), true);
             BufferedReader console = new BufferedReader(new InputStreamReader(System.in))) {

            String userInput;
            // 用户与服务器之间的交互循环
            while ((userInput = console.readLine()) != null) {
                writer.println(userInput);
                System.out.println("Server: " + reader.readLine());
                if ("bye".equalsIgnoreCase(userInput)) {
                    break; // 如果用户输入"bye"，则退出循环
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            socket.close(); // 最后关闭Socket连接
        }
    }

}
