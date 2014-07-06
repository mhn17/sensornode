package com.github.hammertonmarc.sensornode.sensordatamanagement;

import java.util.Collection;
import java.util.concurrent.ArrayBlockingQueue;

/**
 * Created by marc on 14.06.14.
 */
public class SensorDataQueue extends ArrayBlockingQueue<SensorData> {
    public SensorDataQueue(int capacity) {
        super(capacity);
    }
/*
    public boolean offer(SensorData data) {
        this.offer(data);
    }*/
}
