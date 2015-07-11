package de.hammerton.sensornode.core.sensormanagement.sensor.device;

/**
 * @author Marc Hammerton
 */
public interface IDevice {

    /**
     * Release the device and all it's dependencies
     */
    void release();

}
