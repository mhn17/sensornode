package com.github.hammertonmarc.sensornode.core.sensormanagement;

import com.github.hammertonmarc.sensornode.core.exceptions.SensorManagementException;
import com.github.hammertonmarc.sensornode.core.sensormanagement.sensorrepository.XMLSensorRepository;
import org.apache.commons.configuration.ConfigurationException;
import org.apache.commons.configuration.XMLConfiguration;

/**
 * Factory for sensor repositories
 *
 * @author Marc Hammerton
 */
public class SensorRepositoryFactory {

    /**
     * Returns a sensor repository
     *
     * @return SensorRepository
     */
    public static SensorRepository getRepository() throws SensorManagementException {
        try {
            return new XMLSensorRepository(new XMLConfiguration("sensors.xml"));
        } catch (ConfigurationException e) {
            throw new SensorManagementException("Could not create XMLConfiguration");
        }
    }

}
