package com.github.hammertonmarc.sensornode.server.resources;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;

/**
 * Created by marc on 06.07.14.
 */
@Path( "sensors" )
public class SensorResource {
    @GET
    @Produces( MediaType.APPLICATION_JSON)
    public String getSensorList()
    {
        return "Works!";
    }
}
