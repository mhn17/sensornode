package de.hammerton.sensornode.core.sensormanagement.sensor.device;

import java.nio.ByteBuffer;
//import java.util.Random;

/**
 * DummyDevice device for testing
 *
 * @author Marc Hammerton
 */
public class DummyDevice implements IBasicDevice {

    private static long DATA = 0;

    @Override
    public byte[] readData() {
        // Random random = new Random();
        // byte[] randomBytes = new byte[4];
        // set random data
        // random.nextBytes(randomBytes);
        // this.setData(randomBytes);

        DATA++;

        return ByteBuffer.allocate(Long.SIZE).putLong(DATA).array();
    }

    @Override
    public void release() {
        // nothing to do
    }
}
