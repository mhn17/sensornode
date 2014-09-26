package com.github.hammertonmarc.sensornode.core.sensormanagement;

/**
 * Interface for sensor repositories
 *
 * @author Marc Hammerton
 */
public interface SensorRepository {

    /**
     * Returns a sensor list containing the active sensors
     *
     * @return A sensor list with active sensors
     */
    public SensorList getActiveSensors();
}
