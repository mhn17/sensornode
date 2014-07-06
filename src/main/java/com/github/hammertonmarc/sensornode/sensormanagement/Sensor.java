package com.github.hammertonmarc.sensornode.sensormanagement;

import com.github.hammertonmarc.sensornode.sensordatamanagement.SensorData;

import java.util.concurrent.BlockingQueue;

/**
 * Created by marc on 17.05.14.
 */
public abstract class Sensor implements Runnable {

    protected int id, captureInterval;
    protected String name;
    protected int type;
    protected BlockingQueue<SensorData> sensorDataQueue;
    protected byte[] data;
    public static int DATA_TYPE_JPEG = 0;

    public Sensor(int id, String name) {
        this(id, name, 1);
    }

    public Sensor(int id, String name, int captureInterval) {
        this.id = id;
        this.name = name;
        this.captureInterval = captureInterval;
    }

    public abstract void close();

    public abstract void startCapturing();

    public int getId() {
        return id;
    }

    public String getName() {
        return this.name;
    }

    public int getType() {
        return this.type;
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

    public void setSensorDataQueue(BlockingQueue<SensorData> sensorDataQueue) {
        this.sensorDataQueue = sensorDataQueue;
    }

    @Override
    public void run() {
        this.startCapturing();
    }

}
