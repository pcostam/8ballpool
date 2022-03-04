package pipeline;

import kafka.Consumer;
import kafka.KafkaUtils;
import kafka.Producer;
import kafka.Streams;

import java.util.concurrent.ExecutionException;

/**
 * Single-model consumer.
 */
public class KafkaStreamsComponent {
    public static void doKafkaStreamsComponent(String outputTopic, String topicName, String groupId, int numberOfThread) throws ExecutionException, InterruptedException {
        KafkaUtils.createNewTopic(outputTopic);
        KafkaUtils.createNewTopic(topicName);

        Producer producerStream = new Producer(topicName);
        Thread t1 = new Thread(producerStream);
        t1.start();

        Streams streams = new Streams(topicName, outputTopic, "streams-application");
        streams.doStream(topicName);

        System.out.println(">>>>CONSUMER");
        Consumer consumerStream = new Consumer(groupId, outputTopic);
        consumerStream.execute(numberOfThread);
        consumerStream.shutdown();

    }
}
