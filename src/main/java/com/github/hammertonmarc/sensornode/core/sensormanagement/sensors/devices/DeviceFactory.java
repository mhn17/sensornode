package com.github.hammertonmarc.sensornode.core.sensormanagement.sensors.devices;

import au.edu.jcu.v4l4j.V4L4JConstants;
import au.edu.jcu.v4l4j.exceptions.V4L4JException;
import com.github.hammertonmarc.sensornode.core.exceptions.SensorManagementException;

/**
 * Factory for creating devices
 *
 * @author Marc Hammerton
 */
public class DeviceFactory {

    public IWebCamDevice getWebCamDevice(String devicePath, int width, int height, int channel)
            throws SensorManagementException {
        WebCamDevice device;

        try {
            device = new WebCamDevice(devicePath);
            device.initCurrentFrameGrabber(width, height, channel);
//            device.setCurrentFrameGrabber(device.getJPEGFrameGrabber(width, height, channel,
//                    V4L4JConstants.STANDARD_WEBCAM, 80));
        } catch (V4L4JException e) {
            throw new SensorManagementException("Could not create web cam device");
        }

        return device;
    }

}
