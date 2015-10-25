package de.hammerton.sensornode.server.resources;

import de.hammerton.sensornode.core.sensordatamanagement.SensorDataManagementException;
import de.hammerton.sensornode.core.sensordatamanagement.SensorDataRepository;
import de.hammerton.sensornode.core.sensordatamanagement.SensorDataRepositoryFactory;
import de.hammerton.sensornode.core.sensordatamanagement.SensorData;
import de.hammerton.sensornode.core.sensormanagement.Sensor;
import de.hammerton.sensornode.core.sensormanagement.SensorManager;
import de.hammerton.sensornode.server.dto.SensorDto;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import java.util.ArrayList;

@Path("/sensors")
public class Sensors {

    private SensorManager sensorManager;

    /**
     * Constructor
     */
    public Sensors() {
        this.sensorManager = SensorManager.getInstance();
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public ArrayList<SensorDto> list() {
        ArrayList<SensorDto> sensors = new ArrayList<>();

        for (Sensor sensor : this.sensorManager.getSensorList()) {
            sensors.add(new SensorDto(sensor));
        }

        return sensors;
    }

    @GET
    @Path("/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public SensorDto get(@PathParam("id") long id) {
        for (Sensor sensor : this.sensorManager.getSensorList()) {
            if (sensor.getId() == id) {
                return new SensorDto(sensor);
            }
        }

        return null;
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/{id}/sensorData")
    public ArrayList<SensorData> listSensorDataBySensorId(@PathParam("id") int sensorId) {
        SensorDataRepository sensorDataRepository;

        try {
            sensorDataRepository = SensorDataRepositoryFactory.getRepository();
        } catch (SensorDataManagementException e) {
            e.printStackTrace();
            return new ArrayList<>();
        }

        return sensorDataRepository.findBySensorId(sensorId);
    }

    /**
     * Set the SensorManager
     *
     * @param sensorManager The SensorManager
     */
    public void setSensorManager(SensorManager sensorManager) {
        this.sensorManager = sensorManager;
    }
}
