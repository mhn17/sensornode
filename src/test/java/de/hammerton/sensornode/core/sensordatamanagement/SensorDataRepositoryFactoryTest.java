package de.hammerton.sensornode.core.sensordatamanagement;

import org.junit.Test;

import static org.junit.Assert.*;

public class SensorDataRepositoryFactoryTest {

    @Test
    public void testGetRepository() throws Exception {
        assertTrue(SensorDataRepositoryFactory.getRepository() != null);
    }
}