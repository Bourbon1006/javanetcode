package zuoye2.one;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;
public class TCPClient {
    public static void main(String[] args) throws IOException {
        Socket socket = new Socket("localhost", 8888);
        OutputStream os = socket.getOutputStream();
        os.write("hello".getBytes());
        InputStream is = socket.getInputStream();
        byte[] buf = new byte[1024];
        int len = is.read(buf);
        System.out.println(new String(buf, 0, len));
        socket.close();
    }
}

