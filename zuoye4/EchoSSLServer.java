package zuoye4;

import javax.net.ssl.*;
import java.io.*;
import java.security.KeyStore;

public class EchoSSLServer {
    /**
     * 主函数，用于创建并启动一个SSL Echo服务器。该服务器加载指定的KeyStore以初始化SSL上下文，
     * 然后通过SSL协议监听8081端口，接收并回应客户端的请求。
     *
     * @param args 命令行参数（在此示例中未使用）
     * @throws Exception 如果在加载KeyStore、初始化SSL上下文或创建服务器套接字过程中发生错误
     */
        public static void main(String[] args) throws Exception {
            // 加载并初始化服务器的KeyStore
            KeyStore keyStore = KeyStore.getInstance("JKS");
            keyStore.load(new FileInputStream("zuoye4/server.keystore"), "password".toCharArray());

            // 初始化KeyManagerFactory并使用KeyStore
            KeyManagerFactory keyManagerFactory = KeyManagerFactory.getInstance(KeyManagerFactory.getDefaultAlgorithm());
            keyManagerFactory.init(keyStore, "password".toCharArray());

            // 创建SSL上下文并初始化
            SSLContext sslContext = SSLContext.getInstance("TLS");
            sslContext.init(keyManagerFactory.getKeyManagers(), null, null);

            // 使用SSL上下文创建SSLServerSocket并监听端口8081
            SSLServerSocketFactory ssf = sslContext.getServerSocketFactory();
            SSLServerSocket sss = (SSLServerSocket) ssf.createServerSocket(8081);

            System.out.println("SSL Echo Server started");

            // 不断接受客户端连接，并为每个连接创建新的线程处理
            while (true) {
                SSLSocket socket = (SSLSocket) sss.accept();
                new Thread(new EchoHandler(socket)).start();
            }
        }
}

class EchoHandler implements Runnable {
    private SSLSocket socket;

    public EchoHandler(SSLSocket socket) {
        this.socket = socket;
    }

    @Override
    public void run() {
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
             PrintWriter writer = new PrintWriter(socket.getOutputStream(), true)) {

            String line;
            while ((line = reader.readLine()) != null) {
                writer.println(line);
                if ("bye".equalsIgnoreCase(line)) {
                    break;
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
