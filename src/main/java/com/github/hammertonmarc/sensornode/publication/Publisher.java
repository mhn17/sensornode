package com.github.hammertonmarc.sensornode.publication;

/**
 * Created by marc on 18.05.14.
 */
public interface Publisher extends Runnable {

    public void publish(String id, byte[] data);
}
