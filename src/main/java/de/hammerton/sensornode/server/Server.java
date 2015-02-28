package de.hammerton.sensornode.server;


public interface Server extends Runnable {

    /**
     * Start the server
     */
    public void start();

    /**
     * Stop the server
     */
    public void stop();

}
