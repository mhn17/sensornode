package com.github.hammertonmarc.sensornode.core.sensormanagement.sensors.devices;

import au.edu.jcu.v4l4j.FrameGrabber;
import au.edu.jcu.v4l4j.V4L4JConstants;
import au.edu.jcu.v4l4j.VideoDevice;
import au.edu.jcu.v4l4j.exceptions.StateException;
import au.edu.jcu.v4l4j.exceptions.V4L4JException;

/**
 * Wrapper class for V4L4J video devices
 *
 * @author Marc Hammerton
 */
public class WebCamDevice extends VideoDevice implements IWebCamDevice {

    private FrameGrabber currentFrameGrabber = null;

    /**
     * This constructor builds a <code>VideoDevice</code> using the full path to
     * its device file. When finished, resources must be released by calling
     * {@link #release()}.
     *
     * @param dev the path to the device file
     * @throws au.edu.jcu.v4l4j.exceptions.V4L4JException if the device file is not accessible
     */
    public WebCamDevice(String dev) throws V4L4JException {
        super(dev);
    }

    public FrameGrabber getCurrentFrameGrabber() {
        return currentFrameGrabber;
    }

    public void initCurrentFrameGrabber(int width, int height, int channel) {
        try {
            this.currentFrameGrabber = this.getJPEGFrameGrabber(width, height, channel,
                        V4L4JConstants.STANDARD_WEBCAM, 80);
        } catch (V4L4JException e) {
            e.printStackTrace();
        }
    }

    public void releaseAll() {
        if (this.currentFrameGrabber != null) {
            try {
                this.currentFrameGrabber.stopCapture();

            } catch (StateException ex) {
                // the frame grabber may be already stopped, so we just ignore
                // any exception and simply continue.
            }
        }

        this.releaseFrameGrabber();
        this.release();
    }
}
