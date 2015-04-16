package de.hammerton.sensornode.core.sensormanagement.sensor;

import de.hammerton.sensornode.core.sensormanagement.Sensor;

import java.nio.ByteBuffer;
//import java.util.Random;

/**
 * DummySensor sensor for testing
 *
 * @author Marc Hammerton
 */
public class DummySensor extends Sensor {

    private static long DATA = 0;

    private Boolean capturing = false;

    /**
     * @see de.hammerton.sensornode.core.sensormanagement.Sensor#Sensor(int, String)
     */
    public DummySensor(int id, String name) {
        super(id, name);
    }

    /**
     * @see de.hammerton.sensornode.core.sensormanagement.Sensor#Sensor(int, String, int)
     */
    public DummySensor(int id, String name, int captureInterval) {
        super(id, name, captureInterval);
    }

    /**
     * @see de.hammerton.sensornode.core.sensormanagement.Sensor#close()
     */
    @Override
    public void close() {
        this.capturing = false;
        System.out.println("DummySensor: Closing " + this.name);
    }

    /**
     * @see de.hammerton.sensornode.core.sensormanagement.Sensor#startCapturing()
     */
    @Override
    public void startCapturing() {
        System.out.println("DummySensor: Starting to capture data for " + this.name);
        this.capturing = true;

//        Random random = new Random();
//        byte[] randomBytes = new byte[4];

        while (this.isCapturing()) {
            // set random data
//            random.nextBytes(randomBytes);
//            this.setData(randomBytes);

            DATA++;
            this.setData(ByteBuffer.allocate(8).putLong(DATA).array());

            this.waitForNextCapture();
        }
    }

    /**
     * Shows if the sensor is still capturing data
     *
     * @return True when the sensor is capturing, false when it stopped
     */
    public Boolean isCapturing() {
        return capturing;
    }

}
