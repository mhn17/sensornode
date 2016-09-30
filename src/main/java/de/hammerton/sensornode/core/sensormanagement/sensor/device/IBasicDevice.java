package de.hammerton.sensornode.core.sensormanagement.sensor.device;

import de.hammerton.sensornode.core.sensormanagement.sensor.NoDataAvailableException;

/**
 * @author Marc Hammerton
 */
public interface IBasicDevice extends IDevice {

    /**
     * Read the current data for the device
     *
     * @return The current data
     * @throws NoDataAvailableException Throws an exception when no new data is available
     */
    byte[] readData() throws NoDataAvailableException;
}
