package com.github.hammertonmarc.sensornode.core.sensordatamanagement;

import com.github.hammertonmarc.sensornode.core.exceptions.SensorDataManagementException;
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
        MongoClient mongoClient = new MongoClient( "localhost" , 27017 );
        DB db = mongoClient.getDB("sensorNode");
        return db.getCollection("sensorData");
    }
}
