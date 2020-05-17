package hazelcast.q;

import com.hazelcast.client.HazelcastClient;
import com.hazelcast.client.config.ClientConfig;
import com.hazelcast.core.HazelcastInstance;
import com.hazelcast.core.IQueue;
import hazelcast.Common;

public class Consumer implements Common {

    public static void main(String[] args) throws Exception {

        var config = new ClientConfig();
        config.getNetworkConfig()
                .addAddress("localhost:" + PORT_1);

        HazelcastInstance hz = HazelcastClient.newHazelcastClient(config);
        IQueue<Integer> queue = hz.getQueue(QUEUE);
        while ( true ) {
            int item = queue.take();
            System.out.println( "Consumed: " + item );
            if ( item == -1 ) {
                queue.put( -1 );
                break;
            }
            Thread.sleep( 2000 );
        }
        System.out.println( "Consumer Finished!" );
    }

}
