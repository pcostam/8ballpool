package mongodb;

import com.mongodb.*;

import java.io.*;
import org.bson.Document;

import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

import com.mongodb.client.model.InsertOneModel;
import com.mongodb.client.model.BulkWriteOptions;
import com.mongodb.bulk.BulkWriteResult;
import com.mongodb.client.MongoCollection;
import org.json.*;
import java.nio.file.Files;
import java.nio.file.Paths;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class BulkWriteInsert {
    private static Logger LOGGER = LoggerFactory.getLogger(BulkWriteInsert.class);
    public static void doBulkInsert( MongoCollection<Document> coll) {
        try {
            //drop previous import
            coll.drop();

            //Bulk Approach:
            int count = 0;
            List<InsertOneModel<Document>> docs = new ArrayList<>();
            //get json schemas from folder schemas
            List<String> listFiles = listFilesForFolder(new File("events"));

            for(String fileName : listFiles) {
                System.out.println("filename to process:" + fileName);
                String json = Files.readString(Paths.get("events/"+ fileName), StandardCharsets.UTF_8);
                JSONArray jsonarray = new JSONArray('[' + json + ']');

                try {
                    for(int i = 0; i < jsonarray.length(); i++) {
                        JSONObject line = jsonarray.getJSONObject(i);
                        System.out.println("json: " + line);
                        docs.add(new InsertOneModel<>(Document.parse(line.toString())));
                        count++;
                    }

                    if (count > 0) {
                        System.out.println("Begin bulk");
                        BulkWriteResult bulkWriteResult =  coll.bulkWrite(docs, new BulkWriteOptions().ordered(false));
                        System.out.println("Inserted" + bulkWriteResult);
                        docs.clear();
                        count = 0;
                    }

                }

                catch (MongoWriteException e) {
                    LOGGER.error(e.toString());
                }

            }


        }

        catch(IOException e) {
            LOGGER.error(e.toString());
        }
}
public static List<String> listFilesForFolder(final File folder) {
        List<String> resultFiles = new ArrayList<String>() ;
        for (final File fileEntry : folder.listFiles()) {
            if (fileEntry.isFile()) {
                System.out.println(fileEntry.getName());
                resultFiles.add(fileEntry.getName());
            }
        }
        return resultFiles;
    }
}

