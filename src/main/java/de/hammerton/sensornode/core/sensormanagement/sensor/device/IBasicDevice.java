package de.hammerton.sensornode.core.sensormanagement.sensor.device;

/**
 * @author Marc Hammerton
 */
public interface IBasicDevice extends IDevice {

    /**
     * Read the current data for the device
     *
     * @return The current data
     */
    byte[] readData();
}
