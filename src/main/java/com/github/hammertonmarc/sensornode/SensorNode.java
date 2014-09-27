package com.github.hammertonmarc.sensornode;

import com.github.hammertonmarc.sensornode.core.sensordatamanagement.SensorDataManager;
import com.github.hammertonmarc.sensornode.core.sensormanagement.SensorManager;
import com.github.hammertonmarc.sensornode.server.RestServer;
import com.github.hammertonmarc.sensornode.server.Server;

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

        // create server
//        Server server = new RestServer();
//        new Thread(server).start();

        // wait for user action to stop
        System.out.println("Press <q> to exit");
        Scanner scanner = new Scanner(System.in);
        while(true) {
            String line = scanner.nextLine();
            if (line.equals("q")) {
                System.out.println("shutting down");
                sensorManager.closeAll();
//                server.stop();
                sensorDataManager.stop();
                System.exit(0);
            }
        }
    }

}
