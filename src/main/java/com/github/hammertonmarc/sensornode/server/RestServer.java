package com.github.hammertonmarc.sensornode.server;

import com.sun.jersey.api.container.httpserver.HttpServerFactory;
import com.sun.net.httpserver.HttpServer;

import java.io.IOException;

/**
 * Created by marc on 06.07.14.
 */
public class RestServer implements Server {

    private HttpServer httpServer;

    public void start() {
        try {
            this.httpServer = HttpServerFactory.create("http://localhost:8080/sensornode");
            this.httpServer.start();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void stop() {
        this.httpServer.stop(0);
    }

    @Override
    public void run() {
        this.start();
    }
}
