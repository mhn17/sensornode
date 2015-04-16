package de.hammerton.sensornode.core.sensordatamanagement;

import java.util.ArrayList;
import java.util.UUID;

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
     * Find all sensor data
     *
     * @return The sensor data
     */
    public ArrayList<SensorData> find();

    /**
     * Find sensor data by a sensor ID
     *
     * @param sensorId The sensor ID for which to get the data
     * @return The sensor data for the sensor ID
     */
    public ArrayList<SensorData> findBySensorId(int sensorId);

    /**
     * Remove a single sensor data record
     *
     * @param id The sensor data ID
     */
    public void remove(UUID id);
}
