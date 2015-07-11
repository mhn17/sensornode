package de.hammerton.sensornode.core.sensormanagement.sensor.device;

import org.junit.Before;
import org.junit.Test;

import java.nio.ByteBuffer;

import static org.junit.Assert.*;

/**
 * @author Marc Hammerton
 */
public class DummyDeviceTest {

    private DummyDevice device = null;

    @Before
    public void setUp() {
        this.device = new DummyDevice();
    }

    @Test
    public void testReadData() throws Exception {
        ByteBuffer buffer = ByteBuffer.allocate(Long.SIZE);

        byte[] bytes = this.device.readData();
        buffer.put(bytes, 0, bytes.length);
        buffer.flip();
        assertEquals(1, buffer.getLong());
    }
}