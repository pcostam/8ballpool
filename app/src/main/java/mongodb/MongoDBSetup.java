package mongodb;

import com.mongodb.BasicDBObject;
import com.mongodb.DB;
import com.mongodb.MongoClient;
import com.mongodb.MongoClientURI;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.model.CreateCollectionOptions;
import com.mongodb.client.model.ValidationOptions;
import org.bson.Document;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;

public class MongoDBSetup {
    private MongoDatabase database;
    private MongoCollection<Document> collection;

    public MongoDBSetup() throws IOException {
        MongoClient mongoClient = new MongoClient( new MongoClientURI("mongodb://localhost:27017"));
        this.database = mongoClient.getDatabase ("8ballpoolDB");
        //read json schema
        String jsonSchema = Files.readString(Paths.get("schemas/schema_validation.json"), StandardCharsets.UTF_8);
        System.out.println("json schema:" + jsonSchema);
        //convert to bson
        Document doc = Document.parse(jsonSchema);
        //create validation options for each collection to match json schema
        CreateCollectionOptions options = new CreateCollectionOptions();
        ValidationOptions validationOptions = new ValidationOptions();
        validationOptions.validator(doc);
        options.validationOptions(validationOptions);
        System.out.println("options" + options.toString());

        //if collection does not exist, create it explicitly
        if(!mongoClient.getDatabase("8ballpoolDB").listCollectionNames()
                .into(new ArrayList<String>()).contains("8ballpoolcol")) {
            this.database.createCollection("8ballpoolcol");
        }

        this.collection = this.database.getCollection("8ballpoolcol");
    }

    public MongoDatabase getDatabase() {
        return database;
    }

    public void setDatabase(MongoDatabase database) {
        this.database = database;
    }

    public MongoCollection<Document> getCollection() {
        return collection;
    }

    public void setCollection(MongoCollection<Document> collection) {
        this.collection = collection;
    }
}
