package de.hammerton.sensornode;

import de.hammerton.sensornode.core.sensordatamanagement.SensorDataManager;
import de.hammerton.sensornode.core.sensormanagement.SensorManager;
import de.hammerton.sensornode.server.HttpServer;
import de.hammerton.sensornode.server.Server;

/**
 * Main class for the sensor node
 *
 * @author Marc Hammerton
 */
public class SensorNode {

    public static void main(String[] args) throws Exception {
        // create manager
        SensorDataManager sensorDataManager = new SensorDataManager();
        SensorManager sensorManager = SensorManager.getInstance();

        // create threads for manager
        new Thread(sensorDataManager).start();
        new Thread(sensorManager).start();

        // start server
        Server server = new HttpServer();
        new Thread(server).start();

        // wait for user action to stop
        System.out.println("########################");
        System.out.println("# SensorNode           #");
        System.out.println("########################");
    }

}
