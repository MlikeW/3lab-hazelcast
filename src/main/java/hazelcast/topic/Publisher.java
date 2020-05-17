package hazelcast.topic;

import com.hazelcast.client.HazelcastClient;
import com.hazelcast.client.config.ClientConfig;
import com.hazelcast.core.ITopic;
import hazelcast.Common;

public class Publisher implements Common {

    public static void main(String[] args) throws InterruptedException {
        var config = new ClientConfig();

        var address = LOCALHOST + PORT_1;
        config.getNetworkConfig()
                .addAddress(address);

        var hz = HazelcastClient.newHazelcastClient(config);
        ITopic<Integer> topic = hz.getTopic(TOPIC);

        System.out.println(BREAK_LINE + "Starting...");

        for (int i = 0; i < 10; i++) {
            topic.publish(i);
            System.out.println("Published -  " + i);
            Thread.sleep(1000);
        }
        System.out.println("DONE." + BREAK_LINE);
    }
}
