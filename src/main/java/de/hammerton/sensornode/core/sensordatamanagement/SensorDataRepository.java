package de.hammerton.sensornode.core.sensordatamanagement;

import java.util.ArrayList;

/**
 * Interface for sensor data repositories
 *
 * @author Marc Hammerton
 */
public interface SensorDataRepository {

    /**
     * Add sensor data to the repository to store it
     *
     * @param sensorData The sensor data to be added
     */
    public void add(SensorData sensorData);

    /**
     * Find sensor data by a sensor ID
     *
     * @param sensorId The sensor ID for which to get the data
     * @return The sensor data for the sensor ID
     */
    public ArrayList<SensorData> findBySensorId(int sensorId);
}