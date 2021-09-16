package kafka;

import org.apache.kafka.common.serialization.Serdes;
import org.apache.kafka.streams.KafkaStreams;
import org.apache.kafka.streams.StreamsBuilder;
import org.apache.kafka.streams.StreamsConfig;
import org.apache.kafka.streams.Topology;
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
        //properties.put(StreamsConfig.AUTO_OFFSET_RESET_CONFIG, "earliest");
        properties.put(StreamsConfig.DEFAULT_KEY_SERDE_CLASS_CONFIG, Serdes.Integer().getClass());
        properties.put(StreamsConfig.DEFAULT_VALUE_SERDE_CLASS_CONFIG, Serdes.String().getClass());
        return properties;
    }
    public KStream<String, String> openInputStream(StreamsBuilder streamsBuilder, String inputTopic) {
        KStream<String, String> kstream = streamsBuilder.stream(inputTopic);
        return kstream;
    }

    public void doTransform(KStream<String, String> inputStream, String outputTopic) {
            //map values to upper case
            KStream<String, String> upperStream = inputStream.mapValues(v -> v.toUpperCase());
            upperStream.through(outputTopic, Produced.with(Serdes.String(), Serdes.String()));
    }

    public void doStream(String inputTopic) {
        StreamsBuilder streamsBuilder = new StreamsBuilder();
        KStream<String, String> inputStream = openInputStream(streamsBuilder, this.inputTopic);
        doTransform(inputStream, this.outputTopic);
        Topology topology = streamsBuilder.build();

        KafkaStreams streams = new KafkaStreams(topology, properties);
        streams.start();


        // Make sure everything terminates cleanly when process is killed
        Runtime.getRuntime().addShutdownHook(new Thread(() -> {
            logger.info("shutting down");
            streams.close();
        }));
    }
}
