package com.github.hammertonmarc.sensornode.core.sensordatamanagement;

import com.github.hammertonmarc.sensornode.core.exceptions.SensorDataManagementException;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.runners.MockitoJUnitRunner;

import static org.junit.Assert.*;

@RunWith(MockitoJUnitRunner.class)
public class SensorDataManagerTest {

    @Mock
    private SensorDataQueue sensorDataQueue = null;

    @Mock
    private SensorDataRepository repository = null;

    private SensorDataManager manager = null;

    @Before
    public void setUp() throws Exception {
        this.manager = new SensorDataManager();
        this.manager.setSensorDataQueue(this.sensorDataQueue);
        this.manager.setRepository(this.repository);
    }

    @After
    public void tearDown() throws Exception {
        this.manager = null;
    }

    @Test
    public void testStart() throws Exception {
        SensorData sensorData = Mockito.mock(SensorData.class);
        Mockito.when(this.sensorDataQueue.take())
                .thenReturn(sensorData)
                .thenReturn(sensorData)
                .thenReturn(this.sensorDataQueue.getPoisonPill());

        this.manager.start();
        Mockito.verify(this.repository, Mockito.times(2)).add(sensorData);
    }

    @Test(expected=SensorDataManagementException.class)
    public void testStartWithMissingInitialisation() throws Exception {
        SensorDataManager manager = new SensorDataManager();
        manager.start();
    }

    @Test
    public void testStop() throws Exception {
        SensorData sensorData = Mockito.mock(SensorData.class);
        Mockito.when(this.sensorDataQueue.getPoisonPill())
                .thenReturn(sensorData);

        this.manager.stop();
        Mockito.verify(this.sensorDataQueue).add(sensorData);
    }

    @Test
    public void testSetAndGetSensorDataQueue() throws Exception {
        this.manager.setSensorDataQueue(this.sensorDataQueue);
        assertEquals(this.sensorDataQueue, this.manager.getSensorDataQueue());
    }

    @Test
    public void testSetAndGetRepository() throws Exception {
        this.manager.setRepository(this.repository);
        assertEquals(this.repository, this.manager.getRepository());
    }
}