package de.hammerton.sensornode.core.sensormanagement.sensor;

import au.edu.jcu.v4l4j.*;
import au.edu.jcu.v4l4j.exceptions.V4L4JException;
import de.hammerton.sensornode.core.sensormanagement.SensorManagementException;
import de.hammerton.sensornode.core.sensormanagement.Sensor;
import de.hammerton.sensornode.core.sensormanagement.sensor.device.IWebCamDevice;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.IOException;

/**
 * A web cam sensor which takes periodically photos according to the capture interval
 *
 * @author Marc Hammerton
 */
public class WebCamSensor extends Sensor implements CaptureCallback {

    private IWebCamDevice device = null;

    /**
     * Constructor using standard capture interval
     *
     * @param id The sensor ID
     * @param name The sensor name
     * @param device The web cam device

     */
    public WebCamSensor(int id, String name, IWebCamDevice device) {
        super(id, name);
        this.device = device;
    }

    /**
     * Constructor with specific capture interval
     *
     * @param id The sensor ID
     * @param name The sensor name
     * @param captureInterval The capture interval
     * @param device The web cam device
     */
    public WebCamSensor(int id, String name, int captureInterval, IWebCamDevice device) {
        super(id, name, captureInterval);
        this.device = device;
    }

    /**
     * Initialise video device and frame grabber and start capturing
     *
     * @see de.hammerton.sensornode.core.sensormanagement.Sensor#startCapturing()
     */
    public synchronized void startCapturing() throws SensorManagementException {
        try {
            this.device.getCurrentFrameGrabber().setCaptureCallback(this);
            this.device.getCurrentFrameGrabber().startCapture();
        } catch (V4L4JException e) {
            // cleanup and exit
            this.close();

            throw new SensorManagementException("Could not start capturing for web cam");
        }
    }

    /**
     * Stops the capture and releases the frame grabber and video device
     *
     * @see de.hammerton.sensornode.core.sensormanagement.Sensor#close()
     */
    public synchronized void close() {
        System.out.println("closing web cam");
        this.device.releaseAll();
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

        this.waitForNextCapture();
        videoFrame.recycle();
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
