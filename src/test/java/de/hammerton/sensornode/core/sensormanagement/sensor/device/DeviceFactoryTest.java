package de.hammerton.sensornode.core.sensormanagement.sensor.device;

import de.hammerton.sensornode.core.sensormanagement.SensorManagementException;
import org.junit.After;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.runners.MockitoJUnitRunner;

import static org.junit.Assert.*;

/**
 * @author Marc Hammerton
 */
@RunWith(MockitoJUnitRunner.class)
public class DeviceFactoryTest {

    private DeviceFactory deviceFactory = null;
    private IWebCamDevice device = null;

    @Before
    public void setUp() throws Exception {
        this.deviceFactory = new DeviceFactory();
    }

    @After
    public void tearDown() throws Exception {
        if (this.device != null) {
            this.device.releaseAll();
        }
    }

    @Test
    @Ignore
    public void testGetWebCamDevice() throws Exception {
        this.device = this.deviceFactory.getWebCamDevice("/dev/video0", 640, 480, 0);

        assertNotNull(device);
    }

    @Test(expected = SensorManagementException.class)
    public void testGetWebCamDeviceThrowsSensorManagementException()
            throws SensorManagementException {
        this.deviceFactory.getWebCamDevice("path/to/device", 1, 1, 1);
    }
}