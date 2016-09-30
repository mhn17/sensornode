package de.hammerton.sensornode.server.dto;

import de.hammerton.sensornode.core.sensormanagement.Sensor;

/**
 * Data transfer object for a sensor
 *
 * @author Marc Hammerton
 */
public class SensorDto {

    private int id, captureInterval;
    private String name, dataType;

    /**
     * Constructor
     * @param sensor The sensor object
     */
    public SensorDto(Sensor sensor) {
        this.id = sensor.getId();
        this.name = sensor.getName();
        this.captureInterval = sensor.getCaptureInterval();
        this.dataType = sensor.getDataType();
    }

    /**
     * Get the sensor's ID
     *
     * @return The sensor's ID
     */
    public int getId() {
        return this.id;
    }

    /**
     * Get the name of the sensor
     *
     * @return The sensor's name
     */
    public String getName() {
        return this.name;
    }

    /**
     * Get the capture interval
     *
     * @return The capture interval
     */
    public int getCaptureInterval() {
        return this.captureInterval;
    }

    /**
     * Get the type of the data stored for the sensor
     *
     * @return The data type
     */
    public String getDataType() {
        return this.dataType;
    }
}
