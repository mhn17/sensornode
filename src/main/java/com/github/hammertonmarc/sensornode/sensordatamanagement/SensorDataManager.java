package com.github.hammertonmarc.sensornode.sensordatamanagement;

import com.github.hammertonmarc.sensornode.sensordatamanagement.mongodb.MongoDbSensorDataRepository;

import java.util.Collection;
import java.util.Iterator;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.TimeUnit;

/**
 * Created by marc on 14.06.14.
 */
public class SensorDataManager implements Runnable {

    protected SensorDataQueue sensorDataQueue;
    protected boolean stop = false;
    protected SensorDataRepository repository;

    public SensorDataManager(SensorDataQueue sensorDataQueue) {
        this.sensorDataQueue = sensorDataQueue;
        this.repository = new MongoDbSensorDataRepository();
    }

    @Override
    public void run() {
        while (!this.stop) {
            try {
                SensorData data = this.sensorDataQueue.take();
                this.repository.add(data);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    public SensorDataRepository getRepository() {
        return this.repository;
    }

    public void stop() {
        this.stop = true;
    }
}
