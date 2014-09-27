package com.github.hammertonmarc.sensornode.core.sensormanagement.sensors;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

import static org.junit.Assert.*;

public class DummyTest {

    Dummy sensor = null;

    @Before
    public void setUp() throws Exception {
        this.sensor = new Dummy(1, "sensor", 10);
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
        Dummy spySensor = Mockito.spy(this.sensor);

        Mockito.when(spySensor.isCapturing()).thenReturn(true).thenReturn(false);
        spySensor.startCapturing();
        Mockito.verify(spySensor).setData(Mockito.any(byte[].class));
    }

    @Test
    public void testIsCapturing() throws Exception {
        assertFalse(this.sensor.isCapturing());
    }
}