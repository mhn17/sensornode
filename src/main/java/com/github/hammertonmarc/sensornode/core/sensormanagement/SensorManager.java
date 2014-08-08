package com.github.hammertonmarc.sensornode.core.sensormanagement;

/**
 * Created by marc on 17.05.14.
 */
public class SensorManager implements Runnable {

    private static SensorManager instance = new SensorManager();

    protected SensorList sensorList;

    private SensorManager() {
        SensorRepository repository = SensorRepositoryFactory.getRepository();
        this.sensorList = repository.getActiveSensors();
    }

    public static SensorManager getInstance() {
        return instance;
    }

    @Override
    public void run() {
        this.collectData();
    }

    public void collectData() {
        for (Sensor sensor : this.sensorList) {
            sensor.startCapturing();
        }
    }

    public SensorList getSensorList() {
        return this.sensorList;
    }

    public void closeAll() {
        for (Sensor sensor : this.sensorList) {
            sensor.close();
        }
    }
}
