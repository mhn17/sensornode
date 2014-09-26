package com.github.hammertonmarc.sensornode.core.sensordatamanagement.mongodb;

import com.github.hammertonmarc.sensornode.core.sensordatamanagement.SensorData;
import com.github.hammertonmarc.sensornode.core.sensordatamanagement.SensorDataRepository;
import com.mongodb.*;

import java.net.UnknownHostException;
import java.util.ArrayList;

/**
 * Sensor data repository using mongoDb
 *
 * @author Marc Hammerton
 */
public class MongoDbSensorDataRepository implements SensorDataRepository {

    private DBCollection collection = null;

    /**
     * Constructor
     *  - initialise mongo client
     *  - get database and collection
     */
    public MongoDbSensorDataRepository() {
        try {
            MongoClient mongoClient = new MongoClient( "localhost" , 27017 );
            DB db = mongoClient.getDB("sensorNode");
            this.collection = db.getCollection("sensorData");
        } catch (UnknownHostException e) {
            e.printStackTrace();
        }
    }

    /**
     * Add sensor data to collection
     *
     * @param sensorData The sensor data to be stored
     */
    @Override
    public void add(SensorData sensorData) {
        BasicDBObject data = new BasicDBObject("id", sensorData.getSensorId())
                .append("name", sensorData.getSensorName())
                .append("timestamp", sensorData.getTimestamp())
                .append("data", sensorData.getData());
        this.collection.insert(data);
    }

    /**
     * Find sensor data by a sensor ID
     *
     * @param sensorId The sensor ID for which to get the data
     * @return The sensor data for the sensor ID
     */
    @Override
    public ArrayList<SensorData> findBySensorId(int sensorId) {
        ArrayList<SensorData> sensorDataList = new ArrayList<SensorData>();

        BasicDBObject query = new BasicDBObject("id", sensorId);
        DBCursor cursor = this.collection.find(query);

        while(cursor.hasNext()) {
            BasicDBObject data = (BasicDBObject) cursor.next();
            SensorData sensorData = new SensorData(data.getInt("id") , data.getString("name"),
                    data.getLong("timestamp"), (byte[]) data.get("data"));
            sensorDataList.add(sensorData);
        }

        return sensorDataList;
    }
}
