package de.hammerton.sensornode.core.sensordatamanagement;

import java.util.ArrayList;
import java.util.UUID;

/**
 * Interface for sensor data repositories
 *
 * @author Marc Hammerton
 */
public interface SensorDataRepository {

    int DEFAULT_OFFSET = 0;
    int DEFAULT_LIMIT = 20;

    /**
     * Add sensor data to the repository to store it
     *
     * @param sensorData The sensor data to be added
     */
    public void add(SensorData sensorData);

    /**
     * Find all sensor data limiting to the first 20 records
     *
     * @return The sensor data
     */
    public ArrayList<SensorData> find();

    /**
     * Find all sensor data with offset and limit
     *
     * @param offset Where to start listing the records
     * @param limit How many results should be shown
     * @return The sensor data
     */
    public ArrayList<SensorData> find(int offset, int limit);

    /**
     * Find sensor data by a sensor ID
     *
     * @param sensorId The sensor ID for which to get the data
     * @param offset Where to start listing the records
     * @param limit How many results should be shown
     * @return The sensor data for the sensor ID
     */
    public ArrayList<SensorData> findBySensorId(int sensorId, int offset, int limit);

    /**
     * Remove a single sensor data record
     *
     * @param id The sensor data ID
     * @throws SensorDataManagementException When the sensor data could not be removed an exception is thrown
     */
    public void remove(UUID id) throws SensorDataManagementException;
}
