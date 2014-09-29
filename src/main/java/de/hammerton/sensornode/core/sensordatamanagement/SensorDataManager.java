package de.hammerton.sensornode.core.sensordatamanagement;

/**
 * The SensorDataManager is responsible for getting data from the data queue and adding it to the repository
 *
 * @author Marc Hammerton
 */
public class SensorDataManager implements Runnable {

    protected SensorDataQueue sensorDataQueue = null;
    protected SensorDataRepository repository = null;
    protected boolean stop = false;

    /**
     * Initialise the data queue and repository and start managing the data
     */
    @Override
    public void run() {
        try {
            this.sensorDataQueue = SensorDataQueue.getInstance();
            this.repository = SensorDataRepositoryFactory.getRepository();
            this.start();
        } catch (SensorDataManagementException e) {
            System.out.println(e.getMessage());
        }
    }

    /**
     * Start checking the data queue for new data and adding it to the repository
     */
    public void start() throws SensorDataManagementException {
        if (this.sensorDataQueue == null || this.repository == null) {
            throw new SensorDataManagementException("The data queue and the repository need to be initialised before " +
                    "starting the  manager.");
        }

        // get data from queue and keep adding them to the repository until a poison pill is found
        while (!this.stop) {
            try {
                SensorData data = this.sensorDataQueue.take();
                if (data != this.sensorDataQueue.getPoisonPill()) {
                    this.repository.add(data);
                }
                else {
                    this.stop = true;
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * Stop getting data from the data queue
     */
    public void stop() {
        this.sensorDataQueue.add(this.sensorDataQueue.getPoisonPill());
    }

    /**
     * Set the queue for the sensor data
     *
     * @param sensorDataQueue The sensor data queue
     */
    public void setSensorDataQueue(SensorDataQueue sensorDataQueue) {
        this.sensorDataQueue = sensorDataQueue;
    }

    /**
     * Get the data queue
     *
     * @return The sensor data queue
     */
    public SensorDataQueue getSensorDataQueue() {
        return this.sensorDataQueue;
    }

    /**
     * Set the sensor data repository
     *
     * @param repository The sensor data repository
     */
    public void setRepository(SensorDataRepository repository) {
        this.repository = repository;
    }

    /**
     * Return the sensor data repository
     *
     * @return The sensor data repository
     */
    public SensorDataRepository getRepository() {
        return this.repository;
    }
}
