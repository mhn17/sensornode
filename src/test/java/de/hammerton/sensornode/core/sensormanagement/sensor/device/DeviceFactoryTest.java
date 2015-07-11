package de.hammerton.sensornode.core.sensormanagement.sensor.device;

import de.hammerton.sensornode.core.sensormanagement.SensorManagementException;
import de.hammerton.sensornode.core.sensormanagement.sensor.device.adapter.IStringAdapter;
import org.junit.After;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.mockito.runners.MockitoJUnitRunner;

import java.io.File;
import java.net.URL;

import static org.junit.Assert.*;

/**
 * @author Marc Hammerton
 */
@RunWith(MockitoJUnitRunner.class)
public class DeviceFactoryTest {

    private DeviceFactory deviceFactory = null;
    private IDevice device = null;

    @Before
    public void setUp() throws Exception {
        this.deviceFactory = new DeviceFactory();
    }

    @After
    public void tearDown() throws Exception {
        if (this.device != null) {
            this.device.release();
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

    @Test
    public void testGetFileDevice() throws Exception {
        ClassLoader classLoader = getClass().getClassLoader();
        URL url = classLoader.getResource("fixtures/temperature.txt");
        if (url == null) {
            fail("could not load test fixture");
        }

        this.device = this.deviceFactory.getFileDevice(url.getPath(), Mockito.mock(IStringAdapter.class));

        assertNotNull(device);
    }

    @Test(expected = SensorManagementException.class)
    public void testGetFileDeviceThrowsSensorManagementException()
            throws SensorManagementException {
        this.deviceFactory.getFileDevice("path/to/device", Mockito.mock(IStringAdapter.class));
    }

    @Test
    public void testGetDummyDevice() throws Exception {
        this.device = this.deviceFactory.getDummyDevice();

        assertNotNull(device);
    }
}