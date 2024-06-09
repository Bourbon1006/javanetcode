package capture;

import jpcap.JpcapCaptor;
import jpcap.NetworkInterface;
import jpcap.PacketReceiver;
import jpcap.packet.IPPacket;
import jpcap.packet.Packet;
import jpcap.packet.TCPPacket;
import jpcap.packet.UDPPacket;

import java.util.Arrays;

public class JpcapNetworkPacketCapture {

    public static void main(String[] args) {
        try {
            // 获取网络接口列表
            NetworkInterface[] devices = JpcapCaptor.getDeviceList();

            for(NetworkInterface device : devices){
                System.out.println(device);
            }

            // 选择要抓取的网络接口，这里选择第一个网络接口
            NetworkInterface device = devices[4];

            // 打开网络接口
            JpcapCaptor captor = JpcapCaptor.openDevice(device, 65535, false, 20);

            // 开始抓取数据包
            captor.loopPacket(-1, new PacketHandler());

        } catch (Exception e) {
            e.printStackTrace();
        }
}

    // 定义数据包处理器
    static class PacketHandler implements PacketReceiver {
        @Override
        public void receivePacket(Packet packet) {
            if (packet instanceof TCPPacket tcpPacket) {
                // 在这里处理 TCP 数据包
                System.out.println("Captured TCP packet: " + tcpPacket);
            } else if (packet instanceof UDPPacket udpPacket) {
                // 在这里处理 UDP 数据包
                System.out.println("Captured UDP packet: " + udpPacket);
            } else if (packet instanceof IPPacket ipPacket) {
                // 在这里处理 IP 数据包
                System.out.println("Captured IP packet: " + ipPacket);
                System.out.println("版本：IPv4");
                System.out.println("优先权：" + ipPacket.priority);
                System.out.println("区分服务：最大的吞吐量： " + ipPacket.t_flag);
                System.out.println("区分服务：最高的可靠性：" + ipPacket.r_flag);
                System.out.println("长度：" + ipPacket.length);
                System.out.println("标识：" + ipPacket.ident);
                System.out.println("DF:Don't Fragment: " + ipPacket.dont_frag);
                System.out.println("NF:Nore Fragment: " + ipPacket.more_frag);
                System.out.println("片偏移：" + ipPacket.offset);
                System.out.println("生存时间："+ ipPacket.hop_limit);

                String protocol ="";
                switch(ipPacket.protocol)
                {
                case 1:protocol = "ICMP";break;
                case 2:protocol = "IGMP";break;
                case 6:protocol = "TCP";break;
                case 8:protocol = "EGP";break;
                case 9:protocol = "IGP";break;
                case 17:protocol = "UDP";break;
                case 41:protocol = "IPv6";break;
                case 89:protocol = "OSPF";break;
                default : break;
                }
                System.out.println("协议：" + protocol);
                System.out.println("源IP " + ipPacket.src_ip.getHostAddress());
                System.out.println("目的IP " + ipPacket.dst_ip.getHostAddress());
                System.out.println("源主机名： " + ipPacket.src_ip);
                System.out.println("目的主机名： " + ipPacket.dst_ip);
                System.out.println("数据： " + Arrays.toString(ipPacket.data));
                System.out.println("----------------------------------------------");
            }
        }
    }
}

// import jpcap.*;
// import jpcap.packet.*;

// public class PacketCapture {

//     public static void main(String[] args) throws Exception {

//         // 获取网络接口列表
//         NetworkInterface[] devices = JpcapCaptor.getDeviceList();

//         // 选择一个网络接口（示例中选择第一个接口）
//         NetworkInterface device = devices[0];

//         // 打开网络接口，准备捕获数据包
//         JpcapCaptor captor = JpcapCaptor.openDevice(device, 2000, false, 20);

//         // 开始捕获数据包
//         while (true) {

//             // 捕获单个数据包
//             Packet packet = captor.getPacket();

//             // 如果捕获到数据包，输出相关信息
//             if (packet != null) {
//                 System.out.println(packet);
//             }
//         }
//     }
// }
