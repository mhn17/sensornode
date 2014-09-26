package com.github.hammertonmarc.sensornode.core.exceptions;

/**
 * General exception for sensor data management
 *
 * @author Marc Hammerton
 */
public class SensorDataManagementException extends Exception {

    /**
     * Constructor
     *
     * @param message The exception message
     */
    public SensorDataManagementException(String message) {
        super(message);
    }
}
