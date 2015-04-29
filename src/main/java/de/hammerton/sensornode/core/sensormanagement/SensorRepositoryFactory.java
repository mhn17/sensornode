package de.hammerton.sensornode.core.sensormanagement;

import de.hammerton.sensornode.core.sensormanagement.sensorrepository.XMLSensorRepository;
import de.hammerton.sensornode.core.sensormanagement.sensor.device.DeviceFactory;
import org.apache.commons.configuration.ConfigurationException;
import org.apache.commons.configuration.XMLConfiguration;

/**
 * Factory for sensor repositories
 *
 * @author Marc Hammerton
 */
public class SensorRepositoryFactory {

    public static String configurationFile = "sensors.xml";

    /**
     * Returns a sensor repository
     *
     * @return SensorRepository
     */
    public static SensorRepository getRepository() throws SensorManagementException {
        try {
            return new XMLSensorRepository(new XMLConfiguration(configurationFile),
                    new DeviceFactory());
        } catch (ConfigurationException e) {
            throw new SensorManagementException("Could not create XMLConfiguration");
        }
    }
}
