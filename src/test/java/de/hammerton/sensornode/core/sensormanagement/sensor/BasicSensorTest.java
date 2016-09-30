package de.hammerton.sensornode.core.sensormanagement.sensor;

import de.hammerton.sensornode.core.sensormanagement.sensor.device.IBasicDevice;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import static org.junit.Assert.*;

/**
 * @author Marc Hammerton
 */
public class BasicSensorTest {

    private BasicSensor sensor = null;

    @Mock
    private IBasicDevice device = null;

    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);
        this.sensor = new BasicSensor(1, "sensor", 10, this.device);
    }

    @After
    public void tearDown() throws Exception {
        this.sensor = null;
    }

    @Test
    public void testClose() throws Exception {
        this.sensor.close();
        assertFalse(this.sensor.isCapturing());
    }

    @Test
    public void testStartCapturing() throws Exception {
        BasicSensor spySensor = Mockito.spy(this.sensor);

        Mockito.when(spySensor.isCapturing()).thenReturn(true).thenReturn(false);
        spySensor.startCapturing();
        Mockito.verify(spySensor).setData(Mockito.any(byte[].class));
    }

    @Test
    public void testStartCapturingSkipSettingData() throws Exception {
        BasicSensor spySensor = Mockito.spy(this.sensor);

        Mockito.when(spySensor.isCapturing()).thenReturn(true).thenReturn(false);
        Mockito.when(this.device.readData()).thenThrow(new NoDataAvailableException(""));
        spySensor.startCapturing();
        Mockito.verify(spySensor, Mockito.never()).setData(Mockito.any(byte[].class));
    }

    @Test
    public void testIsCapturing() throws Exception {
        assertFalse(this.sensor.isCapturing());
    }

    @Test
    public void testGetCaptureInterval() throws Exception {
        assertEquals(10, this.sensor.getCaptureInterval());
    }

    @Test
    public void testRun() throws Exception {
        BasicSensor spySensor = Mockito.spy(this.sensor);

        Mockito.when(spySensor.isCapturing()).thenReturn(false);
        spySensor.run();
        Mockito.verify(spySensor).startCapturing();
    }
}