package de.hammerton.sensornode.core.sensordatamanagement;

import com.couchbase.lite.*;
import com.orientechnologies.orient.core.db.document.ODatabaseDocumentTx;
import de.hammerton.sensornode.core.sensordatamanagement.repository.CouchbaseLiteDbSensorDataRepository;
import de.hammerton.sensornode.core.sensordatamanagement.repository.MongoDbSensorDataRepository;
import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.MongoClient;
import de.hammerton.sensornode.core.sensordatamanagement.repository.OrientDbSensorDataRepository;
import org.apache.commons.configuration.ConfigurationException;
import org.apache.commons.configuration.SubnodeConfiguration;
import org.apache.commons.configuration.XMLConfiguration;

import java.io.IOException;
import java.net.UnknownHostException;
import java.util.Map;

/**
 * Factory for getting sensor data repositories
 *
 * @author Marc Hammerton
 */
public class SensorDataRepositoryFactory {

    public static String configurationFile = "config.xml";
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
            case "orientDb":
                repository = getOrientDbDataRepository(dbConfig.configurationAt("orientDb"));
                break;
            case "couchbase":
                repository = getCouchbaseLiteDbDataRepository();
                break;
            default:
                throw new SensorDataManagementException("Could not create SensorDataRepository");
        }

        return repository;
    }

    /**
     * Create the couchbase lite database and return the CouchbaseLiteDbDataRepository
     *
     * @return The CouchbaseLiteDbDataRepository
     * @throws SensorDataManagementException
     */
    private static SensorDataRepository getCouchbaseLiteDbDataRepository()
            throws SensorDataManagementException {
        Manager manager;
        Database db;
        try {
            manager = new Manager(new JavaContext("data"), Manager.DEFAULT_OPTIONS);
            db = manager.getDatabase("sensor_node");
        } catch (IOException e) {
            throw new SensorDataManagementException("Could not create couchbase manager");
        } catch (CouchbaseLiteException e) {
            throw new SensorDataManagementException("Could not create couchbase database");
        }

        View sensorIdsView = db.getView("sensorIdsView");
        sensorIdsView.setMap(new Mapper() {
            @Override
            public void map(Map<String, Object> document, Emitter emitter) {
                if (document.containsKey("sensorId")) {
                    emitter.emit(document.get("sensorId"), document);
                }
            }
        }, "1");

        return new CouchbaseLiteDbSensorDataRepository(db);
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
        String url = config.getString("url");
        String userName = config.getString("username");
        String password = config.getString("password");

        ODatabaseDocumentTx db = new ODatabaseDocumentTx(url);

        return new OrientDbSensorDataRepository(db, userName, password);
    }
}
