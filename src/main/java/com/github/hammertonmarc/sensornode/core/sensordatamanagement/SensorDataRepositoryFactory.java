package com.github.hammertonmarc.sensornode.core.sensordatamanagement;

import com.github.hammertonmarc.sensornode.core.exception.SensorDataManagementException;
import com.github.hammertonmarc.sensornode.core.sensordatamanagement.mongodb.MongoDbSensorDataRepository;
import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.MongoClient;

import java.net.UnknownHostException;

/**
 * Factory for getting sensor data repositories
 *
 * @author Marc Hammerton
 */
public class SensorDataRepositoryFactory {

    /**
     * MongoDb configuration values
     */
    private final static String HOST = "localhost";
    private final static int PORT = 27017;
    private final static String DB = "sensorNode";
    private final static String COLLECTION = "sensorData";

    /**
     * Return a SensorDataRepository
     *
     * @return SensorDataRepository
     */
    public static SensorDataRepository getRepository() throws SensorDataManagementException {
        DBCollection collection;
        try {
            collection = getMongoDbCollection();
        } catch (UnknownHostException e) {
            throw new SensorDataManagementException("Could not create MongoClient");
        }

        return new MongoDbSensorDataRepository(collection);
    }

    /**
     * Create and return the mongoDb collection
     * @return The mongoDb collection
     * @throws UnknownHostException
     */
    private static DBCollection getMongoDbCollection() throws UnknownHostException {
        MongoClient mongoClient = new MongoClient(HOST , PORT);
        DB db = mongoClient.getDB(DB);
        return db.getCollection(COLLECTION);
    }
}
