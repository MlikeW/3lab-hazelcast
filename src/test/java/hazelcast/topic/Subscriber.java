package hazelcast.topic;

import com.hazelcast.client.HazelcastClient;
import com.hazelcast.client.config.ClientConfig;
import com.hazelcast.core.ITopic;
import com.hazelcast.core.Message;
import com.hazelcast.core.MessageListener;
import hazelcast.Common;
import org.junit.jupiter.api.Test;

public class Subscriber implements Common {

    @Test
    public void TopicSubscriber() {
        new ClientConfig().getNetworkConfig()
                .addAddress(LOCALHOST + PORT_1);
        ITopic<Integer> topic = HazelcastClient.newHazelcastClient().getTopic(TOPIC);
        topic.addMessageListener(new MessageListenerImpl());
        System.out.println("Subscribed.");
    }

    private static class MessageListenerImpl implements MessageListener<Integer> {
        public void onMessage(Message<Integer> m) {
            System.out.println("Received - " + m.getMessageObject());
        }
    }
}
