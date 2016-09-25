package de.hammerton.sensornode.server.resources;


import de.hammerton.sensornode.core.sensordatamanagement.SensorDataManagementException;
import de.hammerton.sensornode.core.sensordatamanagement.SensorDataRepository;
import de.hammerton.sensornode.core.sensordatamanagement.SensorDataRepositoryFactory;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.util.ArrayList;
import java.util.UUID;

@Path("/sensorData")
public class SensorData {

    private SensorDataRepository sensorDataRepository = null;

    /**
     * Constructor
     */
    public SensorData() {
        try {
            this.sensorDataRepository = SensorDataRepositoryFactory.getRepository();
        } catch (SensorDataManagementException e) {
            e.printStackTrace();
        }
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public ArrayList<de.hammerton.sensornode.core.sensordatamanagement.SensorData> list() {
        return this.sensorDataRepository.find();
    }

    @DELETE
    @Path("/{id}")
    public void delete(@PathParam("id") String id) {
        try {
            this.sensorDataRepository.remove(UUID.fromString(id));
        } catch (SensorDataManagementException e) {
            e.printStackTrace();
        }
    }

    /**
     * Set the SensorDataRepository
     *
     * @param sensorDataRepository The SensorDataRepository
     */
    public void setSensorDataRepository(SensorDataRepository sensorDataRepository) {
        this.sensorDataRepository = sensorDataRepository;
    }
}
