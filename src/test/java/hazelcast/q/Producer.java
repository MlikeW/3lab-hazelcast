package hazelcast.q;

import com.hazelcast.client.HazelcastClient;
import com.hazelcast.client.config.ClientConfig;
import com.hazelcast.core.HazelcastInstance;
import com.hazelcast.core.IQueue;
import hazelcast.Common;
import org.junit.jupiter.api.Test;

public class Producer implements Common {

    @Test
    public void QProducerTest() throws Exception {
        var config = new ClientConfig();
        config.getNetworkConfig()
                .addAddress(LOCALHOST + PORT_1);
        HazelcastInstance hz = HazelcastClient.newHazelcastClient(config);
        IQueue<Integer> queue = hz.getQueue(QUEUE);
        for ( int k = 1; k < 100; k++ ) {
            queue.put( k );
            System.out.println( "Producing: " + k );
            Thread.sleep(500);
        }
        queue.put( -1 );
        System.out.println( "Producer Finished!" );
    }
}
