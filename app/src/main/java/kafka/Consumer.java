package kafka;

import events.Event;
import events.InApp;
import events.Init;
import events.Match;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.Duration;
import java.util.Arrays;
import java.util.Properties;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * Single consumer, multiple worker processing threads
 */
public class Consumer {
    private final KafkaConsumer<String, ? extends Event> consumer;
    private final String topic;
    // Threadpool of consumers
    private ExecutorService executor;

    public Consumer(String groupId, String topic) {
        Properties properties = createConsumerConfig(groupId);
        this.consumer = new KafkaConsumer(properties);
        this.topic = topic;
        this.consumer.subscribe(Arrays.asList(this.topic));
    }

    /**
     * Creates a {@link ThreadPoolExecutor} with a given number of threads to consume the messages.
     *
     * @param numberOfThreads The number of threads will be used to consume the message
     */
    public void execute(int numberOfThreads) {
        final Logger logger = LoggerFactory.getLogger(Consumer.class);
        logger.info("Initialize execution");
        // Initialize a ThreadPool with size = 5 and use the BlockingQueue with size =1000 to
        // hold submitted tasks.
        System.out.println(">>>>>executor");
        executor = new ThreadPoolExecutor(numberOfThreads, numberOfThreads, 0L, TimeUnit.MILLISECONDS,
                new ArrayBlockingQueue<Runnable>(1000), new ThreadPoolExecutor.CallerRunsPolicy());

        while (true) {
            ConsumerRecords<String, ? extends Event> records = consumer.poll(Duration.ofMillis(100));
            for (ConsumerRecord<String, ? extends Event> record : records) {
                if(record.value() instanceof Init) {
                    ConsumerRecord<String, Init> subtypeRecord = (ConsumerRecord<String, Init> ) record;
                    //received new record new record type Init
                    logger.info( "Received new records: " + " key: " + subtypeRecord.key() +
                                " value country: " + subtypeRecord.value().getCountry() +
                                " value user id: " + subtypeRecord.value().getUserId()+
                                " value platform: "+ subtypeRecord.value().getPlatform()+
                                " value time: " + subtypeRecord.value().getTime()+ "\n");
                }
                else if(record.value() instanceof InApp){
                    ConsumerRecord<String, InApp> subtypeRecord = (ConsumerRecord<String, InApp> ) record;
                    //received new record type InApp
                    logger.info( "Received new records: " + " key: " + subtypeRecord.key() +
                            " value user id: " + subtypeRecord.value().getUserId()+
                            " value product id :" + subtypeRecord.value().getProductId()+
                            " value purchase value:" + subtypeRecord.value().getPurchaseValue()+
                            " value time: "+ subtypeRecord.value().getTime()+
                            "\n");
                }

                else if(record.value() instanceof Match){
                    ConsumerRecord<String, Match> subtypeRecord = (ConsumerRecord<String, Match> ) record;
                    //received new record type InApp
                    logger.info( "Received new records: " + " key: " + subtypeRecord.key() +
                            " value user id A: " + subtypeRecord.value().getUserA()+
                            " value user id  B:" + subtypeRecord.value().getUserB()+
                            "\n");
                }


                executor.submit(new ConsumerThreadHandler(record));
            }
        }
    }

    /**
     * Create properties configuration for consumer
     * @param groupId
     * @return Properties
     */
    private static Properties createConsumerConfig(String groupId) {
        Properties properties = new Properties();
        properties.setProperty(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, KafkaConfig.BOOTSTRAP_SERVER);
        properties.setProperty(ConsumerConfig.GROUP_ID_CONFIG, groupId);
        properties.setProperty(ConsumerConfig.ENABLE_AUTO_COMMIT_CONFIG, "true");
        properties.setProperty(ConsumerConfig.AUTO_COMMIT_INTERVAL_MS_CONFIG, "1000");
        properties.setProperty(ConsumerConfig.SESSION_TIMEOUT_MS_CONFIG, "30000");
        properties.setProperty(ConsumerConfig.AUTO_OFFSET_RESET_CONFIG, "earliest");
        properties.setProperty(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class.getName());
        properties.setProperty(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, KafkaJsonDeserializer.class.getName());
        return  properties;
    }

    /**
     *  Close the consumer and shutdown executor
     */
    public void shutdown() {
        if (consumer != null) {
            consumer.close();
        }
        if (executor != null) {
            executor.shutdown();
        }
        try {
            if (!executor.awaitTermination(5000, TimeUnit.MILLISECONDS)) {
                System.out
                        .println("Timed out waiting for consumer threads to shut down, exiting uncleanly");
            }
        } catch (InterruptedException e) {
            System.out.println("Interrupted during shutdown, exiting uncleanly");
        }
    }
}
