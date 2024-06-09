package zuoye;


import org.pcap4j.core.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.util.List;

public class AllInterface
{
    public static void main(String[] args) throws PcapNativeException, InterruptedException
    {
        System.setProperty("logback.configurationFile", "E:/javanetcode/zuoye/logback.xml");

        try {
            List<PcapNetworkInterface> allDevs = Pcaps.findAllDevs();
            //String networkInterfaceName = allDevs.get(4).getName();
            for(PcapNetworkInterface networkInterface : allDevs)
            {
                System.out.println(networkInterface.getDescription());
            }
        }
        catch (PcapNativeException e) {
            e.printStackTrace();
        }
    }
}
