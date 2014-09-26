package com.github.hammertonmarc.sensornode.core.sensormanagement.sensorrepository;

import com.github.hammertonmarc.sensornode.core.exceptions.SensorManagementException;
import com.github.hammertonmarc.sensornode.core.sensormanagement.Sensor;
import com.github.hammertonmarc.sensornode.core.sensormanagement.SensorList;
import com.github.hammertonmarc.sensornode.core.sensormanagement.SensorRepository;
import com.github.hammertonmarc.sensornode.core.sensormanagement.sensors.Dummy;
import com.github.hammertonmarc.sensornode.core.sensormanagement.sensors.WebCam;
import org.apache.commons.configuration.ConfigurationException;
import org.apache.commons.configuration.HierarchicalConfiguration;
import org.apache.commons.configuration.XMLConfiguration;
import java.util.List;

/**
 * Repository for the sensors using a XML configuration
 *
 * @author Marc Hammerton
 */
public class XmlSensorRepository implements SensorRepository {

    private SensorList sensorList = null;

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

        try {
            HierarchicalConfiguration config = new XMLConfiguration("sensors.xml");

            // get dummy sensors
            List<HierarchicalConfiguration> dummySensors = config.configurationsAt("dummy");
            this.createDummySensors(dummySensors);

            // get web cams
            List<HierarchicalConfiguration> webCams = config.configurationsAt("webCam");
            this.createWebCams(webCams);

        }
        catch(ConfigurationException e) {
            e.printStackTrace();
        }

    }

    /**
     * Create dummy sensors and add to sensor list
     *
     * @param dummySensors hierarchical configuration list of dummy sensors
     */
    private void createDummySensors(List<HierarchicalConfiguration> dummySensors) {
       for (HierarchicalConfiguration dummy : dummySensors) {
            Sensor sensor = new Dummy(
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
                Sensor sensor = new WebCam(
                        webCam.getInt("id"),
                        webCam.getString("name"),
                        webCam.getInt("width"),
                        webCam.getInt("height"),
                        webCam.getInt("channel"),
                        webCam.getString("device")
                );

                this.sensorList.add(sensor);
            } catch (SensorManagementException e) {
                System.out.println("Could not initialise sensor: " + webCam.getString("name"));
            }
        }
    }
}
