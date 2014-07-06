package com.github.hammertonmarc.sensornode;

import com.github.hammertonmarc.sensornode.sensordatamanagement.SensorDataManager;
import com.github.hammertonmarc.sensornode.sensordatamanagement.SensorDataQueue;
import com.github.hammertonmarc.sensornode.sensormanagement.SensorManager;
import com.github.hammertonmarc.sensornode.server.RestServer;
import com.github.hammertonmarc.sensornode.server.Server;

/**
 * Created by marc on 17.05.14.
 */
public class SensorNode {

    public static void main(String[] args) throws Exception {
        // create SensorDataQueue
        SensorDataQueue sensorDataQueue = new SensorDataQueue(1024);

        // create manager
        SensorDataManager sensorDataManager = new SensorDataManager(sensorDataQueue);
        SensorManager sensorManager = new SensorManager(sensorDataQueue);

        // create threads for manager
        new Thread(sensorDataManager).start();
        new Thread(sensorManager).start();

        // create server
        Server server = new RestServer();
        new Thread(server).start();

        while(true) {
            int in = System.in.read();
            if (in != 0) {
                sensorManager.closeAll();
                server.stop();
                System.exit(0);
            }
        }

    }

}
