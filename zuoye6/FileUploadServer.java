package zuoye6;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;

public class FileUploadServer {

    public static void main(String[] args) throws IOException {
        ServerSocket serverSocket = new ServerSocket(8080); // 创建ServerSocket，监听8080端口

        while (true) { // 循环等待客户端连接
            Socket clientSocket = serverSocket.accept(); // 接受客户端连接
            System.out.println("New client connected");

            InputStream is = clientSocket.getInputStream(); // 获取客户端发送的文件的输入流
            FileOutputStream fos = new FileOutputStream(new File("zuoye6/receivedFile.txt")); // 在服务器上创建新文件

            byte[] buffer = new byte[1024];
            int readBytesCount;

            while ((readBytesCount = is.read(buffer)) != -1) { // 从输入流读取数据，直到读完为止
                fos.write(buffer, 0, readBytesCount); // 将读取到的数据写入到服务器上的文件中
            }

            fos.close();
            is.close();

            clientSocket.close();
        }
    }
}