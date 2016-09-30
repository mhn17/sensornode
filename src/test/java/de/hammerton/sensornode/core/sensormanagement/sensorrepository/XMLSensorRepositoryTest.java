package de.hammerton.sensornode.core.sensormanagement.sensorrepository;

import de.hammerton.sensornode.core.sensormanagement.SensorList;
import de.hammerton.sensornode.core.sensormanagement.sensor.BasicSensor;
import de.hammerton.sensornode.core.sensormanagement.sensor.device.DeviceFactory;
import org.apache.commons.configuration.XMLConfiguration;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

@RunWith(MockitoJUnitRunner.class)
public class XMLSensorRepositoryTest {

    @Mock
    private DeviceFactory deviceFactory = null;

    private XMLSensorRepository repository = null;

    @Before
    public void setUp() throws Exception {
        XMLConfiguration configuration = new XMLConfiguration("sensors.xml");
        this.repository = new XMLSensorRepository(configuration, deviceFactory);
    }

    @After
    public void tearDown() throws Exception {
        this.repository = null;
    }

    @Test
    public void testGetActiveSensors() throws Exception {
        SensorList activeSensors = this.repository.getActiveSensors();
        assertTrue(activeSensors != null);
        assertTrue(activeSensors.size() == 2);

        assertEquals(BasicSensor.class, activeSensors.get(0).getClass());
        assertEquals(BasicSensor.class, activeSensors.get(1).getClass());

        // check optional parameters
        assertEquals(60000, activeSensors.get(1).getCaptureInterval());
        assertEquals("application/json", activeSensors.get(1).getDataType());
    }
}