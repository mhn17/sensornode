package com.github.hammertonmarc.sensornode.server;

/**
 * Interface for servers
 *
 * @author Marc Hammerton
 */
public interface Server extends Runnable {

    /**
     * Stop the server
     */
    public void stop();
}
