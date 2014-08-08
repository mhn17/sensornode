package com.github.hammertonmarc.sensornode.core.sensormanagement;

import com.github.hammertonmarc.sensornode.core.sensordatamanagement.SensorData;
import com.github.hammertonmarc.sensornode.core.sensordatamanagement.SensorDataQueue;

import java.util.concurrent.BlockingQueue;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * Created by marc on 17.05.14.
 */
@XmlRootElement
public abstract class Sensor implements Runnable {

    protected int id, captureInterval;
    protected String name;
    protected BlockingQueue<SensorData> sensorDataQueue;
    protected byte[] data = null;

    public Sensor(int id, String name) {
        this(id, name, 1);
    }

    public Sensor(int id, String name, int captureInterval) {
        this.id = id;
        this.name = name;
        this.captureInterval = captureInterval;
        this.sensorDataQueue = SensorDataQueue.getInstance();
    }

    public abstract void close();

    public abstract void startCapturing();

    public int getId() {
        return id;
    }

    public String getName() {
        return this.name;
    }

    public byte[] getData() {
        return this.data;
    }

    public void setData(byte[] data) {
        this.data = data;

        if (this.sensorDataQueue != null) {
            SensorData sensorData = new SensorData(this.id, this.name, this.data);
            try {
                this.sensorDataQueue.put(sensorData);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public void run() {
        this.startCapturing();
    }

}
