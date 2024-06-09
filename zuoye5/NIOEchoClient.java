package zuoye5;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SocketChannel;
import java.util.Scanner;

public class NIOEchoClient {
    private static final String SERVER_ADDRESS = "localhost";
    private static final int SERVER_PORT = 12345;

    public static void main(String[] args) {
        try {
            // 打开SocketChannel
            SocketChannel socketChannel = SocketChannel.open(new InetSocketAddress(SERVER_ADDRESS, SERVER_PORT));
            socketChannel.configureBlocking(false);

            Scanner scanner = new Scanner(System.in);
            ByteBuffer buffer = ByteBuffer.allocate(1024);

            while (true) {
                System.out.print("Enter message: ");
                String message = scanner.nextLine();
                buffer.clear();
                buffer.put(message.getBytes());
                buffer.flip();
                socketChannel.write(buffer);

                buffer.clear();
                int bytesRead = socketChannel.read(buffer);
                if (bytesRead > 0) {
                    buffer.flip();
                    byte[] bytes = new byte[buffer.remaining()];
                    buffer.get(bytes);
                    System.out.println("Received from server: " + new String(bytes));
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
