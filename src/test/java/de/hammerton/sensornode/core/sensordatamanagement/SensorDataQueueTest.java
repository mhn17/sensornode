package de.hammerton.sensornode.core.sensordatamanagement;

import org.junit.Test;

import static org.junit.Assert.*;

public class SensorDataQueueTest {

    @Test
    public void testGetInstance() throws Exception {
        assertTrue(SensorDataQueue.getInstance() != null);
    }

    @Test
    public void testGetPoisonPill() throws Exception {
        SensorData poisonPill = SensorDataQueue.getInstance().getPoisonPill();
        assertEquals(99999999, poisonPill.getSensorId());
    }
}