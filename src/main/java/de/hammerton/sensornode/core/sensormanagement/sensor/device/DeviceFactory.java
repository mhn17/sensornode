package de.hammerton.sensornode.core.sensormanagement.sensor.device;

import de.hammerton.sensornode.core.sensormanagement.SensorManagementException;
import de.hammerton.sensornode.core.sensormanagement.sensor.device.adapter.IStringAdapter;

import java.io.IOException;

/**
 * Factory for creating devices
 *
 * @author Marc Hammerton
 */
public class DeviceFactory {

    /**
     * Creates a basic file device
     *
     * @param path The Path to the sensor data file
     * @param adapter The adapter to extract the data from the file
     * @return A basic file device
     * @throws SensorManagementException Throws an SensorManagementException when the device could not be created
     */
    public IBasicDevice getFileDevice(String path, IStringAdapter adapter)
            throws SensorManagementException {
        IBasicDevice device;

        try {
            device = new FileDevice(path, adapter);
        } catch (IOException e) {
            throw new SensorManagementException("Could not create basic file device");
        }

        return device;
    }

    /**
     * Creates a basic dummy device
     *
     * @return A basic dummy device
     */
    public IBasicDevice getDummyDevice() {
        return new DummyDevice();
    }
}
