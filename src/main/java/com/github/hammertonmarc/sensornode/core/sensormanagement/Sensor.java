package com.github.hammertonmarc.sensornode.core.sensormanagement;

import com.github.hammertonmarc.sensornode.core.sensordatamanagement.SensorData;
import com.github.hammertonmarc.sensornode.core.sensordatamanagement.SensorDataQueue;

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
    protected BlockingQueue<SensorData> sensorDataQueue;
    protected byte[] data = null;

    /**
     * Constructor
     *  - set capture interval to 1 second
     *
     * @param id The sensor ID
     * @param name The name of the sensor
     */
    public Sensor(int id, String name) {
        this(id, name, 1000);
    }

    /**
     * Constructor
     *  - sets the capture interval to a specific value
     *
     * @param id The sensor ID
     * @param name The name of the sensor
     * @param captureInterval The capture interval
     */
    public Sensor(int id, String name, int captureInterval) {
        this.id = id;
        this.name = name;
        this.captureInterval = captureInterval;
        this.sensorDataQueue = SensorDataQueue.getInstance();
    }

    /**
     * Close the sensor
     * Perform all necessary actions to shutdown the sensor gracefully
     */
    public abstract void close();

    /**
     * Start capturing data from the sensor
     */
    public abstract void startCapturing();

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
            SensorData sensorData = new SensorData(this.id, this.name, this.data);
            try {
                this.sensorDataQueue.put(sensorData);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * Start capturing data for this sensor
     */
    @Override
    public void run() {
        this.startCapturing();
    }

}
