package de.hammerton.sensornode.server.dto;

import de.hammerton.sensornode.core.sensormanagement.Sensor;

public class SensorDto {

    private int id, captureInterval;
    private String name;

    public SensorDto(Sensor sensor) {
        this.id = sensor.getId();
        this.name = sensor.getName();
        this.captureInterval = sensor.getCaptureInterval();
    }

    public int getId() {
        return this.id;
    }

    public int getCaptureInterval() {
        return this.captureInterval;
    }

    public String getName() {
        return this.name;
    }
}
