package de.hammerton.sensornode.core.sensormanagement.sensor.device;

import au.edu.jcu.v4l4j.FrameGrabber;

/**
 * Interface for WebCamDevices
 *
 * @author Marc Hammerton
 */
public interface IWebCamDevice {

    /**
     * Get the current frame grabber for the device
     *
     * @return The current frame grabber
     */
    public FrameGrabber getCurrentFrameGrabber();

    /**
     * Release the device and the frame grabber
     */
    public void releaseAll();
}
