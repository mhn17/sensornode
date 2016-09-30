package de.hammerton.sensornode.core.sensormanagement.sensor.device;

import java.nio.ByteBuffer;

/**
 * DummyDevice device for testing
 *
 * @author Marc Hammerton
 */
public class DummyDevice implements IBasicDevice {

    private static long DATA = 0;

    @Override
    public byte[] readData() {

        DATA++;

        return ByteBuffer.allocate(Long.SIZE).putLong(DATA).array();
    }

    @Override
    public void release() {
        // nothing to do
    }
}
