package de.hammerton.sensornode.core.sensormanagement;

import de.hammerton.sensornode.core.sensordatamanagement.SensorData;
import de.hammerton.sensornode.core.sensordatamanagement.SensorDataQueue;
import de.hammerton.sensornode.core.sensormanagement.sensor.device.IDevice;

import java.util.concurrent.BlockingQueue;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * Basic class for sensors
 *
 * @author Marc Hammerton
 */
@XmlRootElement
public abstract class Sensor implements Runnable {

    protected int id, captureInterval;
    protected String name;
    protected SensorDataQueue sensorDataQueue;
    protected byte[] data = null;
    protected IDevice device = null;

    /**
     * Constructor
     *  - set capture interval to 1 second
     *
     * @param id The sensor ID
     * @param name The name of the sensor
     */
    public Sensor(int id, String name, IDevice device) {
        this(id, name, 3000, device);
    }

    /**
     * Constructor
     *  - sets the capture interval to a specific value
     *
     * @param id The sensor ID
     * @param name The name of the sensor
     * @param captureInterval The capture interval
     */
    public Sensor(int id, String name, int captureInterval, IDevice device) {
        this.id = id;
        this.name = name;
        this.captureInterval = captureInterval;
        this.device = device;
        this.sensorDataQueue = SensorDataQueue.getInstance();
    }

    /**
     * Start capturing data from the sensor
     *
     * @throws SensorManagementException
     */
    public abstract void startCapturing() throws SensorManagementException;

    /**
     * Return the sensor ID
     *
     * @return The sensor id
     */
    public int getId() {
        return id;
    }

    /**
     * Return the sensor name
     *
     * @return The sensor name
     */
    public String getName() {
        return this.name;
    }

    /**
     * Return the interval for capturing data
     *
     * @return The capture interval
     */
    public int getCaptureInterval() {
        return this.captureInterval;
    }

    /**
     * Return the last read sensor data
     *
     * @return The sensor data
     */
    public byte[] getData() {
        return this.data;
    }

    /**
     * After getting data from the sensor it is set as a byte array and added to the sensor data queue for further
     * processing (e.g. storing it in a database)
     *
     * @param data The data read from the sensor
     */
    public void setData(byte[] data) {
        this.data = data;

        if (this.sensorDataQueue != null) {
            SensorData sensorData = new SensorData(this.id, this.data);
            try {
                this.sensorDataQueue.put(sensorData);
            } catch (InterruptedException e) {
                System.out.println("Sensor " + this.name + ": interrupted while adding data to the queue.");
            }
        }
    }

    /**
     * Get the sensor data queue
     *
     * return the sensor data queue
     */
    public SensorDataQueue getSensorDataQueue() {
        return sensorDataQueue;
    }

    /**
     * Set the sensor data queue
     *
     * @param sensorDataQueue The sensor data queue
     */
    public void setSensorDataQueue(SensorDataQueue sensorDataQueue) {
        this.sensorDataQueue = sensorDataQueue;
    }

    /**
     * Wait for next capture depending on the capture interval
     */
    protected synchronized void waitForNextCapture() {
        try {
            Thread.sleep(this.captureInterval);
        } catch (InterruptedException e) {
            this.close();
        }
    }

    /**
     * Start capturing data for this sensor
     */
    @Override
    public void run() {
        try {
            this.startCapturing();
        } catch (SensorManagementException e) {
            e.printStackTrace();
        }
    }

    /**
     * Close the sensor
     * Perform all necessary actions to shutdown the sensor gracefully
     */
    public void close() {
        System.out.println("Sensor: Closing " + this.name);
        this.device.release();
    }


    /**
     * Return the current device for this sensor
     *
     * @return The current device
     */
    public IDevice getDevice() {
        return this.device;
    }
}
