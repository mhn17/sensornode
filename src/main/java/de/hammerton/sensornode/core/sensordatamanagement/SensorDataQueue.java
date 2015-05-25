package de.hammerton.sensornode.core.sensordatamanagement;

import java.util.concurrent.ArrayBlockingQueue;

/**
 * A data queue for sensor data. It buffers the data until it can be stored in the database.
 *
 * @author Marc Hammerton
 */
public class SensorDataQueue extends ArrayBlockingQueue<SensorData> {

    /**
     * This element can be added to the queue to indicate it is going to be closed
     */
    private final SensorData POISON_PILL = new SensorData(99999999, new byte[1]);

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

    /**
     * Get the poison pill object, which can be used to stop taking from the queue
     *
     * @return The sensor data poison pill
     */
    public SensorData getPoisonPill() {
        return this.POISON_PILL;
    }
}
