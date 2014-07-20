package com.github.hammertonmarc.sensornode.core.sensordatamanagement;

import com.github.hammertonmarc.sensornode.core.sensordatamanagement.mongodb.MongoDbSensorDataRepository;

/**
 * Created by marc on 14.06.14.
 */
public class SensorDataManager implements Runnable {

    protected SensorDataQueue sensorDataQueue;
    protected boolean stop = false;
    protected SensorDataRepository repository;

    public SensorDataManager(SensorDataQueue sensorDataQueue) {
        this.sensorDataQueue = sensorDataQueue;
        this.repository = SensorDataRepositoryFactory.getRepository();
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
