package de.hammerton.sensornode.core.sensormanagement.sensorrepository;

import de.hammerton.sensornode.core.sensormanagement.SensorManagementException;
import de.hammerton.sensornode.core.sensormanagement.Sensor;
import de.hammerton.sensornode.core.sensormanagement.SensorList;
import de.hammerton.sensornode.core.sensormanagement.SensorRepository;
import de.hammerton.sensornode.core.sensormanagement.sensor.BasicSensor;
import de.hammerton.sensornode.core.sensormanagement.sensor.WebCamSensor;
import de.hammerton.sensornode.core.sensormanagement.sensor.device.DeviceFactory;
import de.hammerton.sensornode.core.sensormanagement.sensor.device.IBasicDevice;
import de.hammerton.sensornode.core.sensormanagement.sensor.device.IWebCamDevice;
import de.hammerton.sensornode.core.sensormanagement.sensor.device.adapter.IStringAdapter;
import de.hammerton.sensornode.core.sensormanagement.sensor.device.adapter.TemperatureStringAdapter;
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

        // get basic sensors
        List<HierarchicalConfiguration> basicSensors = this.config.configurationsAt("basic");
        this.createBasicSensors(basicSensors);
    }

    /**
     * Create basic sensors and add to sensor list
     *
     * @param basicConfigurations hierarchical configuration list of dummy sensors
     */
    private void createBasicSensors(List<HierarchicalConfiguration> basicConfigurations) {
        for (HierarchicalConfiguration basicSensor : basicConfigurations) {
            IBasicDevice device = this.createBasicDevice(basicSensor.configurationAt("device"));

            Sensor sensor;
            if (basicSensor.containsKey("captureInterval")) {
                sensor = new BasicSensor(
                        basicSensor.getInt("id"),
                        basicSensor.getString("name"),
                        basicSensor.getInt("captureInterval"),
                        device
                );
            }
            else {
                sensor = new BasicSensor(
                        basicSensor.getInt("id"),
                        basicSensor.getString("name"),
                        device
                );

            }

            this.sensorList.add(sensor);
        }
    }

    private IBasicDevice createBasicDevice(HierarchicalConfiguration deviceConfiguration) {
        IBasicDevice device = null;
        String type = deviceConfiguration.getRoot().getAttributes("type").get(0).getValue().toString();

        switch (type) {
            case "file":
                IStringAdapter adapter = this.createStringAdapter(
                        deviceConfiguration.configurationAt("adapter")
                );

                try {
                    device = this.deviceFactory.getFileDevice(
                            deviceConfiguration.getString("path"),
                            adapter
                    );
                } catch (SensorManagementException e) {
                    System.out.println("Could not add basic file sensor to list:");
                    System.out.println(e.getMessage());
                }

                break;
            case "dummy":
                device = this.deviceFactory.getDummyDevice();
                break;
        }

        return device;
    }

    /**
     * Create a string adapter
     *
     * @param adapterConfiguration The adapter configuration
     * @return A new string adapter
     */
    private IStringAdapter createStringAdapter(HierarchicalConfiguration adapterConfiguration) {
        String type = adapterConfiguration.getRoot().getAttributes("type").get(0).getValue().toString();

        switch (type) {
            case "temperature":
                return new TemperatureStringAdapter();
            default:
                return null;
        }
    }

    /**
     * Create web cams and add to sensor list
     *
     * @param webCamConfigurations hierarchical configuration list of web cams
     */
    private void createWebCams(List<HierarchicalConfiguration> webCamConfigurations) {
        for (HierarchicalConfiguration webCam : webCamConfigurations) {
            try {
                IWebCamDevice device = this.deviceFactory.getWebCamDevice(
                        webCam.getString("devicePath"),
                        webCam.getInt("width"),
                        webCam.getInt("height"),
                        webCam.getInt("channel"));

                Sensor sensor;
                if (webCam.containsKey("captureInterval")) {
                    sensor = new WebCamSensor(
                            webCam.getInt("id"),
                            webCam.getString("name"),
                            webCam.getInt("captureInterval"),
                            device
                    );
                }
                else {
                    sensor = new WebCamSensor(
                            webCam.getInt("id"),
                            webCam.getString("name"),
                            device
                    );

                }

                this.sensorList.add(sensor);
            } catch (SensorManagementException e) {
                System.out.println("Could not add web cam sensor to list:");
                System.out.println(e.getMessage());
            }
        }
    }
}
