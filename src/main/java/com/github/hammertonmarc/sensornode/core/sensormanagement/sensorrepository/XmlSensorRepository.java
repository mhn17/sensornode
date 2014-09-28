package com.github.hammertonmarc.sensornode.core.sensormanagement.sensorrepository;

import com.github.hammertonmarc.sensornode.core.exception.SensorManagementException;
import com.github.hammertonmarc.sensornode.core.sensormanagement.Sensor;
import com.github.hammertonmarc.sensornode.core.sensormanagement.SensorList;
import com.github.hammertonmarc.sensornode.core.sensormanagement.SensorRepository;
import com.github.hammertonmarc.sensornode.core.sensormanagement.sensor.DummySensor;
import com.github.hammertonmarc.sensornode.core.sensormanagement.sensor.WebCamSensor;
import com.github.hammertonmarc.sensornode.core.sensormanagement.sensor.device.DeviceFactory;
import com.github.hammertonmarc.sensornode.core.sensormanagement.sensor.device.IWebCamDevice;
import org.apache.commons.configuration.HierarchicalConfiguration;

import java.util.List;

/**
 * Repository for the sensors using a XML configuration
 *
 * @author Marc Hammerton
 */
public class XMLSensorRepository implements SensorRepository {

    private SensorList sensorList = null;
    private HierarchicalConfiguration config;
    private DeviceFactory deviceFactory;

    /**
     * Constructor
     * @param config The XML configuration
     */
    public XMLSensorRepository(HierarchicalConfiguration config, DeviceFactory deviceFactory) {
        this.config = config;
        this.deviceFactory = deviceFactory;
    }

    /**
     * Return the active sensors in the sensor list. If the sensor list has not yet been initialised, it is created.
     *
     * @return sensor list with active sensors
     */
    @Override
    public SensorList getActiveSensors() {
        if (this.sensorList == null) {
            this.createSensorList();
        }

        return this.sensorList;
    }

    /**
     * Create the sensor list
     * - read configuration
     * - create sensors based on configuration
     * - add sensors to sensor list
     */
    private void createSensorList() {
        this.sensorList = new SensorList();

        // get web cams
        List<HierarchicalConfiguration> webCams = this.config.configurationsAt("webCam");
        this.createWebCams(webCams);

        // get dummy sensors
        List<HierarchicalConfiguration> dummySensors = this.config.configurationsAt("dummy");
        this.createDummySensors(dummySensors);
    }

    /**
     * Create dummy sensors and add to sensor list
     *
     * @param dummySensors hierarchical configuration list of dummy sensors
     */
    private void createDummySensors(List<HierarchicalConfiguration> dummySensors) {
       for (HierarchicalConfiguration dummy : dummySensors) {
            Sensor sensor = new DummySensor(
                    dummy.getInt("id"),
                    dummy.getString("name")
            );

            this.sensorList.add(sensor);
        }
    }

    /**
     * Create web cams and add to sensor list
     *
     * @param webCams hierarchical configuration list of web cams
     */
    private void createWebCams(List<HierarchicalConfiguration> webCams) {
        for (HierarchicalConfiguration webCam : webCams) {
            try {
                IWebCamDevice device = this.deviceFactory.getWebCamDevice(
                        webCam.getString("devicePath"),
                        webCam.getInt("width"),
                        webCam.getInt("height"),
                        webCam.getInt("channel"));

                Sensor sensor = new WebCamSensor(webCam.getInt("id"), webCam.getString("name"), device);
                this.sensorList.add(sensor);
            } catch (SensorManagementException e) {
                System.out.println("Could not add web cam sensor to list:");
                System.out.println(e.getMessage());
            }
        }
    }
}
