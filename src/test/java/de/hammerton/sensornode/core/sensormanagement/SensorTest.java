package de.hammerton.sensornode.core.sensormanagement;

import de.hammerton.sensornode.core.sensordatamanagement.SensorData;
import de.hammerton.sensornode.core.sensordatamanagement.SensorDataQueue;
import de.hammerton.sensornode.core.sensormanagement.sensor.SensorMock;
import de.hammerton.sensornode.core.sensormanagement.sensor.device.IDevice;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

import static org.junit.Assert.*;

public class SensorTest {

    private Sensor sensor = null;

    @Before
    public void setUp() throws Exception {
        this.sensor = new SensorMock(25, "sensor", Mockito.mock(IDevice.class));
    }

    @After
    public void tearDown() throws Exception {
        this.sensor = null;
    }

    @Test
    public void testGetId() throws Exception {
        assertEquals(25, this.sensor.getId());
    }

    @Test
    public void testGetName() throws Exception {
        assertEquals("sensor", this.sensor.getName());
    }

    @Test
    public void testGetCaptureData() throws Exception {
        assertEquals(Sensor.DEFAULT_CAPTURE_INTERVAL, this.sensor.getCaptureInterval());
    }

    @Test
    public void testGetDataType() throws Exception {
        assertEquals(Sensor.DEFAULT_DATA_TYPE, this.sensor.getDataType());
    }

    @Test
    public void testCreateSensorWithCustomData() throws Exception {
        Sensor sensor = new SensorMock(1, "new sensor", 60000, "application/json", Mockito.mock(IDevice.class));

        assertEquals(1, sensor.getId());
        assertEquals("new sensor", sensor.getName());
        assertEquals(60000, sensor.getCaptureInterval());
        assertEquals("application/json", sensor.getDataType());
    }

    @Test
    public void testSetAndGetData() throws Exception {
        byte[] data = new byte[] {1, 5, 3};

        SensorDataQueue queue = Mockito.mock(SensorDataQueue.class);
        Mockito.doNothing().when(queue).put(Mockito.any(SensorData.class));
        this.sensor.setSensorDataQueue(queue);

        this.sensor.setData(data);
        Mockito.verify(queue).put(Mockito.any(SensorData.class));
        assertEquals(data, this.sensor.getData());
    }

    @Test
    public void testSetAndGetSensorDataQueue() throws Exception {
        SensorDataQueue queue = Mockito.mock(SensorDataQueue.class);
        this.sensor.setSensorDataQueue(queue);
        assertEquals(queue, this.sensor.getSensorDataQueue());
    }


    @Test
    public void testClose() throws Exception {
        Sensor spySensor = Mockito.spy(this.sensor);

        Mockito.doNothing().when(spySensor.device).release();
        spySensor.close();

        Mockito.verify(spySensor.device).release();
    }
}