package com.github.hammertonmarc.sensornode.core.sensormanagement.sensorrepository;

import com.github.hammertonmarc.sensornode.core.sensormanagement.SensorList;
import com.github.hammertonmarc.sensornode.core.sensormanagement.sensors.Dummy;
import com.github.hammertonmarc.sensornode.core.sensormanagement.sensors.WebCam;
import org.apache.commons.configuration.XMLConfiguration;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.runners.MockitoJUnitRunner;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

@RunWith(MockitoJUnitRunner.class)
public class XMLSensorRepositoryTest {

    private XMLSensorRepository repository = null;

    @Before
    public void setUp() throws Exception {
        XMLConfiguration configuration = new XMLConfiguration("sensors.xml");
        this.repository = new XMLSensorRepository(configuration);
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

        assertEquals(WebCam.class, activeSensors.get(0).getClass());
        assertEquals(Dummy.class, activeSensors.get(1).getClass());
    }
}