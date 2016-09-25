package de.hammerton.sensornode.core.sensordatamanagement;

import de.hammerton.sensornode.core.sensordatamanagement.repository.XodusDbSensorDataRepository;
import jetbrains.exodus.entitystore.*;
import org.apache.commons.configuration.ConfigurationException;
import org.apache.commons.configuration.SubnodeConfiguration;
import org.apache.commons.configuration.XMLConfiguration;

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

        SubnodeConfiguration dbConfig = config.configurationAt("database");

        try {
            final PersistentEntityStore entityStore = PersistentEntityStores.newInstance(dbConfig.getString("file"));
            repository = new XodusDbSensorDataRepository(entityStore);
        } catch (NullPointerException e) {
            throw new SensorDataManagementException("Could not create SensorDataRepository");
        }

        return repository;
    }
}
