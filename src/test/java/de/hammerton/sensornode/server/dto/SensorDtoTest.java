package de.hammerton.sensornode.server.dto;

import de.hammerton.sensornode.core.sensormanagement.Sensor;
import junit.framework.TestCase;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.runners.MockitoJUnitRunner;

import static org.junit.Assert.*;

@RunWith(MockitoJUnitRunner.class)
public class SensorDtoTest extends TestCase{

    @Mock
    private Sensor sensor = null;

    private SensorDto sensorDto = null;

    @Before
    public void setUp() throws Exception {
        super.setUp();

        Mockito.when(this.sensor.getId()).thenReturn(1);
        Mockito.when(this.sensor.getCaptureInterval()).thenReturn(1);
        Mockito.when(this.sensor.getName()).thenReturn("sensor1");

        this.sensorDto = new SensorDto(this.sensor);
    }

    @Test
    public void testGetId() throws Exception {
        assertEquals(1, this.sensorDto.getId());
    }

    @Test
    public void testGetCaptureInterval() throws Exception {
        assertEquals(1, this.sensorDto.getCaptureInterval());
    }

    @Test
    public void testGetName() throws Exception {
        assertEquals("sensor1", this.sensorDto.getName());
    }
}