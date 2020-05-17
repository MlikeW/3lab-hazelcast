package hazelcast;

import com.hazelcast.client.config.ClientConfig;

import java.util.List;

public interface Common {

    String LOCALHOST = "localhost:";
    String PORT_1 = "5701";
    String PORT_2 = "5702";
    String PORT_3 = "5703";

    List<String> PORTS_LIST = List.of(PORT_1,PORT_2,PORT_3);

    String PESSIMISTIC = "pessimistic";
    String OPTIMISTIC = "optimistic";
    String NONE = "none";
    String BREAK_LINE = "\n_______________________________________________\n";

    String QUEUE = "bounded";
    String TOPIC = "topic";
    String LOCK = "lock";


    static ClientConfig getClientConfig(String port) {
        var config = new ClientConfig();
        config.getNetworkConfig()
                .addAddress(LOCALHOST + port);
        return config;
    }
}
