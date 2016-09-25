package de.hammerton.sensornode.core.sensordatamanagement;

import java.util.UUID;

/**
 * A data transfer object for sensor data
 *
 * @author Marc Hammerton
 */
public class SensorData {

    protected UUID id;
    protected int sensorId;
    protected long timestamp;
    protected byte[] data;

    /**
     * Constructor
     *  - creates a new id
     *  - set the timestamp to the current time
     * @param sensorId The sensor ID
     * @param data The sensor data
     */
    public SensorData(int sensorId, byte[] data) {
        this(UUID.randomUUID(), sensorId, System.currentTimeMillis() / 1000L, data);
    }


    /**
     * Constructor
     *
     * @param id The sensor data ID
     * @param sensorId The sensor ID
     * @param timestamp The time when the data was read
     * @param data The sensor data
     */
    public SensorData(UUID id, int sensorId, long timestamp, byte[] data) {
        this.id = id;
        this.sensorId = sensorId;
        this.timestamp = timestamp;
        this.data = data;
    }

    /**
     * Returns the sensor data ID
     *
     * @return The sensor data ID
     */
    public UUID getId() {
        return id;
    }

    /**
     * Returns the sensor ID
     *
     * @return The sensor ID
     */
    public int getSensorId() {
        return sensorId;
    }

    /**
     * Returns the timestamp when the data was read
     *
     * @return The timestamp
     */
    public long getTimestamp() {
        return timestamp;
    }

    /**
     * Returns the sensor data
     *
     * @return The sensor data
     */
    public byte[] getData() {
        return data;
    }
}
