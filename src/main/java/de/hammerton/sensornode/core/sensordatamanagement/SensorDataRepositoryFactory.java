package de.hammerton.sensornode.core.sensordatamanagement;

import de.hammerton.sensornode.core.sensordatamanagement.repository.MongoDbSensorDataRepository;
import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.MongoClient;
import de.hammerton.sensornode.core.sensordatamanagement.repository.OrientDbSensorDataRepository;
import org.apache.commons.configuration.ConfigurationException;
import org.apache.commons.configuration.HierarchicalConfiguration;
import org.apache.commons.configuration.SubnodeConfiguration;
import org.apache.commons.configuration.XMLConfiguration;

import java.net.UnknownHostException;
import java.util.List;

/**
 * Factory for getting sensor data repositories
 *
 * @author Marc Hammerton
 */
public class SensorDataRepositoryFactory {

    public static String configurationFile = "config.xml";

    /**
     * Return a SensorDataRepository
     *
     * @return SensorDataRepository
     */
    public static SensorDataRepository getRepository() throws SensorDataManagementException {
        XMLConfiguration config = null;

        try {
            config = new XMLConfiguration(configurationFile);
        } catch (ConfigurationException e) {
            throw new SensorDataManagementException("Could not create XMLConfiguration");
        }

        SubnodeConfiguration dbConfig = config.configurationAt("databases");

        switch(dbConfig.getString("use")) {
            case "mongoDb":
                return getMongoDbDataRepository(dbConfig.configurationAt("mongoDb"));
            case "orientDb":
                return getOrientDbDataRepository(dbConfig.configurationAt("orientDb"));
            default:
                throw new SensorDataManagementException("Could not create SensorDataRepository");
        }

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

    /**
     * Create the orientDb and return the OrientDbSensorDataRepository
     *
     * @param config configuration for the orientDb
     * @return The OrientDbSensorDataRepository
     */
    private static SensorDataRepository getOrientDbDataRepository(SubnodeConfiguration config)
            throws SensorDataManagementException {


        return new OrientDbSensorDataRepository();
    }
}
