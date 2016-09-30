package de.hammerton.sensornode.core.sensormanagement.sensor.device;

import de.hammerton.sensornode.core.sensormanagement.sensor.NoDataAvailableException;
import de.hammerton.sensornode.core.sensormanagement.sensor.device.adapter.IStringAdapter;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Matchers;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.mockito.runners.MockitoJUnitRunner;

import java.io.File;
import java.net.URL;
import java.util.Date;

import static org.junit.Assert.*;

/**
 * @author Marc Hammerton
 */
@RunWith(MockitoJUnitRunner.class)
public class FileDeviceTest {

    private FileDevice device = null;
    private File file = null;

    @Mock
    private IStringAdapter adapter = null;

    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);

        ClassLoader classLoader = getClass().getClassLoader();
        URL url = classLoader.getResource("fixtures/temperature.txt");
        if (url == null) {
            fail("could not load test fixture");
        }

        this.file = new File(url.getPath());
        this.device = new FileDevice(url.getPath(), this.adapter);
    }

    @After
    public void tearDown() throws Exception {
        this.device.release();
    }

    @Test
    public void testReadData() throws Exception {
        // make sure last modified of file is now
        assertTrue("failed setting last modified for fixture",
                this.file.setLastModified(new Date().getTime() + 20000));

        Mockito.doReturn("21").when(this.adapter).extractData(Matchers.<String>any());

        assertEquals("21", new String(this.device.readData()));
    }

    @Test(expected = NoDataAvailableException.class)
    public void testReadDataWithFileNotUpdated() throws Exception {
        // make sure last modified of file is in the past
        assertTrue("failed setting last modified for fixture",
                this.file.setLastModified(new Date().getTime() - 20000));

        this.device.readData();
    }
}