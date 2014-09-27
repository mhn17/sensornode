package com.github.hammertonmarc.sensornode.core.sensormanagement.sensors.devices;

import au.edu.jcu.v4l4j.FrameGrabber;

/**
 * Interface for WebCamDevices
 */
public interface IWebCamDevice {

    public FrameGrabber getCurrentFrameGrabber();

//    public void setCurrentFrameGrabber(FrameGrabber currentFrameGrabber);

    public void releaseAll();
}
