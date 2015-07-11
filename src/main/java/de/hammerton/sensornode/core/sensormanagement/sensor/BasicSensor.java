package de.hammerton.sensornode.core.sensormanagement.sensor;

import de.hammerton.sensornode.core.sensormanagement.Sensor;
import de.hammerton.sensornode.core.sensormanagement.sensor.device.IBasicDevice;

import java.nio.ByteBuffer;

/**
 * BasicSensor sensor for reading data from file or something similar
 *
 * @author Marc Hammerton
 */
public class BasicSensor extends Sensor {

    private Boolean capturing = false;
    private IBasicDevice device = null;

    /**
     * Constructor using standard capture interval
     *
     * @param id The sensor ID
     * @param name The sensor name
     * @param device The web cam device

     */
    public BasicSensor(int id, String name, IBasicDevice device) {
        super(id, name, device);
        this.device = device;
    }

    /**
     * Constructor with specific capture interval
     *
     * @param id The sensor ID
     * @param name The sensor name
     * @param captureInterval The capture interval
     * @param device The web cam device
     */
    public BasicSensor(int id, String name, int captureInterval, IBasicDevice device) {
        super(id, name, captureInterval, device);
        this.device = device;
    }

    /**
     * @see Sensor#close()
     */
    @Override
    public void close() {
        this.capturing = false;
        this.device.release();
        System.out.println("BasicSensor: Closing " + this.name);
    }

    /**
     * @see Sensor#startCapturing()
     */
    @Override
    public void startCapturing() {
        System.out.println("BasicSensor: Starting to capture data for " + this.name);
        this.capturing = true;

        while (this.isCapturing()) {
            this.setData(this.device.readData());

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
