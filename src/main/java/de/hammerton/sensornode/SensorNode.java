package de.hammerton.sensornode;

import de.hammerton.sensornode.core.sensordatamanagement.SensorDataManager;
import de.hammerton.sensornode.core.sensormanagement.SensorManager;
import de.hammerton.sensornode.server.HttpServer;
import de.hammerton.sensornode.server.Server;

import java.util.Scanner;

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
        System.out.println("Press <q> to quit");

        Scanner scanner = new Scanner(System.in);
        while(true) {
            String line = scanner.nextLine();
            if (line.equals("q")) {
                System.out.println("Shutting down SensorNode");
                server.stop();
                sensorManager.closeAll();
                sensorDataManager.stop();
                System.exit(0);
            }
        }
    }

}
