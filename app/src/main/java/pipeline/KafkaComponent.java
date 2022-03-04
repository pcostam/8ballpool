package pipeline;

import kafka.Consumer;
import kafka.KafkaUtils;
import kafka.Producer;

import java.util.concurrent.ExecutionException;

/*
Only to test 2nd part.
Single-model consumer
 */
public class KafkaComponent {
    public static void doKafkaComponent(String consumerGroupId, String consumerTopicName, int noConsumerThread) throws ExecutionException, InterruptedException {
        KafkaUtils.createNewTopic(consumerTopicName);
        doProducer();
        doConsumer(consumerGroupId, consumerTopicName, noConsumerThread);
    }

    public static void doProducer() {
        Producer producer = new Producer();
        Thread t1 = new Thread(producer);
        t1.start();

    }

    public static void doConsumer(String consumerGroupId, String consumerTopicName, int noConsumerThread) {
        Consumer consumers = new Consumer(consumerGroupId, consumerTopicName);
        consumers.execute(noConsumerThread);
        consumers.shutdown();
    }
}
