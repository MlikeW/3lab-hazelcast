package hazelcast;

import com.hazelcast.client.HazelcastClient;
import com.hazelcast.client.config.ClientConfig;
import com.hazelcast.core.HazelcastInstance;

public class DisMap implements Common{

    private final HazelcastInstance hazelcastInstance;

    public DisMap(HazelcastInstance hazelcastInstance) {
        this.hazelcastInstance = hazelcastInstance;
    }

    public void setData(String key, String content) {
        hazelcastInstance.getMap("data")
                .set(key, content);
    }
//    public Map<String, String> getData() {
//        return hazelcastInstance.getMap("data");
//    }

    public static void main(String[] args) {
        var config = new ClientConfig();
        config.getNetworkConfig()
                .addAddress(LOCALHOST + PORT_1);

        HazelcastInstance hz = HazelcastClient.newHazelcastClient(config);
        var dm = new DisMap(hz);
        for (int i = 0; i < 1000; i++) {
            dm.setData(String.valueOf(i), "coolValue");
        }
    }
}
