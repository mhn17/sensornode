package com.github.hammertonmarc.sensornode.core.sensormanagement.sensors;

import com.github.hammertonmarc.sensornode.core.sensormanagement.Sensor;
import java.util.Random;


/**
 * Dummy sensor for testing
 *
 * @author Marc Hammerton
 */
public class Dummy extends Sensor {

    private Boolean continueCapture = true;

    /**
     * @see com.github.hammertonmarc.sensornode.core.sensormanagement.Sensor#Sensor(int, String)
     */
    public Dummy(int id, String name) {
        super(id, name);
    }

    /**
     * @see com.github.hammertonmarc.sensornode.core.sensormanagement.Sensor#Sensor(int, String, int)
     */
    public Dummy(int id, String name, int captureInterval) {
        super(id, name, captureInterval);
    }

    /**
     * @see com.github.hammertonmarc.sensornode.core.sensormanagement.Sensor#close()
     */
    @Override
    public void close() {
        this.continueCapture = false;
        System.out.println("closing dummy sensor");
    }

    /**
     * @see com.github.hammertonmarc.sensornode.core.sensormanagement.Sensor#startCapturing()
     */
    @Override
    public void startCapturing() {
        Random random = new Random();
        byte[] randomBytes = new byte[4];
        while (this.continueCapture) {
            // set random data
            random.nextBytes(randomBytes);
            this.setData(randomBytes);

            // wait for next capture
            try {
                Thread.sleep(captureInterval);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

}
