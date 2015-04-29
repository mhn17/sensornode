package de.hammerton.sensornode.server;

import com.sun.jersey.api.container.httpserver.HttpServerFactory;

import java.io.IOException;

public class HttpServer implements Server {

    // ToDo refactor base URI
    static final String BASE_URI = "http://localhost:9999/sensornode";

    com.sun.net.httpserver.HttpServer serverInstance = null;

    @Override
    public void start() {
        this.getServerInstance().start();
    }

    @Override
    public void stop() {
        this.serverInstance.stop(0);
    }

    @Override
    public void run() {
        this.start();
    }

    public void setServerInstance(com.sun.net.httpserver.HttpServer serverInstance) {
        this.serverInstance = serverInstance;
    }

    public com.sun.net.httpserver.HttpServer getServerInstance() {
        if (this.serverInstance == null) {
            try {
                this.serverInstance = HttpServerFactory.create(BASE_URI);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        return this.serverInstance;
    }
}
