package de.hammerton.sensornode.server.resources;

import de.hammerton.sensornode.core.sensordatamanagement.SensorDataRepository;
import junit.framework.TestCase;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.runners.MockitoJUnitRunner;

import java.util.ArrayList;
import java.util.UUID;

@RunWith(MockitoJUnitRunner.class)
public class SensorDataTest extends TestCase {

    @Mock
    private SensorDataRepository sensorDataRepository = null;

    private SensorData sensorData = null;

    @Before
    public void setUp() throws Exception {
        super.setUp();

        this.sensorData = new SensorData();
        this.sensorData.setSensorDataRepository(this.sensorDataRepository);
    }

    @Test
    public void testList() throws Exception {
        this.sensorData.list();
        Mockito.verify(this.sensorDataRepository).find();
    }

    @Test
    public void testDelete() throws Exception {
        UUID id = UUID.randomUUID();
        this.sensorData.delete(id.toString());
        Mockito.verify(this.sensorDataRepository).remove(id);
    }
}