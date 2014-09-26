package com.github.hammertonmarc.sensornode.core.sensormanagement;

import com.github.hammertonmarc.sensornode.core.sensormanagement.sensorrepository.XmlSensorRepository;

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
    public static SensorRepository getRepository() {
        return new XmlSensorRepository();
    }

}
