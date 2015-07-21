package de.hammerton.sensornode.core.sensormanagement.sensor.device.adapter;

/**
 * @author Marc Hammerton
 */
public class TemperatureStringAdapter implements IStringAdapter {

    /**
     * The content of a temperature file is for example:
     * Temperature: 20.7
     *
     * This adapter extracts the temperature (in the example above "20.7"
     * from the raw data string.
     *
     * @param rawData The raw data string
     * @return The extracted temperature
     */
    @Override
    public String extractData(String rawData) {
        return rawData.substring(13).replace(System.getProperty("line.separator"), "");
    }
}
