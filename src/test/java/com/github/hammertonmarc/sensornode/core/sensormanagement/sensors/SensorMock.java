package com.github.hammertonmarc.sensornode.core.sensormanagement.sensors;

import com.github.hammertonmarc.sensornode.core.sensormanagement.Sensor;

/**
 * Mock for testings the sensors
 *
 * @author Marc Hammerton
 */
public class SensorMock extends Sensor {

    /**
     * @param id The sensor id
     * @param name The name of the sensor
     */
    public SensorMock(int id, String name) {
        super(id, name);
    }

    /**
     * @param id The sensor id
     * @param name The name of the sensor
     * @param captureInterval The interval for caputuring data
     */
    public SensorMock(int id, String name, int captureInterval) {
        super(id, name, captureInterval);
    }


    @Override
    public void close() {

    }

    @Override
    public void startCapturing() {

    }
}
