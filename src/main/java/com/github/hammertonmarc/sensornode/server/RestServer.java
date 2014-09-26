package com.github.hammertonmarc.sensornode.server;

import com.sun.jersey.api.container.httpserver.HttpServerFactory;
import com.sun.net.httpserver.HttpServer;

import java.io.IOException;

/**
 * Rest server for accessing the sensor data
 *
 * @author Marc Hammerton
 */
public class RestServer implements Server {

    private HttpServer httpServer = null;

    /**
     * Constructor
     *  - initialise http server
     */
    public RestServer() {
        try {
            this.httpServer = HttpServerFactory.create("http://localhost:8080/sensornode");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Start the server
     */
    public void start() {
        this.httpServer.start();
    }

    /**
     * @see Server#stop()
     */
    @Override
    public void stop() {
        this.httpServer.stop(0);
    }

    /**
     * Call the start method for starting the server
     */
    @Override
    public void run() {
        this.start();
    }
}
