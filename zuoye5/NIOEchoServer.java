package zuoye5;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.Iterator;

public class NIOEchoServer {
    private static final int PORT = 12345;

    /**
     * 主函数，用于创建并启动一个非阻塞模式的服务器，接收客户端连接并处理读写操作。
     *
     * @param args 命令行参数（未使用）
     */
    public static void main(String[] args) {
        try {
            // 初始化ServerSocketChannel并绑定到指定端口
            ServerSocketChannel serverSocketChannel = ServerSocketChannel.open();
            serverSocketChannel.socket().bind(new InetSocketAddress(PORT));
            serverSocketChannel.configureBlocking(false);

            // 创建并初始化Selector
            Selector selector = Selector.open();
            serverSocketChannel.register(selector, SelectionKey.OP_ACCEPT);

            // 进入服务循环，等待客户端连接和数据读取
            while (true) {
                selector.select(); // 非阻塞模式下等待事件

                // 处理选择器上就绪的事件
                Iterator<SelectionKey> keyIterator = selector.selectedKeys().iterator();
                while (keyIterator.hasNext()) {
                    SelectionKey key = keyIterator.next();
                    keyIterator.remove(); // 移除当前处理的键，以避免重复处理

                    if (key.isAcceptable()) {
                        // 当服务端口可以接受连接时，接受连接并注册为读取操作
                        ServerSocketChannel serverChannel = (ServerSocketChannel) key.channel();
                        SocketChannel socketChannel = serverChannel.accept();
                        socketChannel.configureBlocking(false);
                        socketChannel.register(selector, SelectionKey.OP_READ);
                    } else if (key.isReadable()) {
                        // 当通道可以读取数据时，读取数据并回写到客户端
                        SocketChannel socketChannel = (SocketChannel) key.channel();
                        ByteBuffer buffer = ByteBuffer.allocate(1024);
                        int bytesRead = socketChannel.read(buffer);

                        if (bytesRead == -1) {
                            // 如果读取长度为-1，关闭通道
                            socketChannel.close();
                        } else {
                            // 否则，将读取的数据写回客户端
                            buffer.flip();
                            socketChannel.write(buffer);
                        }
                    }
                }
            }
        } catch (IOException e) {
            // 打印IO异常堆栈跟踪
            e.printStackTrace();
        }
    }
}
