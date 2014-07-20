package com.github.hammertonmarc.sensornode.core.sensordatamanagement;

import com.github.hammertonmarc.sensornode.core.sensordatamanagement.mongodb.MongoDbSensorDataRepository;

/**
 * Created by marc on 20.07.14.
 */
public class SensorDataRepositoryFactory {

    /**
     * @return SensorDataRepository
     */
    public static SensorDataRepository getRepository() {
        return new MongoDbSensorDataRepository();
    }
}
