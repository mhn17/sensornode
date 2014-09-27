package com.github.hammertonmarc.sensornode.core.sensormanagement.sensors;

import com.github.hammertonmarc.sensornode.core.exceptions.SensorManagementException;
import com.github.hammertonmarc.sensornode.core.sensormanagement.Sensor;
import java.util.Random;


/**
 * Dummy sensor for testing
 *
 * @author Marc Hammerton
 */
public class Dummy extends Sensor {

    private Boolean capturing = false;

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
        this.capturing = false;
        System.out.println("closing dummy sensor");
    }

    /**
     * @see com.github.hammertonmarc.sensornode.core.sensormanagement.Sensor#startCapturing()
     */
    @Override
    public void startCapturing() {
        this.capturing = true;

        Random random = new Random();
        byte[] randomBytes = new byte[4];

        while (this.isCapturing()) {
            // set random data
            random.nextBytes(randomBytes);
            this.setData(randomBytes);
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
