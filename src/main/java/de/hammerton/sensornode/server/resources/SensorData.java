package de.hammerton.sensornode.server.resources;


import de.hammerton.sensornode.core.sensordatamanagement.SensorDataManagementException;
import de.hammerton.sensornode.core.sensordatamanagement.SensorDataRepository;
import de.hammerton.sensornode.core.sensordatamanagement.SensorDataRepositoryFactory;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import java.util.ArrayList;

@Path("/sensorData")
public class SensorData {

    private SensorDataRepository sensorDataRepository = null;

    public SensorData() {
        try {
            this.sensorDataRepository = SensorDataRepositoryFactory.getRepository();
        } catch (SensorDataManagementException e) {
            e.printStackTrace();
        }
    }

    @GET
    @Path("/")
    @Produces(MediaType.APPLICATION_JSON)
    public ArrayList<de.hammerton.sensornode.core.sensordatamanagement.SensorData> list() {
        return this.sensorDataRepository.find();
    }

}
