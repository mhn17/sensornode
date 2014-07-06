package com.github.hammertonmarc.sensornode.sensordatamanagement.mongodb;

import com.github.hammertonmarc.sensornode.sensordatamanagement.SensorData;
import com.github.hammertonmarc.sensornode.sensordatamanagement.SensorDataRepository;
import com.mongodb.*;

import java.net.UnknownHostException;
import java.util.ArrayList;

/**
 * Created by marc on 14.06.14.
 */
public class MongoDbSensorDataRepository implements SensorDataRepository {

    private DBCollection collection;

    public MongoDbSensorDataRepository() {
        try {
            MongoClient mongoClient = new MongoClient( "localhost" , 27017 );
            DB db = mongoClient.getDB("sensorNode");
            this.collection = db.getCollection("sensorData");
        } catch (UnknownHostException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void add(SensorData sensorData) {
        BasicDBObject data = new BasicDBObject("id", sensorData.getSensorId())
                .append("name", sensorData.getSensorName())
                .append("timestamp", sensorData.getTimestamp())
                .append("data", sensorData.getData());
        this.collection.insert(data);
    }

    @Override
    public ArrayList<SensorData> findBySensorId(int sensorId) {
        ArrayList<SensorData> sensorDataList = new ArrayList<SensorData>();

        BasicDBObject query = new BasicDBObject("id", sensorId);
        DBCursor cursor = this.collection.find(query);

        while(cursor.hasNext()) {
            BasicDBObject data = (BasicDBObject) cursor.next();
            SensorData sensorData = new SensorData((int) data.getInt("id") , data.getString("name"),
                    (long) data.getLong("timestamp"), (byte[]) data.get("data"));
            sensorDataList.add(sensorData);
        }

        return sensorDataList;
    }
}
