package com.github.hammertonmarc.sensornode.core.sensormanagement.sensorrepository;

import com.github.hammertonmarc.sensornode.core.exceptions.SensorManagementException;
import com.github.hammertonmarc.sensornode.core.sensormanagement.Sensor;
import com.github.hammertonmarc.sensornode.core.sensormanagement.SensorList;
import com.github.hammertonmarc.sensornode.core.sensormanagement.SensorRepository;
import com.github.hammertonmarc.sensornode.core.sensormanagement.sensors.WebCam;
import org.apache.commons.configuration.Configuration;
import org.apache.commons.configuration.ConfigurationException;
import org.apache.commons.configuration.HierarchicalConfiguration;
import org.apache.commons.configuration.XMLConfiguration;
import org.apache.commons.configuration.tree.ConfigurationNode;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * Created by marc on 07.08.14.
 */
public class XmlSensorRepository implements SensorRepository {

    private SensorList sensorList = null;

    @Override
    public SensorList getActiveSensors() {
        if (this.sensorList == null) {
            this.createSensorList();
        }

        return this.sensorList;
    }

    private void createSensorList() {
        this.sensorList = new SensorList();

        try {
            HierarchicalConfiguration config = new XMLConfiguration("sensors.xml");
            List<HierarchicalConfiguration> webCams = config.configurationsAt("webCam");
            this.createWebCams(webCams);

        }
        catch(ConfigurationException e) {
            e.printStackTrace();
        }

    }

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
