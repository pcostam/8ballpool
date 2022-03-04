/*
 * This Java source file was generated by the Gradle 'init' task.
 */
package pipeline;

import kafka.*;
import mongodb.BulkWriteInsert;
import mongodb.MongoDBSetup;

import java.io.IOException;
import java.util.Collections;
import java.util.Properties;
import java.util.Set;
import java.util.concurrent.ExecutionException;

import org.apache.kafka.clients.admin.*;
import org.apache.kafka.common.KafkaFuture;


public class App {

    public static void main(String[] args) throws IOException, ExecutionException, InterruptedException {
        String topicName = "8sample-topic";
        int numberOfThread = 3;
        String groupId = "java-group-consumer";

        //setup mongoDb
        /*MongoDBSetup setupMongo = new MongoDBSetup();
        BulkWriteInsert.doBulkInsert(setupMongo.getCollection());*/
        //initialize zookeeper and kafka server???

        /*Properties properties = new Properties();
        properties.put(AdminClientConfig.BOOTSTRAP_SERVERS_CONFIG, KafkaConfig.BOOTSTRAP_SERVER);



        try (AdminClient admin = AdminClient.create(properties)) {
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

        }*/


        //sends init and in-app events
        /*Producer producer = new Producer();
        Thread t1 = new Thread(producer);
        t1.start();


        Consumer consumers = new Consumer(groupId, topicName);

        consumers.execute(numberOfThread);

        consumers.shutdown();*/
        String outputTopic = "output-topic";
        KafkaUtils.createNewTopic(outputTopic);


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
