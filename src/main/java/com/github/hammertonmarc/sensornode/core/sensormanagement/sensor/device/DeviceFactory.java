package com.github.hammertonmarc.sensornode.core.sensormanagement.sensor.device;

import au.edu.jcu.v4l4j.exceptions.V4L4JException;
import com.github.hammertonmarc.sensornode.core.exception.SensorManagementException;

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
}
