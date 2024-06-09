package zuoye;

import java.net.URL;

public class URLParser {
    public static void main(String[] args) {
        String urlString = "ftp://mp3:mp3@138.24.121.61:21000/javafaq/books/jnp/index.html?isbn=1565922069#toc";

        try {
            URL url = new URL(urlString); //将string解析为url格式

            System.out.println("Protocol: " + url.getProtocol());
            System.out.println("Authority: " + url.getAuthority());
            System.out.println("User Info: " + url.getUserInfo());
            System.out.println("Host: " + url.getHost());
            System.out.println("Port: " + url.getPort());
            System.out.println("Path: " + url.getPath());
            System.out.println("File: " + url.getFile());
            System.out.println("Ref: " + url.getRef());
            System.out.println("Query: " + url.getQuery());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
