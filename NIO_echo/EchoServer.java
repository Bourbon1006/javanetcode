package NIO_echo;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.Iterator;

public class EchoServer {
    /**
     * 主函数，用于启动服务器并使用非阻塞模式处理客户端连接和数据传输。
     *
     * @param args 命令行参数（未使用）
     * @throws IOException 如果打开选择器或服务器套接字通道时发生IO异常
     */
    public static void main(String[] args) throws IOException {
        // 打开一个选择器
        Selector selector = Selector.open();
        // 打开服务器套接字通道，并绑定到8080端口
        ServerSocketChannel serverSocketChannel = ServerSocketChannel.open();
        serverSocketChannel.bind(new InetSocketAddress(8080));
        // 配置通道为非阻塞模式
        serverSocketChannel.configureBlocking(false);
        // 注册选择器，监听接受操作
        serverSocketChannel.register(selector, SelectionKey.OP_ACCEPT);

        System.out.println("Server is listening on port 8080.");

        // 无限循环，等待和处理事件
        while (true) {
            // 监听并选择就绪的操作
            selector.select();
            // 处理选择器中选择的键集合
            Iterator<SelectionKey> iterator = selector.selectedKeys().iterator();
            while (iterator.hasNext()) {
                SelectionKey key = iterator.next();
                iterator.remove(); // 移除当前处理的键，以避免重复处理

                // 如果通道接受操作就绪
                if (key.isAcceptable()) {
                    // 接受客户端连接
                    SocketChannel socketChannel = serverSocketChannel.accept();
                    socketChannel.configureBlocking(false); // 配置为非阻塞模式
                    // 注册选择器，监听读操作
                    socketChannel.register(selector, SelectionKey.OP_READ);
                } else if (key.isReadable()) {
                    // 读取数据并回写到客户端
                    SocketChannel socketChannel = (SocketChannel) key.channel();
                    ByteBuffer buffer = ByteBuffer.allocate(256); // 分配缓冲区
                    int bytesRead = socketChannel.read(buffer); // 读取数据
                    if (bytesRead == -1) { // 如果读取到EOF，关闭通道
                        socketChannel.close();
                    } else {
                        // 数据反转并写回客户端
                        buffer.flip();
                        socketChannel.write(buffer);
                    }
                }
            }
        }
    }

}
