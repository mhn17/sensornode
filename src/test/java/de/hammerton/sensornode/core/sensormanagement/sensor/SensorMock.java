package de.hammerton.sensornode.core.sensormanagement.sensor;

import de.hammerton.sensornode.core.sensormanagement.Sensor;
import de.hammerton.sensornode.core.sensormanagement.sensor.device.IDevice;

/**
 * Mock for testings the sensors
 *
 * @author Marc Hammerton
 */
public class SensorMock extends Sensor {

    /**
     * @param id The sensor id
     * @param name The name of the sensor
     * @param device The device for this sensor
     */
    public SensorMock(int id, String name, IDevice device) {
        super(id, name, device);
    }

    /**
     * @param id The sensor id
     * @param name The name of the sensor
     * @param captureInterval The capture interval
     * @param dataType The data type
     * @param device The device for this sensor
     */
    public SensorMock(int id, String name, int captureInterval, String dataType, IDevice device) {
        super(id, name, captureInterval, dataType, device);
    }

    @Override
    public void startCapturing() {

    }
}
