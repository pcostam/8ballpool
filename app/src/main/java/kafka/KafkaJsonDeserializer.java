package kafka;

import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.module.SimpleModule;
import com.fasterxml.jackson.databind.type.TypeFactory;
import events.InApp;
import events.Init;
import events.Match;
import org.apache.kafka.common.serialization.Deserializer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.annotation.Nullable;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.util.Iterator;
import java.util.Map;

public class KafkaJsonDeserializer implements Deserializer {

    private Logger logger = LoggerFactory.getLogger(this.getClass());



    public KafkaJsonDeserializer() {
    }



    @Override
    public void configure(Map configs, boolean isKey) {

    }

    /**
     * Decides object type to deserialize into based on json header.
     * @param topic
     * @param bytes
     * @return Object
     */
    @Override
    public Object deserialize(String topic, byte[] bytes) {
        ObjectMapper mapper = new ObjectMapper();
        Object obj = null;
        try {
            //decide type based on json header
            if(bytes != null ) {
                JsonNode jsonNode = mapper.readTree(new ByteArrayInputStream(bytes));
                if(!jsonNode.isNull()) {
                    JsonNode fieldNode = jsonNode.get("event-type");

                    Class<?> newType;
                    if(!(fieldNode.isNull()) && (fieldNode != null)) {
                        String typeStr = fieldNode.asText();
                        newType=Init.class;
                        if(typeStr.equals("init")) {
                            newType = Init.class;
                        }
                        else if(typeStr.equals("in-app-purchase")) {
                            newType = InApp.class;
                        }
                        else if(typeStr.equals("match")) {
                            newType = Match.class;
                        }

                        obj = mapper.readValue(bytes, newType);

                }


                    if(obj == null) {
                        obj = mapper.readValue(bytes, Init.class);
                    }
                }



            }
            else {
                System.out.println("null received at deserializer");
            }


        } catch (Exception e) {
            logger.error(e.getMessage());
        }
        return obj;
    }

    @Override
    public void close() {

    }
}