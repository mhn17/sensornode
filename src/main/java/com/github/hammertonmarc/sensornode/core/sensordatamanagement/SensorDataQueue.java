package com.github.hammertonmarc.sensornode.core.sensordatamanagement;

import java.util.concurrent.ArrayBlockingQueue;

/**
 * Created by marc on 14.06.14.
 */
public class SensorDataQueue extends ArrayBlockingQueue<SensorData> {

    public static int capacity = 1024;

    private static SensorDataQueue instance = new SensorDataQueue();

    private SensorDataQueue() {
        super(capacity);
    }

    public static SensorDataQueue getInstance() {
        return instance;
    }
}
