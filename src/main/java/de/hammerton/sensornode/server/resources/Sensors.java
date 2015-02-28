package de.hammerton.sensornode.server.resources;

import de.hammerton.sensornode.core.sensormanagement.Sensor;
import de.hammerton.sensornode.core.sensormanagement.SensorManager;

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
    @Path("/")
    @Produces(MediaType.APPLICATION_JSON)
    public ArrayList<Sensor> list() {
        return this.sensorManager.getSensorList();
    }

    @GET
    @Path("/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Sensor get(@PathParam("id") long id) {
        for (Sensor sensor : this.sensorManager.getSensorList()) {
            if (sensor.getId() == id) {
                return sensor;
            }
        }

        return null;
    }

}
