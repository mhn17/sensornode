package de.hammerton.sensornode.core.sensormanagement.sensor.device;

import au.edu.jcu.v4l4j.exceptions.V4L4JException;
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
     * Create a web cam device and initialise the frame grabber
     *
     * @param devicePath Path to access the device, e.g. "/dev/video0"
     * @param width Image width
     * @param height Image height
     * @param channel Input channel
     * @return A web cam device
     * @throws SensorManagementException
     */
    public IWebCamDevice getWebCamDevice(String devicePath, int width, int height, int channel)
            throws SensorManagementException {
        WebCamDevice device;

        try {
            device = new WebCamDevice(devicePath);
            device.initCurrentFrameGrabber(width, height, channel);
        } catch (V4L4JException e) {
            throw new SensorManagementException("Could not create web cam device");
        }

        return device;
    }

    /**
     * Creates a basic file device
     *
     * @param path The Path to the sensor data file
     * @param adapter The adapter to extract the data from the file
     * @return A basic file device
     * @throws SensorManagementException
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
