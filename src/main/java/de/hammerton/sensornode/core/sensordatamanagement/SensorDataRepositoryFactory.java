package de.hammerton.sensornode.core.sensordatamanagement;

import de.hammerton.sensornode.core.sensordatamanagement.repository.MongoDbSensorDataRepository;
import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.MongoClient;
import org.apache.commons.configuration.ConfigurationException;
import org.apache.commons.configuration.SubnodeConfiguration;
import org.apache.commons.configuration.XMLConfiguration;

import java.net.UnknownHostException;

/**
 * Factory for getting sensor data repositories
 *
 * @author Marc Hammerton
 */
public class SensorDataRepositoryFactory {

    private static String configurationFile = "config.xml";
    private static SensorDataRepository repository = null;

    /**
     * Return a SensorDataRepository
     *
     * @return SensorDataRepository
     */
    public static SensorDataRepository getRepository() throws SensorDataManagementException {
        if (repository != null) {
            return repository;
        }

        XMLConfiguration config;

        try {
            config = new XMLConfiguration(configurationFile);
        } catch (ConfigurationException e) {
            throw new SensorDataManagementException("Could not create XMLConfiguration");
        }

        SubnodeConfiguration dbConfig = config.configurationAt("databases");

        switch(dbConfig.getString("use")) {
            case "mongoDb":
                repository = getMongoDbDataRepository(dbConfig.configurationAt("mongoDb"));
                break;
            default:
                throw new SensorDataManagementException("Could not create SensorDataRepository");
        }

        return repository;
    }

    /**
     * Create the mongoDb collection and return the MongoDbSensorDataRepository
     *
     * @param config configuration for the mongoDb
     * @return The MongoDbSensorDataRepository
     */
    private static SensorDataRepository getMongoDbDataRepository(SubnodeConfiguration config)
            throws SensorDataManagementException {
        String host = config.getString("host");
        int port = config.getInt("port");
        String dbName = config.getString("db");
        String collectionName = config.getString("collection");

        DBCollection collection;
        try {
            MongoClient mongoClient = new MongoClient(host, port);
            DB db = mongoClient.getDB(dbName);
            collection = db.getCollection(collectionName);
        } catch (UnknownHostException e) {
            throw new SensorDataManagementException("Could not create MongoClient");
        }

        return new MongoDbSensorDataRepository(collection);
    }
}
