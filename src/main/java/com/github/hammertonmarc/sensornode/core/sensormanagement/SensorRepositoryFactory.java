package com.github.hammertonmarc.sensornode.core.sensormanagement;

import com.github.hammertonmarc.sensornode.core.sensormanagement.sensorrepository.XmlSensorRepository;

/**
 * Created by marc on 07.08.14.
 */
public class SensorRepositoryFactory {

    /**
     * @return SensorRepository
     */
    public static SensorRepository getRepository() {
        return new XmlSensorRepository();
    }

}
