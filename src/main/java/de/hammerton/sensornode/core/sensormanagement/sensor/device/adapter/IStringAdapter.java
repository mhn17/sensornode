package de.hammerton.sensornode.core.sensormanagement.sensor.device.adapter;

/**
 * @author Marc Hammerton
 */
public interface IStringAdapter {

    /**
     * Extracts the interesting data from a raw data string
     *
     * @param rawData The raw data string
     * @return The extracted data
     */
    String extractData(String rawData);

}
