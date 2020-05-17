package hazelcast.topic;

import com.hazelcast.client.HazelcastClient;
import com.hazelcast.client.config.ClientConfig;
import com.hazelcast.core.ITopic;
import com.hazelcast.core.Message;
import com.hazelcast.core.MessageListener;
import hazelcast.Common;

public class Subscriber implements Common {

    public static void main(String[] args) {
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
