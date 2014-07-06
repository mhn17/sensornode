package com.github.hammertonmarc.sensornode;

import com.github.hammertonmarc.sensornode.sensordatamanagement.SensorDataManager;
import com.github.hammertonmarc.sensornode.sensordatamanagement.SensorDataQueue;
import com.github.hammertonmarc.sensornode.sensormanagement.SensorManager;

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

        while(true) {
            int in = System.in.read();
            if (in != 0) {
                sensorManager.closeAll();
                System.exit(0);
            }
        }

/*
        for (Sensor sensor : sensorManager.getSensorList()) {
            if (sensor.getType() == 0) {
                Publisher publisher = new FilePublisher("JPEG");
            }
        }




        WebCam webcam = new WebCam("webcam");
        Thread.sleep(1000);
        for (int i=0; i<5; i++) {
            byte[] data = webcam.getData();
            if (data != null) {
                publisher.publish("webcam" + i, data);
            } else {
                System.out.println(i + ". no data");
            }
            Thread.sleep(500);
        }
*/

    }

}
