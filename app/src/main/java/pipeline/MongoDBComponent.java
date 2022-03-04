package pipeline;

import kafka.KafkaConfig;
import kafka.KafkaUtils;
import mongodb.BulkWriteInsert;
import mongodb.MongoDBSetup;
import org.apache.kafka.clients.admin.AdminClientConfig;

import java.io.IOException;
import java.util.Properties;
import java.util.concurrent.ExecutionException;

public class MongoDBComponent {

    public static void doMongoDBComponent(String topicName) throws IOException, ExecutionException, InterruptedException {
        MongoDBSetup setupMongo = new MongoDBSetup();
        BulkWriteInsert.doBulkInsert(setupMongo.getCollection());

    }
}
