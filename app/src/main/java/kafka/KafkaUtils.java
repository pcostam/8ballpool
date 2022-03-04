package kafka;

import org.apache.kafka.clients.admin.*;
import org.apache.kafka.common.KafkaFuture;

import java.util.Collections;
import java.util.Properties;
import java.util.Set;
import java.util.concurrent.ExecutionException;

public class KafkaUtils {
    public static void createNewTopic(String topicName) throws ExecutionException, InterruptedException {
        Properties properties = new Properties();
        properties.put(AdminClientConfig.BOOTSTRAP_SERVERS_CONFIG, KafkaConfig.BOOTSTRAP_SERVER);

        try (
                AdminClient admin = AdminClient.create(properties)) {
            int partitions = 1;
            short replicationFactor = 1;

            ListTopicsOptions options = new ListTopicsOptions();
            options.listInternal(true);
            ListTopicsResult topics = admin.listTopics(options);
            Set<String> currentTopicList = topics.names().get();
            if(!currentTopicList.contains(topicName)) {

                NewTopic newTopic = new NewTopic(topicName, partitions, replicationFactor);

                CreateTopicsResult result = admin.createTopics(
                        Collections.singleton(newTopic)
                );

                KafkaFuture<Void> future = result.values().get(topicName);
                future.get();
            }

        }
    }

}
