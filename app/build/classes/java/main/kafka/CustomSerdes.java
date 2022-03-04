package kafka;

import events.Event;
import org.apache.kafka.common.serialization.Serde;
import org.apache.kafka.common.serialization.Serdes;

public final class CustomSerdes {
    static public final class InAppSerde
            extends Serdes.WrapperSerde<Event> {
        public InAppSerde() {
            super(new KafkaJsonSerializer(),
                    new KafkaJsonDeserializer());
        }
    }


    public static Serde<Event> InApp() {
        return new CustomSerdes.InAppSerde();
    }


}

