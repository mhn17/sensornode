package com.github.hammertonmarc.sensornode;

import com.github.hammertonmarc.sensornode.core.sensordatamanagement.SensorDataManager;
import com.github.hammertonmarc.sensornode.core.sensormanagement.SensorManager;
import com.github.hammertonmarc.sensornode.server.RestServer;
import com.github.hammertonmarc.sensornode.server.Server;

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
        Server server = new RestServer();
        new Thread(server).start();

        // wait for user action to stop
//        System.out.println("Sensor node running. Press <enter> to quit");
//        while(true) {
//            int in = System.in.read();
//            // ToDo Change back to 0 and fix problems on windows
//            if (in != 0) {
//                sensorManager.closeAll();
//                server.stop();
//                sensorDataManager.stop();
//                System.exit(0);
//            }
//        }

        // wait for user action to stop
        while(true) {
            int in = System.in.read();
            if (in != 0) {
                sensorManager.closeAll();
                server.stop();
                sensorDataManager.stop();
                System.exit(0);
            }
        }
    }

}
