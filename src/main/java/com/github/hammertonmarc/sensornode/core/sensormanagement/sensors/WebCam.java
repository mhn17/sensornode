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
 * Created by marc on 17.05.14.
 */
public class WebCam extends Sensor implements CaptureCallback {
    private int width;
    private int height;
    private int channel;
    private String device = "/dev/video0";

    private VideoDevice videoDevice;
    private FrameGrabber frameGrabber;

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

    public void startCapturing() {
        // start capture
        try {
            frameGrabber.startCapture();
        } catch (V4L4JException e){
            System.err.println("Error starting the capture");
            e.printStackTrace();
        }
    }

    public void close() {
        this.cleanupCapture();
        System.out.println("closing web cam");
    }

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
     * @throws V4L4JException if any parameter is invalid
     */
    private void initFrameGrabber() throws V4L4JException {
        videoDevice = new VideoDevice(device);
        int std = V4L4JConstants.STANDARD_WEBCAM;
        frameGrabber = videoDevice.getJPEGFrameGrabber(this.width, this.height, this.channel, std, 80);
        frameGrabber.setCaptureCallback(this);
        width = frameGrabber.getWidth();
        height = frameGrabber.getHeight();
        System.out.println("Starting capture at "+width+"x"+height);
    }

    /**
     * this method stops the capture and releases the frame grabber and video device
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

    @Override
    public void exceptionReceived(V4L4JException e) {
        // This method is called by v4l4j if an exception
        // occurs while waiting for a new frame to be ready.
        // The exception is available through e.getCause()
        e.printStackTrace();
    }

}
