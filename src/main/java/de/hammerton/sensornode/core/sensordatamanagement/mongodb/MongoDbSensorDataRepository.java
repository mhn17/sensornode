package de.hammerton.sensornode.core.sensordatamanagement.mongodb;

import de.hammerton.sensornode.core.sensordatamanagement.SensorData;
import de.hammerton.sensornode.core.sensordatamanagement.SensorDataRepository;
import com.mongodb.*;

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
     *  - set the collection for the mongoDb
     *
     *  @param collection The mongoDb collection for sensor data
     */
    public MongoDbSensorDataRepository(DBCollection collection) {
        this.collection = collection;
    }

    /**
     * Add sensor data to collection
     *
     * @param sensorData The sensor data to be stored
     */
    @Override
    public void add(SensorData sensorData) {
        BasicDBObject data = getBasicDBObject(sensorData);
        this.collection.insert(data);
    }

    /**
     * Find all sensor data
     *
     * @return The sensor data
     */
    @Override
    public ArrayList<SensorData> find() {
        ArrayList<SensorData> sensorDataList = new ArrayList<SensorData>();

        DBCursor cursor = this.collection.find();

        while(cursor.hasNext()) {
            BasicDBObject data = (BasicDBObject) cursor.next();
            SensorData sensorData = getSensorDataFromDBObject(data);
            sensorDataList.add(sensorData);
        }

        return sensorDataList;
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
            SensorData sensorData = getSensorDataFromDBObject(data);
            sensorDataList.add(sensorData);
        }

        return sensorDataList;
    }

    /**
     * Return a BasicDBObject for sensor data
     *
     * @param sensorData The sensor data which should be converted into a BasicDBObject
     * @return A BasicDBObject based on the sensor data
     */
    private BasicDBObject getBasicDBObject(SensorData sensorData) {
        return new BasicDBObject("id", sensorData.getSensorId())
                .append("name", sensorData.getSensorName())
                .append("timestamp", sensorData.getTimestamp())
                .append("data", sensorData.getData());
    }

    /**
     * Return SensorData object based on a DBObject
     *
     * @param data The DBObject with the data
     * @return The SensorData object
     */
    private SensorData getSensorDataFromDBObject(BasicDBObject data) {
        return new SensorData(
                data.getInt("id"),
                data.getString("name"),
                data.getLong("timestamp"),
                (byte[]) data.get("data"));
    }
}
