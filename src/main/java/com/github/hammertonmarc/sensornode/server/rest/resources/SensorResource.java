package com.github.hammertonmarc.sensornode.server.rest.resources;

import com.github.hammertonmarc.sensornode.core.sensordatamanagement.SensorDataRepository;
import com.github.hammertonmarc.sensornode.core.sensordatamanagement.SensorDataRepositoryFactory;
import com.github.hammertonmarc.sensornode.core.sensormanagement.SensorManager;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;

/**
 * Sensor resource for displaying sensors
 *
 * @author Marc Hammerton
 */
@Path( "sensors" )
public class SensorResource {

    private SensorManager sensorManager;
    private SensorDataRepository repository;

    /**
     * Constructor
     *  - initialise sensor manager and repository
     */
    public SensorResource() {
        this.sensorManager = SensorManager.getInstance();
        this.repository = SensorDataRepositoryFactory.getRepository();
    }

    /**
     * Return the list of sensors
     *
     * @return Json string with sensors
     */
    @GET
    @Produces( MediaType.APPLICATION_JSON)
    public String getSensorList()
    {
        // ToDo return the list of sensors as json string
        return "Works!";
    }
}
