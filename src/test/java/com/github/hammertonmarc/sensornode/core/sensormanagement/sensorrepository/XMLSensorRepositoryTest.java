package com.github.hammertonmarc.sensornode.core.sensormanagement.sensorrepository;

import com.github.hammertonmarc.sensornode.core.sensormanagement.SensorList;
import com.github.hammertonmarc.sensornode.core.sensormanagement.sensor.DummySensor;
import com.github.hammertonmarc.sensornode.core.sensormanagement.sensor.WebCamSensor;
import com.github.hammertonmarc.sensornode.core.sensormanagement.sensor.device.DeviceFactory;
import com.github.hammertonmarc.sensornode.core.sensormanagement.sensor.device.IWebCamDevice;
import org.apache.commons.configuration.XMLConfiguration;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
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
        IWebCamDevice device = Mockito.mock(IWebCamDevice.class);
        Mockito.doReturn(device).when(this.deviceFactory).getWebCamDevice("/dev/video0", 640, 480, 0);

        SensorList activeSensors = this.repository.getActiveSensors();
        assertTrue(activeSensors != null);
        assertTrue(activeSensors.size() == 2);

        assertEquals(WebCamSensor.class, activeSensors.get(0).getClass());
        assertEquals(DummySensor.class, activeSensors.get(1).getClass());
    }
}