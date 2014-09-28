package com.github.hammertonmarc.sensornode.core.sensormanagement;

import com.github.hammertonmarc.sensornode.core.sensordatamanagement.SensorData;
import com.github.hammertonmarc.sensornode.core.sensordatamanagement.SensorDataQueue;
import com.github.hammertonmarc.sensornode.core.sensormanagement.sensor.SensorMock;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

import static org.junit.Assert.*;

public class SensorTest {

    private Sensor sensor = null;

    @Before
    public void setUp() throws Exception {
        this.sensor = new SensorMock(25, "sensor");
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
}