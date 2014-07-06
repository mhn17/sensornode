package com.github.hammertonmarc.sensornode.sensormanagement;

import com.github.hammertonmarc.sensornode.sensordatamanagement.SensorDataQueue;
import com.github.hammertonmarc.sensornode.sensormanagement.sensors.WebCam;

/**
 * Created by marc on 17.05.14.
 */
public class SensorManager implements Runnable {
    protected SensorList sensorList;

    public SensorManager() {
        this.sensorList = new SensorList();
        this.createSensors(null);
    }

    public SensorManager(SensorDataQueue sensorDataQueue) {
        this.sensorList = new SensorList();
        this.createSensors(sensorDataQueue);
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

    protected void createSensors(SensorDataQueue sensorDataQueue) {
        Sensor sensor = new WebCam(1, "webcam1", Sensor.DATA_TYPE_JPEG);
        sensor.setSensorDataQueue(sensorDataQueue);
        this.sensorList.add(sensor);

    }
}
