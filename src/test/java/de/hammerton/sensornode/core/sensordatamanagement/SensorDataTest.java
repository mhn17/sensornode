package de.hammerton.sensornode.core.sensordatamanagement;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.util.Calendar;
import java.util.UUID;

import static org.junit.Assert.*;

public class SensorDataTest {

    private SensorData sensorData = null;
    private UUID id = UUID.randomUUID();
    private int sensorId = 1;
    private String name = "sensorName";
    private long timestamp = 1000;
    private byte[] data = "data".getBytes();

    @Before
    public void setUp() throws Exception {
        this.sensorData = new SensorData(this.id, this.sensorId, this.name, this.timestamp, this.data);
    }

    @After
    public void tearDown() throws Exception {
        this.sensorData = null;
    }

    @Test
    public void testGetId() throws Exception {
        assertEquals(this.id, this.sensorData.getId());
    }

    @Test
    public void testGetSensorId() throws Exception {
        assertEquals(this.sensorId, this.sensorData.getSensorId());
    }

    @Test
    public void testGetSensorName() throws Exception {
        assertEquals(this.name, this.sensorData.getSensorName());
    }

    @Test
    public void testGetTimestamp() throws Exception {
        assertEquals(this.timestamp, this.sensorData.getTimestamp());
    }

    @Test
    public void testGetTimestampUsingCurrent() throws Exception {
        SensorData sensorData = new SensorData(this.sensorId, this.name, this.data);

        long timestamp = Calendar.getInstance().getTime().getTime();
        assertTrue(timestamp >= sensorData.getTimestamp());
        assertTrue(this.timestamp <= sensorData.getTimestamp());
    }

    @Test
    public void testGetData() throws Exception {
        assertEquals(this.data, this.sensorData.getData());
    }
}