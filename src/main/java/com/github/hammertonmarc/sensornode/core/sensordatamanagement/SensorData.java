package com.github.hammertonmarc.sensornode.core.sensordatamanagement;

import java.util.Calendar;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * Created by marc on 14.06.14.
 */
@XmlRootElement
public class SensorData {
    protected int sensorId;
    protected String sensorName;
    protected long timestamp;
    protected byte[] data;

    public SensorData (int sensorId, String sensorName, byte[] data) {
        this.sensorId = sensorId;
        this.sensorName = sensorName;
        this.data = data;

        Calendar calendar = Calendar.getInstance();
        this.timestamp = calendar.getTime().getTime();
    }

    public SensorData (int sensorId, String sensorName, long timestamp, byte[] data) {
        this.sensorId = sensorId;
        this.sensorName = sensorName;
        this.timestamp = timestamp;
        this.data = data;
    }

    public int getSensorId() {
        return sensorId;
    }

    public String getSensorName() {
        return sensorName;
    }

    public long getTimestamp() {
        return timestamp;
    }

    public byte[] getData() {
        return data;
    }
}
