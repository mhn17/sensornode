package com.github.hammertonmarc.sensornode.core.sensormanagement.sensors;

import au.edu.jcu.v4l4j.*;
import au.edu.jcu.v4l4j.exceptions.StateException;
import au.edu.jcu.v4l4j.exceptions.V4L4JException;
import com.github.hammertonmarc.sensornode.core.exceptions.SensorManagementException;
import com.github.hammertonmarc.sensornode.core.sensormanagement.Sensor;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.IOException;

/**
 * A web cam sensor which takes periodically photos according to the capture interval
 *
 * @author Marc Hammerton
 */
public class WebCam extends Sensor implements CaptureCallback {
    private int width;
    private int height;
    private int channel;
    private String device = "/dev/video0";

    private VideoDevice videoDevice = null;
    private FrameGrabber frameGrabber = null;

    /**
     * Constructor
     *  - initialise the frame grabber
     *
     * @param id The sensor ID
     * @param name The sensor name
     * @param width The width of the photo
     * @param height The height of the photo
     * @param channel The video channel
     * @param device The device (the attached web cam)
     *
     * @throws SensorManagementException
     */
    public WebCam(int id, String name, int width, int height, int channel, String device)
            throws SensorManagementException {
        super(id, name);
        this.width = width;
        this.height = height;
        this.channel = channel;
        this.device = device;

        // Initialise video device and frame grabber
        try {
            initFrameGrabber();
        } catch (V4L4JException e1) {
            // cleanup and exit
            cleanupCapture();

            throw new SensorManagementException("Sensor not connected");
        }
    }

    /**
     * @see com.github.hammertonmarc.sensornode.core.sensormanagement.Sensor#startCapturing()
     */
    public void startCapturing() {
        // start capture
        try {
            frameGrabber.startCapture();
        } catch (V4L4JException e){
            System.err.println("Error starting the capture");
            e.printStackTrace();
        }
    }

    /**
     * @see com.github.hammertonmarc.sensornode.core.sensormanagement.Sensor#close()
     */
    public void close() {
        this.cleanupCapture();
        System.out.println("closing web cam");
    }

    /**
     * @see au.edu.jcu.v4l4j.CaptureCallback#nextFrame(au.edu.jcu.v4l4j.VideoFrame)
     */
    @Override
    public void nextFrame(VideoFrame videoFrame) {
        BufferedImage bi = videoFrame.getBufferedImage();
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        try {
            ImageIO.write(bi, "jpg", baos);
            baos.flush();
            byte[] imageInByte = baos.toByteArray();
            baos.close();
            this.setData(imageInByte);
        } catch (IOException e) {
            e.printStackTrace();
        }

        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        videoFrame.recycle();
    }

    /**
     * Initialises the FrameGrabber object
     *
     * @throws V4L4JException if any parameter is invalid
     */
    private void initFrameGrabber() throws V4L4JException {
        videoDevice = new VideoDevice(device);
        frameGrabber = videoDevice.getJPEGFrameGrabber(this.width, this.height, this.channel,
                V4L4JConstants.STANDARD_WEBCAM, 80);
        frameGrabber.setCaptureCallback(this);

        width = frameGrabber.getWidth();
        height = frameGrabber.getHeight();
        System.out.println("Starting capturing at "+width+"x"+height);
    }

    /**
     * Stops the capture and releases the frame grabber and video device
     */
    private void cleanupCapture() {
        if (frameGrabber != null) {
            try {
                frameGrabber.stopCapture();
            } catch (StateException ex) {
                // the frame grabber may be already stopped, so we just ignore
                // any exception and simply continue.
            }
        }

        if (videoDevice != null) {
            // release the frame grabber and video device
            videoDevice.releaseFrameGrabber();
            videoDevice.release();
        }
    }

    /**
     * @see au.edu.jcu.v4l4j.CaptureCallback#exceptionReceived(au.edu.jcu.v4l4j.exceptions.V4L4JException)
     */
    @Override
    public void exceptionReceived(V4L4JException e) {
        // This method is called by v4l4j if an exception
        // occurs while waiting for a new frame to be ready.
        // The exception is available through e.getCause()
        e.printStackTrace();
    }
}
