package de.hammerton.sensornode.core.sensormanagement;

import de.hammerton.sensornode.core.exception.SensorManagementException;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.mockito.runners.MockitoJUnitRunner;

import static org.junit.Assert.assertSame;
import static org.mockito.Mockito.*;

/**
 * @author Marc Hammerton
 */
@RunWith(MockitoJUnitRunner.class)
public class SensorManagerTest {

    private SensorManager sensorManager = null;
    private SensorList sensorList = null;

    @Before
    public void setUp() throws Exception {
        this.sensorManager = SensorManager.getInstance();

        // create sensor list with sensor mocks
        this.sensorList = new SensorList();

        for (int i=0; i<3; i++) {
            Sensor sensor = mock(Sensor.class);
            Mockito.doNothing().when(sensor).run();
            sensorList.add(sensor);
        }

        this.sensorManager.setSensorList(sensorList);
    }

    @After
    public void tearDown() {
        this.sensorManager = null;
        this.sensorList = null;
    }

    @Test
    public void testCollectData() throws Exception {
        this.sensorManager.collectData();

        Thread.sleep(100);
        for (Sensor sensor : this.sensorManager.getSensorList()) {
            verify(sensor).run();
        }
    }

    @Test(expected = SensorManagementException.class)
    public void testCollectDataThrowsException() throws Exception {
        this.sensorManager.setSensorList(null);
        this.sensorManager.collectData();
    }

    @Test
    public void testGetSensorList() throws Exception {
        SensorList sensorList = this.sensorManager.getSensorList();
        assertSame("should be the same sensor list", this.sensorList, sensorList);
    }

    @Test
    public void closeAll() throws Exception {
        this.sensorManager.closeAll();

        for (Sensor sensor : this.sensorManager.getSensorList()) {
            verify(sensor).close();
        }
    }
}
