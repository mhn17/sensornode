package de.hammerton.sensornode.server.resources;

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

    public void setSensorManager(SensorManager sensorManager) {
        this.sensorManager = sensorManager;
    }
}
