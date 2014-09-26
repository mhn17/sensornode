package com.github.hammertonmarc.sensornode.core.sensormanagement;

import com.github.hammertonmarc.sensornode.core.sensormanagement.sensors.SensorMock;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

/**
 * @author Marc Hammerton
 */
public class SensorTest {

    private Sensor sensor = null;

    @Before
    public void setUp() throws Exception {
        this.sensor = new SensorMock(1, "sensorName", 5);
    }

    @After
    public void tearDown() throws Exception {
        this.sensor = null;
    }

    @Test
    public void getId() throws Exception {
        assertEquals(1, this.sensor.getId());
    }

    @Test
    public void getName() throws Exception {

    }

    @Test
    public void setAndGetData() throws Exception {

    }
}
