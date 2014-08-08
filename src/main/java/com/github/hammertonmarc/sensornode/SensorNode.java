package com.github.hammertonmarc.sensornode;

import com.github.hammertonmarc.sensornode.core.sensordatamanagement.SensorDataManager;
import com.github.hammertonmarc.sensornode.core.sensormanagement.SensorManager;
import com.github.hammertonmarc.sensornode.server.RestServer;
import com.github.hammertonmarc.sensornode.server.Server;

/**
 * Created by marc on 17.05.14.
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
