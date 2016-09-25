package de.hammerton.sensornode.core.sensordatamanagement;

import org.junit.Ignore;
import org.junit.Test;

import static org.junit.Assert.*;

public class SensorDataRepositoryFactoryTest {

    @Test
    @Ignore
    public void testGetRepository() throws Exception {
        assertTrue(SensorDataRepositoryFactory.getRepository() != null);
    }
}