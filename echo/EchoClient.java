package echo;

import java.net.*;
import java.io.*;

public class EchoClient {
    private String host="localhost";
    private int port=8001;
    private Socket socket;

    public EchoClient()throws IOException{
        socket=new Socket(host,port);
    }
    public static void main(String args[])throws IOException{
        new EchoClient().talk();
    }
    private PrintWriter getWriter(Socket socket)throws IOException{
        OutputStream socketOut = socket.getOutputStream();
        return new PrintWriter(socketOut,true);
    }
    private BufferedReader getReader(Socket socket)throws IOException{
        InputStream socketIn = socket.getInputStream();
        return new BufferedReader(new InputStreamReader(socketIn));
    }
    public void talk()throws IOException {
        try{
            BufferedReader br=getReader(socket);
            PrintWriter pw=getWriter(socket);
            BufferedReader localReader=new BufferedReader(new InputStreamReader(System.in));
            String msg=null;
            while((msg=localReader.readLine())!=null){

                pw.println(msg);
                System.out.println(br.readLine());

                if(msg.equals("bye"))
                    break;
            }
        }catch(IOException e){
            e.printStackTrace();
        }finally{
            try{socket.close();}catch(IOException e){e.printStackTrace();}
        }
    }
}

