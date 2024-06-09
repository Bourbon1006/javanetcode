package capture;

import java.util.List;

import org.pcap4j.core.BpfProgram;
import org.pcap4j.core.PcapHandle;
import org.pcap4j.core.PcapNetworkInterface;
import org.pcap4j.core.Pcaps;
import org.pcap4j.packet.Packet;
import org.pcap4j.packet.TcpPacket;
import org.pcap4j.packet.UdpPacket;
import org.pcap4j.packet.IpPacket;

public class Pcap4jNetworkPacketCapture {


    public static void main(String[] args) {
        System.setProperty("logback.configurationFile", "E:/javanetcode/capture/logback.xml");



        int snapshotLength = 65536; // 抓取数据包的最大长度
        int readTimeout = 50; // 以毫秒为单位的读取超时时间

        // try (PcapHandle handle = Pcaps.openLive(networkInterface, 65536, PcapHandle.PromiscuousMode.PROMISCUOUS, 10)) {
        try {
            List<PcapNetworkInterface> allDevs = Pcaps.findAllDevs();
            String networkInterfaceName = allDevs.get(4).getName();
            System.out.println(networkInterfaceName+allDevs.size());

            // 打开指定网络接口
            PcapNetworkInterface networkInterface = Pcaps.getDevByName(networkInterfaceName);

            // 打开捕获器
            PcapHandle handle = networkInterface.openLive(snapshotLength, PcapNetworkInterface.PromiscuousMode.PROMISCUOUS, readTimeout);

            // 设置过滤器，仅捕获 TCP、UDP 和 IP 协议数据包
            handle.setFilter("tcp or udp or ip", BpfProgram.BpfCompileMode.OPTIMIZE);

            while (true) {
                Packet packet = handle.getNextPacket();
                if (packet == null) {
                    //continue;
                }
                try{
                    // 判断协议类型并处理相应的数据包
                    if (packet.contains(TcpPacket.class)) {
                        TcpPacket tcpPacket = packet.get(TcpPacket.class);
                        // 在这里处理 TCP 数据包
                        System.out.println("Captured TCP packet: " + tcpPacket);
                        //break;
                    } else if (packet.contains(UdpPacket.class)) {
                        UdpPacket udpPacket = packet.get(UdpPacket.class);
                        // 在这里处理 UDP 数据包
                        System.out.println("Captured UDP packet: " + udpPacket);

                        //break;
                    } else if (packet.contains(IpPacket.class)) {
                        IpPacket ipPacket = packet.get(IpPacket.class);
                        // 在这里处理 IP 数据包
                        System.out.println("Captured IP packet: " + ipPacket);
                        //break;
                    }
                }
                catch(Exception e){
                    //e.printStackTrace();
                }

            }
        } catch (Exception e) {
            //e.printStackTrace();
        }
    }
}