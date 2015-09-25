package de.hammerton.sensornode.core.sensordatamanagement.repository;

import de.hammerton.sensornode.core.sensordatamanagement.SensorData;
import de.hammerton.sensornode.core.sensordatamanagement.SensorDataRepository;

import java.util.ArrayList;
import java.util.UUID;

/**
 * Sensor data repository using orientDb
 *
 * @author Marc Hammerton
 */
public class OrientDbSensorDataRepository implements SensorDataRepository {


    @Override
    public void add(SensorData sensorData) {

    }

    @Override
    public ArrayList<SensorData> find() {
        return null;
    }

    @Override
    public ArrayList<SensorData> findBySensorId(int sensorId) {
        return null;
    }

    @Override
    public void remove(UUID id) {

    }
}
