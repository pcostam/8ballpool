package kafka;

import events.Event;
import events.InApp;
import events.Init;
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
     * Creates a {@link ThreadPoolExecutor} with a given number of threads to consume the messages
     * from the broker.
     *
     * @param numberOfThreads The number of threads will be used to consume the message
     */
    public void execute(int numberOfThreads) {
        System.out.println(">>>>>>>>>>>>>> execution");
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
                if(record.value().getEventType().toString().equals("init")) {
                    System.out.println("is init");
                    ConsumerRecord<String, Init> subtypeRecord = (ConsumerRecord<String, Init> ) record;
                    //received new record
                    logger.info( "Received new records: " + " key: " + subtypeRecord.key() + " value: " + subtypeRecord.value().getCountry()+ "\n");
                }
                else {
                    System.out.println("is in app");
                    ConsumerRecord<String, InApp> subtypeRecord = (ConsumerRecord<String, InApp> ) record;
                    //received new records
                    logger.info( "Received new records: " + " key: " + subtypeRecord.key() + " value: " + subtypeRecord.value().getUserId()+ "\n");
                }


                executor.submit(new ConsumerThreadHandler(record));
            }
        }
    }

    private static Properties createConsumerConfig(String groupId) {
        Properties properties = new Properties();
        properties.setProperty(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, KafkaConfig.BOOTSTRAP_SERVER);
        properties.setProperty(ConsumerConfig.GROUP_ID_CONFIG, groupId);
        properties.setProperty("enable.auto.commit", "true");
        properties.setProperty("auto.commit.interval.ms", "1000");
        properties.setProperty("session.timeout.ms", "30000");
        properties.setProperty(ConsumerConfig.AUTO_OFFSET_RESET_CONFIG, "earliest");
        properties.setProperty(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class.getName());
        properties.setProperty(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, KafkaJsonDeserializer.class.getName());
        return  properties;
    }

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
