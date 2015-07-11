package de.hammerton.sensornode.core.sensormanagement.sensor.device.adapter;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 * @author Marc Hammerton
 */
public class TemperatureStringAdapterTest {

    private TemperatureStringAdapter adapter = null;

    @Before
    public void setUp() throws Exception {
        this.adapter = new TemperatureStringAdapter();
    }

    @Test
    public void testExtractData() throws Exception {
        assertEquals("15.2", this.adapter.extractData("Temperature: 15.2"));
    }
}