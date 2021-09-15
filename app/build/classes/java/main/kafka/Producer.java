package kafka;

import events.InApp;
import org.apache.kafka.clients.producer.*;
import org.apache.kafka.common.serialization.StringSerializer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Properties;

public class Producer implements Runnable {

    private final KafkaProducer<String, InApp> producer;
    private String topic;

    public Producer() {
        Properties prop = createProducerConfig();
        this.producer = new KafkaProducer<String, InApp>(prop, new StringSerializer(),
                new KafkaJsonSerializer());
        this.topic = "8sample-topic";


    }

    /**
     * Create properties object for producer
     * @return Properties
     */
    private static Properties createProducerConfig() {
        Properties properties = new Properties();
        properties.setProperty(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, KafkaConfig.BOOTSTRAP_SERVER);
        properties.setProperty(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class.getName());
        properties.setProperty(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, StringSerializer.class.getName());
        properties.setProperty(ProducerConfig.ACKS_CONFIG, "all");
        return properties;
    }

    public InApp createEventInApp() {
        InApp event = new InApp();
        event.setUserId("acmana");
        return event;
    }
    @Override
    public void run() {
        final Logger logger = LoggerFactory.getLogger(Producer.class);
        for(int i = 0; i < 5 ; ++i) {
            InApp event = createEventInApp();
            //Create the producer record
            ProducerRecord<String, InApp> record = new ProducerRecord<>(this.topic, "0", event);

            //Send data - assynchronous
            producer.send(record, new Callback() {
                @Override
                public void onCompletion(RecordMetadata recordMetadata, Exception e) {
                    if( e == null) {
                        logger.info("received record metadata \n " + recordMetadata.topic());

                    } else {
                        logger.error("Error: Occured", e);

                    }
                }
            });
        }
    }
}
