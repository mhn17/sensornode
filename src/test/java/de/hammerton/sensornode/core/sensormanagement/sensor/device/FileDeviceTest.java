package de.hammerton.sensornode.core.sensormanagement.sensor.device;

import de.hammerton.sensornode.core.sensormanagement.sensor.device.adapter.IStringAdapter;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Matchers;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.runners.MockitoJUnitRunner;

import java.net.URL;

import static org.junit.Assert.*;
import static org.mockito.Matchers.any;

/**
 * @author Marc Hammerton
 */
@RunWith(MockitoJUnitRunner.class)
public class FileDeviceTest {

    private FileDevice device = null;

    @Mock
    private IStringAdapter adapter = null;

    @Before
    public void setUp() throws Exception {
        ClassLoader classLoader = getClass().getClassLoader();
        URL url = classLoader.getResource("fixtures/temperature.txt");
        if (url == null) {
            fail("could not load test fixture");
        }

        this.device = new FileDevice(url.getPath(), this.adapter);
    }

    @After
    public void tearDown() throws Exception {
        this.device.release();
    }

    @Test
    public void testReadData() throws Exception {
        Mockito.doReturn("21").when(this.adapter).extractData(Matchers.<String>any());

        assertEquals("21", new String(this.device.readData()));
    }
}