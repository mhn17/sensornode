package de.hammerton.sensornode.server;

import com.sun.jersey.api.container.httpserver.HttpServerFactory;

import java.io.IOException;

public class HttpServer implements Server {

    // ToDo refactor base URI
    static final String BASE_URI = "http://localhost:9999/sensornode";

    com.sun.net.httpserver.HttpServer server = null;

    public HttpServer() {
        try {
            server = HttpServerFactory.create(BASE_URI);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void start() {
        server.start();
    }

    @Override
    public void stop() {
        server.stop(0);
    }

    @Override
    public void run() {
        this.start();
    }
}
