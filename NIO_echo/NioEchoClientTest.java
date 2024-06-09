package NIO_echo;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;

import static org.junit.Assert.assertEquals;

public class NioEchoClientTest {
    private ServerSocketChannel serverSocketChannel;
    private Thread serverThread;

    @Before
    public void setUp() throws IOException {
        serverSocketChannel = ServerSocketChannel.open();
        serverSocketChannel.bind(new InetSocketAddress(8081));
        serverSocketChannel.configureBlocking(false);

        serverThread = new Thread(() -> {
            try {
                EchoServer.main(new String[]{});
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
        serverThread.start();
    }

    @After
    public void tearDown() throws IOException {
        serverSocketChannel.close();
        serverThread.interrupt();
    }

    @Test
    public void testCommunication() throws IOException, InterruptedException {
        // 创建客户端并发送消息
        SocketChannel client = SocketChannel.open(new InetSocketAddress("localhost", 8081));
        client.configureBlocking(false);

        String message = "Hello, Server!";
        ByteBuffer outgoingBuffer = ByteBuffer.wrap(message.getBytes());
        client.write(outgoingBuffer);
        outgoingBuffer.clear();

        // 接收服务器的响应
        ByteBuffer incomingBuffer = ByteBuffer.allocate(256);
        int bytesRead = client.read(incomingBuffer);
        incomingBuffer.flip();

        // 验证服务器的响应
        assertEquals(message, new String(incomingBuffer.array(), 0, bytesRead));

        client.close();
    }
}
