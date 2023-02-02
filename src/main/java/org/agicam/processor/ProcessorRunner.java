package org.agicam.processor;

import com.mongodb.ConnectionString;
import com.mongodb.MongoClientSettings;
import com.mongodb.ServerApi;
import com.mongodb.ServerApiVersion;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import org.bson.Document;

/**
 * Created By: Jordan M
 * Description:
 */
public class ProcessorRunner {
    public static void main(String[] args) {
        ConnectionString connectionString = new ConnectionString("mongodb+srv://AmazonDB:<password>@amazondb.gwy8ghv.mongodb.net/test?retryWrites=true&w=majority");
        MongoClientSettings settings = MongoClientSettings.builder()
                .applyConnectionString(connectionString)
                .serverApi(ServerApi.builder()
                        .version(ServerApiVersion.V1)
                        .build())
                .build();
        MongoClient mongoClient = MongoClients.create(settings);
        MongoDatabase database = mongoClient.getDatabase("test");
        MongoCollection<Document> collection = database.getCollection("images");

        collection.insertOne(new Document("_id","JordTest"));
    }
}
