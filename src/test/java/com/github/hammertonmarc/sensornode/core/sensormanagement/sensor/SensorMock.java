package com.github.hammertonmarc.sensornode.core.sensormanagement.sensor;

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

    @Override
    public void close() {

    }

    @Override
    public void startCapturing() {

    }
}
