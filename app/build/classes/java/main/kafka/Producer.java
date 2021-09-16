package kafka;

import events.Event;
import events.InApp;
import events.Init;
import org.apache.kafka.clients.producer.*;
import org.apache.kafka.common.serialization.StringSerializer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Properties;
import java.util.Random;

public class Producer implements Runnable {

    private final KafkaProducer<String, Event> producer;
    private String topic;

    public Producer() {
        Properties prop = createProducerConfig();
        this.producer = new KafkaProducer<String, Event>(prop, new StringSerializer(),
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
        event.setEventType(event.getEventType().IN_APP_PURCHASE);
        return event;
    }

    public Init createEventInit() {
        Init event = new Init();
        event.setCountry("Portugal");
        event.setEventType(event.getEventType().INIT);
        return event;
    }
    @Override
    public void run() {
        final Logger logger = LoggerFactory.getLogger(Producer.class);
        for(int i = 0; i < 5 ; ++i) {
            Event event;
            Random rand = new Random();
            if(rand.nextInt() % 2 == 0) {
                event = createEventInApp();
            } else {
                event = createEventInit();
            }

            //Create the producer record
            ProducerRecord<String, Event> record = new ProducerRecord<>(this.topic, "0", event);

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
