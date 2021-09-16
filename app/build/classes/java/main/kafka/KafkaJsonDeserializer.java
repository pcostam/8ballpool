package kafka;

import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.module.SimpleModule;
import com.fasterxml.jackson.databind.type.TypeFactory;
import events.InApp;
import events.Init;
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

    @Override
    public Object deserialize(String topic, byte[] bytes) {
        System.out.println("deserialize");
        ObjectMapper mapper = new ObjectMapper();
        Object obj = null;
        try {
            //decide type based on header
            System.out.println("read tree");
            if(bytes != null ) {
                System.out.println("bytes" +  bytes);
                System.out.print("bytearray " + new ByteArrayInputStream(bytes).toString());
                JsonNode jsonNode = mapper.readTree(new ByteArrayInputStream(bytes));
                System.out.println("next");
                if(!jsonNode.isNull()) {
                    JsonNode fieldNode = jsonNode.get("event-type");

                    Iterator<Map.Entry<String, JsonNode>> fields = fieldNode.fields();
                    while(fields.hasNext()) {
                        Map.Entry<String, JsonNode> field = fields.next();
                        String   fieldName  = field.getKey();
                        JsonNode fieldValue = field.getValue();

                        System.out.println(fieldName + " = " + fieldValue.asText());
                    }

                    Class<?> newType;
                    if(!(fieldNode.isNull()) && (fieldNode != null)) {
                        System.out.println("jsonNode.get " + jsonNode.get("event-type"));
                        System.out.println("is not null");
                        String typeStr = fieldNode.asText();

                        if(typeStr.equals("init")) {
                            newType = Init.class;
                        }
                        else {
                            newType = InApp.class;
                        }
                        System.out.println("event-type deserialize: " + typeStr);

                        obj = mapper.readValue(bytes, newType);

                        System.out.println("obj" + obj.toString());

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
            try {
                obj = mapper.readValue(bytes, Init.class);
            } catch (IOException ex) {
                ex.printStackTrace();
            }
            return obj;
            //logger.error(e.getMessage());
        }
        return obj;
    }

    @Override
    public void close() {

    }
}