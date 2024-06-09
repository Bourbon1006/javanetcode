package zuoye;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class DNSResolver {

    public static void main(String[] args) {
        try {
            // 解析域名获取IP地址
            String domain = "www.xsyu.edu.cn";
            InetAddress[] addresses = InetAddress.getAllByName(domain);
            System.out.println( domain + ":");
            for (InetAddress address : addresses) {
                System.out.println("该ip地址为:" +address.getHostAddress());

            }

            // 获取当前主机的IP地址
            InetAddress localHost = InetAddress.getLocalHost();
            System.out.println("主机ip地址: " + localHost.getHostAddress());
        } catch (UnknownHostException e) {
            System.err.println("DNS resolution failed: " + e.getMessage());
        }
    }
}

