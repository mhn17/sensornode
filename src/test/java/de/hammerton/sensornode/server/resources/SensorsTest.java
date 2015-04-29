package de.hammerton.sensornode.server.resources;

import de.hammerton.sensornode.core.sensormanagement.Sensor;
import de.hammerton.sensornode.core.sensormanagement.SensorList;
import de.hammerton.sensornode.core.sensormanagement.SensorManager;
import de.hammerton.sensornode.server.dto.SensorDto;
import junit.framework.TestCase;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.runners.MockitoJUnitRunner;

import java.util.ArrayList;

@RunWith(MockitoJUnitRunner.class)
public class SensorsTest extends TestCase {

    @Mock
    private SensorManager sensorManager = null;

    private Sensors sensors = null;

    @Before
    public void setUp() throws Exception {
        super.setUp();

        this.sensors = new Sensors();
        this.sensors.setSensorManager(this.sensorManager);

        this.setUpSensorList();
    }

    @Test
    public void testList() throws Exception {
        ArrayList<SensorDto> list = this.sensors.list();

        assertEquals(2, list.size());
        assertEquals(1, list.get(0).getId());
        assertEquals(2, list.get(1).getId());
    }

    @Test
    public void testGetWhenSensorExists() throws Exception {
        SensorDto sensorDto = this.sensors.get(1);

        assertEquals(1, sensorDto.getId());
    }

    @Test
    public void testGetWhenSensorDoesNotExist() throws Exception {
        SensorDto sensorDto = this.sensors.get(9999);

        assertEquals(null, sensorDto);
    }

    /**
     * Helper method to set up the sensor list
     */
    private void setUpSensorList() {
        Sensor sensor1 = Mockito.mock(Sensor.class);
        Mockito.when(sensor1.getId()).thenReturn(1);

        Sensor sensor2 = Mockito.mock(Sensor.class);
        Mockito.when(sensor2.getId()).thenReturn(2);

        SensorList sensorList = new SensorList();
        sensorList.add(sensor1);
        sensorList.add(sensor2);

        Mockito.when(this.sensorManager.getSensorList())
                .thenReturn(sensorList);
    }
}