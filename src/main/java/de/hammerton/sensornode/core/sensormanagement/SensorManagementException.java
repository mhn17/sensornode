package de.hammerton.sensornode.core.sensormanagement;

/**
 * General exception for sensor management
 *
 * @author Marc Hammerton
 */
public class SensorManagementException extends Exception {

    /**
     * Constructor
     *
     * @param message The exception message
     */
    public SensorManagementException(String message) {
        super(message);
    }
}
