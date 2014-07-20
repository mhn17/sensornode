package com.github.hammertonmarc.sensornode.server.rest.resources;

import com.github.hammertonmarc.sensornode.core.sensordatamanagement.SensorDataRepository;
import com.github.hammertonmarc.sensornode.core.sensordatamanagement.SensorDataRepositoryFactory;
import com.github.hammertonmarc.sensornode.core.sensormanagement.SensorManager;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;

/**
 * Created by marc on 06.07.14.
 */
@Path( "sensors" )
public class SensorResource {

    private SensorManager sensorManager;
    private SensorDataRepository repository;


    public SensorResource() {
        this.sensorManager = SensorManager.getInstance();
        this.repository = SensorDataRepositoryFactory.getRepository();
    }

    @GET
    @Produces( MediaType.APPLICATION_JSON)
    public String getSensorList()
    {
        return "Works!";
    }
}
