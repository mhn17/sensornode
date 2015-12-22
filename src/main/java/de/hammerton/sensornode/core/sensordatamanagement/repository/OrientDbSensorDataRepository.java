package de.hammerton.sensornode.core.sensordatamanagement.repository;

import com.orientechnologies.orient.core.db.document.ODatabaseDocumentTx;
import com.orientechnologies.orient.core.iterator.ORecordIteratorClass;
import com.orientechnologies.orient.core.record.impl.ODocument;
import com.orientechnologies.orient.core.sql.OCommandSQL;
import com.orientechnologies.orient.core.sql.query.OSQLSynchQuery;
import de.hammerton.sensornode.core.sensordatamanagement.SensorData;
import de.hammerton.sensornode.core.sensordatamanagement.SensorDataRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * Sensor data repository using orientDb
 *
 * @author Marc Hammerton
 */
public class OrientDbSensorDataRepository implements SensorDataRepository {

    private ODatabaseDocumentTx db;
    private String userName;
    private String password;

    /**
     * Constructor
     *  - set the tx object, the user name and the password
     *
     *  @param db The orientDb tx object
     */
    public OrientDbSensorDataRepository(ODatabaseDocumentTx db, String userName, String password) {
        this.db = db;
        this.userName = userName;
        this.password = password;
    }

    @Override
    public void add(SensorData sensorData) {
        this.db.open(this.userName, this.password);

        ODocument doc = new ODocument("SensorData");
        doc.field("id", sensorData.getId());
        doc.field("sensorId", sensorData.getSensorId());
        doc.field("timestamp", sensorData.getTimestamp());
        doc.field("data", sensorData.getData());

        // save the document
        doc.save();

        this.db.close();
    }

    @Override
    public ArrayList<SensorData> find() {
        ArrayList<SensorData> sensorDataList = new ArrayList<>();
        this.db.open(this.userName, this.password);

        ORecordIteratorClass<ODocument> iterator = this.db.browseClass("SensorData");

        while(iterator.hasNext()) {
            ODocument doc = iterator.next();
            sensorDataList.add(this.createDocumentFromSensorData(doc));
        }

        this.db.close();

        return sensorDataList;
    }

    @Override
    public ArrayList<SensorData> findBySensorId(int sensorId) {
        ArrayList<SensorData> sensorDataList = new ArrayList<>();
        this.db.open(this.userName, this.password);

        List<ODocument> result = this.db.query(
                new OSQLSynchQuery<ODocument>("SELECT id, sensorId, timestamp, data FROM SensorData " +
                        "WHERE sensorId = ?"), sensorId);
        for (ODocument doc : result) {
            sensorDataList.add(this.createDocumentFromSensorData(doc));
        }

        this.db.close();

        return sensorDataList;
    }

    @Override
    public void remove(UUID id) {
        this.db.open(this.userName, this.password);

        // Try to delete
        this.db.command(new OCommandSQL("DELETE FROM SensorData WHERE id = ?")).execute(id.toString());
        this.db.commit();

        this.db.close();
    }

    /**
     * Create a SensorData object based on the data in a document
     *
     * @param document A document containing sensor data
     * @return The SensorData object
     */
    private SensorData createDocumentFromSensorData(ODocument document) {
        return new SensorData(
                UUID.fromString(document.field("id").toString()),
                (Integer) document.field("sensorId"),
                (Long) document.field("timestamp"),
                (byte[]) document.field("data")
        );
    }
}
