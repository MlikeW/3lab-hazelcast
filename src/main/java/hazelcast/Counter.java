package hazelcast;

import com.hazelcast.client.HazelcastClient;
import com.hazelcast.core.HazelcastInstance;
import com.hazelcast.core.IMap;

import java.util.Map;
import java.util.concurrent.CompletableFuture;

import static java.util.concurrent.CompletableFuture.runAsync;
import static java.util.stream.Collectors.toList;

public class Counter implements Common {

    public static void main(String[] args) {
        var clients = (PORTS_LIST).stream()
                .map(Common::getClientConfig)
                .map(HazelcastClient::newHazelcastClient)
                .collect(toList());
        var lockMode = NONE;
        var hzClient = clients.get(0);

        IMap<String, Integer> map = hzClient.getMap(lockMode);
        map.put(lockMode, 0);

        var futures = clients.stream()
                .map(client -> parallelCount(client, 1000, lockMode))
                .collect(toList());

        CompletableFuture.allOf(futures.toArray(CompletableFuture[]::new))
                .join();

        map = hzClient.getMap(lockMode);
        Integer result = map.get(lockMode);

        System.out.println(BREAK_LINE + "Result - " + result + BREAK_LINE);

    }

    private static CompletableFuture<Void> parallelCount(HazelcastInstance hazelcastInstance, Integer end, String lockMode) {
        IMap<String, Integer> map = hazelcastInstance.getMap(lockMode);
        return runAsync(constructJob(lockMode, map, end));
    }

    private static Runnable constructJob(String lock, IMap<String, Integer> map, Integer end) {
        switch (lock) {
            case PESSIMISTIC:
                return () -> pessimisticLock(map, lock, end);
            case OPTIMISTIC:
                return () -> optimisticLock(map, lock, end);
            default:
                return () -> noLock(map, NONE, end);
        }
    }

    private static void pessimisticLock(IMap<String, Integer> map, String key, int to) {
        int value;
        for (int i = 0; i < to; i++) {
            map.lock(key);
            try {
                value = map.get(key);
                map.put(key, ++value);
            } finally {
                map.unlock(key);
            }
        }
    }

    private static void optimisticLock(IMap<String, Integer> map, String key, int to) {
        int oldValue;
        int newValue;
        for (int i = 0; i < to; i++) {
            do {
                oldValue = map.get(key);
                newValue = oldValue + 1;
            } while (!map.replace(key, oldValue, newValue));
        }
    }

    private static void noLock(Map<String, Integer> map, String key, int to) {
        int value;
        for (int i = 0; i < to; i++) {
            value = map.get(key);
            map.put(key, ++value);
        }
    }
}