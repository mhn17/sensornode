package de.hammerton.sensornode.core.sensordatamanagement.repository;

import com.couchbase.lite.*;
import de.hammerton.sensornode.core.sensordatamanagement.SensorData;
import de.hammerton.sensornode.core.sensordatamanagement.SensorDataRepository;

import java.util.*;

/**
 * Sensor data repository using Couchbase Lite
 *
 * @author Marc Hammerton
 */
public class CouchbaseLiteDbSensorDataRepository implements SensorDataRepository {

    private Database db = null;

    /**
     * Constructor
     *  - set the db
     *
     *  @param db The mongoDb db for sensor data
     */
    public CouchbaseLiteDbSensorDataRepository(Database db) {
        this.db = db;
    }

    @Override
    public void add(SensorData sensorData) {
        Map<String, Object> properties = new HashMap<>();
        properties.put("sensorId", sensorData.getSensorId());
        properties.put("timestamp", sensorData.getTimestamp());
        properties.put("data", sensorData.getData());

        Document document = this.db.getDocument(sensorData.getId().toString());
        try {
            document.putProperties(properties);
        } catch (CouchbaseLiteException e) {
            e.printStackTrace();
        }
    }

    @Override
    public ArrayList<SensorData> find() {
        ArrayList<SensorData> sensorDataList = new ArrayList<>();
        Query query = this.db.createAllDocumentsQuery();

        QueryEnumerator result = null;
        try {
            result = query.run();
        } catch (CouchbaseLiteException e) {
            e.printStackTrace();
        }

        for (Iterator<QueryRow> it = result; it != null && it.hasNext(); ) {
            QueryRow row = it.next();
            sensorDataList.add(this.getSensorDataFromDocument(row.getDocument()));
        }

        return sensorDataList;
    }

    @Override
    public ArrayList<SensorData> findBySensorId(int sensorId) {
        ArrayList<SensorData> sensorDataList = new ArrayList<>();
        Query query = this.db.getView("sensorIdsView").createQuery();

        QueryEnumerator result = null;
        try {
            result = query.run();
        } catch (CouchbaseLiteException e) {
            e.printStackTrace();
        }

        for (Iterator<QueryRow> it = result; it != null && it.hasNext(); ) {
            QueryRow row = it.next();
            sensorDataList.add(this.getSensorDataFromDocument(row.getDocument()));
        }

        return sensorDataList;
    }

    @Override
    public void remove(UUID id) {
        Document document = this.db.getDocument(id.toString());
        try {
            document.delete();
        } catch (CouchbaseLiteException e) {
            e.printStackTrace();
        }
    }

    /**
     * Return SensorData object based on a document
     *
     * @param document The document with the data
     * @return The SensorData object
     */
    private SensorData getSensorDataFromDocument(Document document) {
        return new SensorData(
                UUID.fromString(document.getProperty("_id").toString()),
                (int) document.getProperty("sensorId"),
                Long.valueOf(document.getProperty("timestamp").toString()),
                document.getProperty("data").toString().getBytes());
    }
}
