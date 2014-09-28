package de.hammerton.sensornode.core.sensormanagement.sensor;

import au.edu.jcu.v4l4j.FrameGrabber;
import au.edu.jcu.v4l4j.VideoFrame;
import au.edu.jcu.v4l4j.exceptions.V4L4JException;
import de.hammerton.sensornode.core.exception.SensorManagementException;
import de.hammerton.sensornode.core.sensormanagement.sensor.device.IWebCamDevice;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.runners.MockitoJUnitRunner;

import java.awt.image.BufferedImage;

@RunWith(MockitoJUnitRunner.class)
public class WebCamSensorTest {

    @Mock
    IWebCamDevice device = null;

    WebCamSensor sensor = null;

    @Before
    public void setUp() throws Exception {
        this.sensor = new WebCamSensor(1, "webCamSensor", 10, this.device);
    }

    @After
    public void tearDown() throws Exception {
        this.sensor = null;
    }

    @Test
    public void testStartCapturing() throws Exception {
        FrameGrabber frameGrabber = Mockito.mock(FrameGrabber.class);
        Mockito.doNothing().when(frameGrabber).setCaptureCallback(this.sensor);
        Mockito.doNothing().when(frameGrabber).startCapture();
        Mockito.doReturn(frameGrabber).when(this.device).getCurrentFrameGrabber();

        this.sensor.startCapturing();
        Mockito.verify(frameGrabber).startCapture();
    }

    @Test(expected = SensorManagementException.class)
    public void testStartCapturingThrowsException() throws Exception {
        WebCamSensor spySensor = Mockito.spy(new WebCamSensor(2, "webCamSensor", this.device));

        FrameGrabber frameGrabber = Mockito.mock(FrameGrabber.class);
        Mockito.doNothing().when(frameGrabber).setCaptureCallback(spySensor);
        Mockito.doThrow(new V4L4JException("test")).when(frameGrabber).startCapture();
        Mockito.doReturn(frameGrabber).when(this.device).getCurrentFrameGrabber();

        spySensor.startCapturing();
        Mockito.verify(spySensor).close();
    }

    @Test
    public void testClose() throws Exception {
        WebCamSensor spySensor = Mockito.spy(this.sensor);

        Mockito.doNothing().when(this.device).releaseAll();
        spySensor.close();

        Mockito.verify(this.device).releaseAll();
    }

    @Test
    public void testNextFrame() throws Exception {
        BufferedImage bi = new BufferedImage(10,10,BufferedImage.TYPE_INT_RGB);
        VideoFrame frame = Mockito.mock(VideoFrame.class);

        Mockito.doReturn(bi).when(frame).getBufferedImage();

        WebCamSensor spySensor = Mockito.spy(this.sensor);
        spySensor.nextFrame(frame);
        Mockito.verify(spySensor).setData(Mockito.any(byte[].class));
    }
}