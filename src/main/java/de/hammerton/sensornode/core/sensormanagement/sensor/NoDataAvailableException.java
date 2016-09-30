package de.hammerton.sensornode.core.sensormanagement.sensor;

/**
 * Exception when no data was available from the device
 *
 * @author Marc Hammerton
 */
public class NoDataAvailableException extends Exception {

    /**
     * Constructor
     *
     * @param message The exception message
     */
    public NoDataAvailableException(String message) {
        super(message);
    }
}

