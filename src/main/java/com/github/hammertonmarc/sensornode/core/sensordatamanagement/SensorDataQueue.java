package com.github.hammertonmarc.sensornode.core.sensordatamanagement;

import java.util.concurrent.ArrayBlockingQueue;

/**
 * Data queue for sensor data. It buffers the data until it can be stored in the database.
 *
 * @author Marc Hammerton
 */
public class SensorDataQueue extends ArrayBlockingQueue<SensorData> {

    /**
     * The maximum number of elements in the queue
     */
    public static int capacity = 1024;

    private static SensorDataQueue instance = new SensorDataQueue();

    /**
     * Constructor
     */
    private SensorDataQueue() {
        super(capacity);
    }

    /**
     * Returns singleton instance of SensorDataQueue
     *
     * @return An instance of SensorDataQueue
     */
    public static SensorDataQueue getInstance() {
        return instance;
    }
}
