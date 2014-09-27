package com.github.hammertonmarc.sensornode.core.sensormanagement;

import com.github.hammertonmarc.sensornode.core.exceptions.SensorManagementException;

/**
 * Manager for the active sensors.
 * The manager gets the list of active sensors and instructs the sensors to start
 * collecting data. When the program is stopped, it shuts down the sensors.
 *
 * @author Marc Hammerton
 */
public class SensorManager implements Runnable {

    /**
     * Instance of the sensor manager
     */
    private static SensorManager instance = new SensorManager();

    /**
     * List of all active sensors
     */
    private SensorList sensorList = null;

    private SensorRepository sensorRepository = null;

    /**
     * Private constructor. Use getInstance to get an instance of the sensor
     * manager.
     */
    private SensorManager() {
        try {
            this.sensorRepository = SensorRepositoryFactory.getRepository();
        } catch (SensorManagementException e) {
            e.printStackTrace();
        }
    }

    /**
     * Return an instance of the sensor manager
     * @return SensorManager
     */
    public static SensorManager getInstance() {
        return instance;
    }

    /**
     * Start the sensor manager
     * - start collecting data
     */
    @Override
    public void run() {
        try {
            this.sensorList = this.sensorRepository.getActiveSensors();
            this.collectData();
        } catch (SensorManagementException e) {
            System.out.println(e.getMessage());
        }
    }

    /**
     * Instruct all sensors from the sensor list to start collecting data
     */
    public void collectData() throws SensorManagementException {
        if (this.sensorList == null) {
            throw new SensorManagementException("The sensor list needs to be initialised before " +
                    "starting the  manager.");
        }
        for (Sensor sensor : this.sensorList) {
            new Thread(sensor).start();
        }
    }

    /**
     * Set the sensor list
     * @param sensorList List of active sensors
     */
    public void setSensorList(SensorList sensorList) {
        this.sensorList = sensorList;
    }

    /**
     * Return the current sensor list with all active sensors
     * @return SensorList
     */
    public SensorList getSensorList() {
        return this.sensorList;
    }

    /**
     * Close all active sensors from the sensor list
     */
    public void closeAll() {
        for (Sensor sensor : this.sensorList) {
            sensor.close();
        }
    }
}
