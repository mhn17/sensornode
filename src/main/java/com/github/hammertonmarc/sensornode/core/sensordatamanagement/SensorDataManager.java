package com.github.hammertonmarc.sensornode.core.sensordatamanagement;

import com.github.hammertonmarc.sensornode.core.exceptions.SensorDataManagementException;

/**
 * The SensorDataManager is responsible for getting data from the data queue and adding it to the repository
 *
 *  @author Marc Hammerton
 */
public class SensorDataManager implements Runnable {

    protected SensorDataQueue sensorDataQueue;
    protected SensorDataRepository repository;
    protected boolean stop = false;

    /**
     * Constructor
     *  - initialise the data queue and repository
     */
    public SensorDataManager() throws SensorDataManagementException {
        this.sensorDataQueue = SensorDataQueue.getInstance();
        this.repository = SensorDataRepositoryFactory.getRepository();
    }

    /**
     * Start checking the data queue for new data and adding it to the repository
     */
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

    /**
     * Return the sensor data repository
     *
     * @return The sensor data repository
     */
    public SensorDataRepository getRepository() {
        return this.repository;
    }

    /**
     * Stop getting data from the data queue
     */
    public void stop() {
        this.stop = true;
    }
}
