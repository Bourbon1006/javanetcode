package zuoye6;

import java.io.*;
import java.net.Socket;

public class FileUploadClient {

    public static void main(String[] args) throws IOException {
        Socket socket = new Socket("localhost", 8080); // 连接到服务端的IP地址和端口号

        FileInputStream fis = new FileInputStream(new File("zuoye6/localfile.txt")); // 读取本地文件
        OutputStream os = socket.getOutputStream(); // 获取Socket的输出流

        byte[] buffer = new byte[1024];
        int readBytesCount;

        while ((readBytesCount = fis.read(buffer)) != -1) { // 从本地文件读取数据，直到读完为止
            os.write(buffer, 0, readBytesCount); // 将读取到的数据写入到Socket的输出流中
        }

        fis.close();
        os.close();

        socket.close();
    }
}