package com.github.hammertonmarc.sensornode.core.sensordatamanagement;

import java.util.Calendar;

/**
 * A data transfer object for sensor data
 *
 * @author Marc Hammerton
 */
public class SensorData {
    protected int sensorId;
    protected String sensorName;
    protected long timestamp;
    protected byte[] data;

    /**
     * Constructor
     *  - sets the timestamp to the current time
     *
     * @param sensorId The sensor ID
     * @param sensorName The sensor name
     * @param data The sensor data
     */
    public SensorData (int sensorId, String sensorName, byte[] data) {
        this.sensorId = sensorId;
        this.sensorName = sensorName;
        this.data = data;

        Calendar calendar = Calendar.getInstance();
        this.timestamp = calendar.getTime().getTime();
    }

    /**
     * Constructor
     *  - sets the timestamp to a specific time
     *
     * @param sensorId The sensor ID
     * @param sensorName The sensor name
     * @param timestamp The time when the data was read
     * @param data The sensor data
     */
    public SensorData (int sensorId, String sensorName, long timestamp, byte[] data) {
        this.sensorId = sensorId;
        this.sensorName = sensorName;
        this.timestamp = timestamp;
        this.data = data;
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
     * Returns the sensor name
     *
     * @return The sensor name
     */
    public String getSensorName() {
        return sensorName;
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
