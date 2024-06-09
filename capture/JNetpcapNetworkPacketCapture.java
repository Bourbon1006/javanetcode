package capture;

import org.jnetpcap.Pcap;
import org.jnetpcap.PcapIf;
import org.jnetpcap.packet.JPacket;
import org.jnetpcap.packet.JPacketHandler;
import org.jnetpcap.protocol.tcpip.Tcp;
import org.jnetpcap.protocol.tcpip.Udp;
import org.jnetpcap.protocol.network.Ip4;

import java.util.ArrayList;
import java.util.List;

public class JNetpcapNetworkPacketCapture {

  public static void main(String[] args) {
    List<PcapIf> allDevs = new ArrayList<>(); // 存储所有网络接口
    StringBuilder errbuf = new StringBuilder(); // 错误信息

    // 获取所有网络接口
    int r = Pcap.findAllDevs(allDevs, errbuf);
    if (r != Pcap.OK || allDevs.isEmpty()) {
      System.err.printf("Can't read list of devices, error is %s", errbuf.toString());
      return;
    }

    // 选择要抓取的网络接口，这里选择第一个网络接口
    PcapIf device = allDevs.get(4);

    // 打开选定的网络接口
    int snaplen = 64 * 1024; // 捕获的数据包大小限制
    int flags = Pcap.MODE_PROMISCUOUS; // 捕获模式
    int timeout = 10 * 1000; // 超时时间，单位为毫秒
    Pcap pcap = Pcap.openLive(device.getName(), snaplen, flags, timeout, errbuf);
    if (pcap == null) {
      System.err.printf("Error while opening device for capture: %s\n", errbuf.toString());
      return;
    }

    // 创建数据包处理器
    JPacketHandler<String> packetHandler = new JPacketHandler<String>() {
      final Tcp tcp = new Tcp();
      final Udp udp = new Udp();
      final Ip4 ip4 = new Ip4();

      @Override
      public void nextPacket(JPacket packet, String user) {
        if (packet.hasHeader(tcp)) {
          // 处理 TCP 数据包
          System.out.println("Captured TCP packet: " + tcp.toString());
        } else if (packet.hasHeader(udp)) {
          // 处理 UDP 数据包
          System.out.println("Captured UDP packet: " + udp.toString());
        } else if (packet.hasHeader(ip4)) {
          // 处理 IP 数据包
          System.out.println("Captured IP packet: " + ip4.toString());
        }
      }
    };

    // 开始捕获数据包
    pcap.loop(Pcap.LOOP_INFINITE, packetHandler, "");

    // 关闭网络接口
    pcap.close();
  }
}
