package kafka;

import events.Event;
import events.InApp;
import events.Init;
import org.apache.kafka.common.serialization.Serdes;
import org.apache.kafka.streams.*;
import org.apache.kafka.streams.kstream.Consumed;
import org.apache.kafka.streams.kstream.KStream;
import org.apache.kafka.streams.kstream.Produced;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Properties;

public class Streams {
    private String inputTopic;
    private String outputTopic;
    private String appIdConfig;
    private Properties properties;
    private static final Logger logger = LoggerFactory.getLogger(Streams.class);

    public Streams(String inputTopic, String outputTopic, String appIdConfig) {
        this.appIdConfig = appIdConfig;
        this.properties = createStreamsConfig(appIdConfig);
        this.outputTopic = outputTopic;
        this.inputTopic = inputTopic;

    }
    /**
     * Define configuration properties
     */
    public Properties createStreamsConfig(String appIdConfig) {
        Properties properties = new Properties();
        properties.put(StreamsConfig.APPLICATION_ID_CONFIG, appIdConfig);
        properties.put(StreamsConfig.BOOTSTRAP_SERVERS_CONFIG, KafkaConfig.BOOTSTRAP_SERVER);
        return properties;
    }
    public KStream<String, Event> openInputStream(StreamsBuilder streamsBuilder, String inputTopic) {
        return streamsBuilder.stream(inputTopic, Consumed.with(Serdes.String(), CustomSerdes.InApp()));
    }

    public Event transformations(Event event) {
            event = transformFieldIdToName(event);
            event = convertUpperCase(event);
            return event;
    }
    public Event transformFieldIdToName(Event event) {
        if(event instanceof Init) {
            Init init = (Init) event;
            init.setUserId("new name");
            return init;
        }
        else if(event instanceof InApp) {
            InApp inApp = (InApp) event;
            inApp.setUserId("changeuserId");
            inApp.setProductId("productId");
            return inApp;
        }

        else {
            return event;
        }
    }
    public Event convertUpperCase(Event event) {
        if(event instanceof Init) {
            Init init = (Init) event;
            init.setCountry(init.getCountry().toUpperCase());
            init.setPlatform(init.getPlatform().toUpperCase());
            init.setUserId(init.getUserId().toUpperCase());
            return init;
        }
        else if(event instanceof InApp) {
            InApp inApp = (InApp) event;
            inApp.setUserId(inApp.getUserId().toUpperCase());
            inApp.setProductId(inApp.getProductId().toUpperCase());
            return inApp;
        }

        else {
            return event;
        }

    }
    public void doTransform(KStream<String, Event> inputStream, String outputTopic) {
            //map values to upper case
            System.out.println(">>>do transform");
            System.out.println("input stream" + inputStream.toString());
            KStream<String, Event> upperStream = inputStream.map((key, value) -> new KeyValue<>(key, transformations(value)));
            upperStream.through(outputTopic, Produced.with(Serdes.String(), CustomSerdes.InApp()));
    }

    public void doStream(String inputTopic) {
        System.out.println(">>>> do stream");
        StreamsBuilder streamsBuilder = new StreamsBuilder();
        KStream<String, Event> inputStream = openInputStream(streamsBuilder, this.inputTopic);
        doTransform(inputStream, this.outputTopic);
        Topology topology = streamsBuilder.build();

        KafkaStreams streams = new KafkaStreams(topology, this.properties);
        System.out.println(">>> start streams");
        streams.start();

        terminate(streams);


    }

    /**
     * Make sure everything terminates cleanly when process is killed
     */
    public void terminate(KafkaStreams streams) {
        Runtime.getRuntime().addShutdownHook(new Thread(() -> {
            logger.info("shutting down");
            streams.close();
        }));
    }
}
