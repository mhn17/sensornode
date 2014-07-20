package com.github.hammertonmarc.sensornode.core.sensordatamanagement;

import java.util.ArrayList;

/**
 * Created by marc on 14.06.14.
 */
public interface SensorDataRepository {

    public void add(SensorData sensorData);

    public ArrayList<SensorData> findBySensorId(int sensorId);
}
