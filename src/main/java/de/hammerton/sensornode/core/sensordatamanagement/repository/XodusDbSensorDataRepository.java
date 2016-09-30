package de.hammerton.sensornode.core.sensordatamanagement.repository;

import de.hammerton.sensornode.core.sensordatamanagement.SensorData;
import de.hammerton.sensornode.core.sensordatamanagement.SensorDataManagementException;
import de.hammerton.sensornode.core.sensordatamanagement.SensorDataRepository;
import jetbrains.exodus.entitystore.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.UUID;

/**
 * Sensor data repository using mongoDb
 *
 * @author Marc Hammerton
 */
public class XodusDbSensorDataRepository implements SensorDataRepository {

    public static final String SENSOR_DATA_ENTITY_NAME = "SensorData";
    public static final String SENSOR_DATA_DEFAULT_SORT_FIELD = "timestamp";

    private final PersistentEntityStore entityStore;

    /**
     * Constructor
     *  - set the entity store for the Xodus database
     *
     *  @param entityStore The entity store for sensor data
     */
    public XodusDbSensorDataRepository(PersistentEntityStore entityStore) {
        this.entityStore = entityStore;
    }

    /**
     * Add sensor data
     *
     * @param sensorData The sensor data to be stored
     */
    @Override
    public void add(SensorData sensorData) {
        entityStore.executeInTransaction(txn -> {
            final Entity entity = txn.newEntity(SENSOR_DATA_ENTITY_NAME);
            entity.setProperty("sensorDataId", sensorData.getId().toString());
            entity.setProperty("sensorId", sensorData.getSensorId());
            entity.setProperty("timestamp", sensorData.getTimestamp());
            entity.setBlobString("data", Arrays.toString(sensorData.getData()));
        });
    }

    /**
     * Find all sensor data limiting to the first 20 records
     *
     * @return The sensor data
     */
    @Override
    public ArrayList<SensorData> find() {
        return this.find(DEFAULT_OFFSET, DEFAULT_LIMIT);
    }

    /**
     * Find all sensor data with offset and limit
     *
     * @param offset Where to start listing the records
     * @param limit How many results should be shown
     * @return The sensor data
     */
    @Override
    public ArrayList<SensorData> find(int offset, int limit) {
        ArrayList<SensorData> sensorDataList = new ArrayList<>();

        entityStore.executeInTransaction(txn -> {
            final EntityIterable allSensorData = txn.sort(SENSOR_DATA_ENTITY_NAME,
                    SENSOR_DATA_DEFAULT_SORT_FIELD, true);

            this.mapResultSetToList(sensorDataList, allSensorData, offset, limit);
        });

        return sensorDataList;
    }

    /**
     * Find sensor data by a sensor ID
     *
     * @param sensorId The sensor ID for which to get the data
     * @param offset Where to start listing the records
     * @param limit How many results should be shown
     * @return The sensor data for the sensor ID
     */
    @Override
    public ArrayList<SensorData> findBySensorId(int sensorId, int offset, int limit) {
        ArrayList<SensorData> sensorDataList = new ArrayList<>();

        entityStore.executeInTransaction(txn -> {
            final EntityIterable filteredSensorData = txn.find(SENSOR_DATA_ENTITY_NAME, "sensorId", sensorId);

            this.mapResultSetToList(sensorDataList, filteredSensorData, offset, limit);
        });

        return sensorDataList;
    }

    /**
     * Remove sensor data from the repository identified by its identifier
     *
     * @param id The sensor data ID
     * @throws SensorDataManagementException When the sensor data could not be removed an exception is thrown
     */
    @Override
    public void remove(UUID id) throws SensorDataManagementException {
        entityStore.executeInTransaction(txn -> {
            // @ToDo make sure only one record is deleted, otherwise inform user about inconsistent data
            final EntityIterable filteredSensorData = txn.find(SENSOR_DATA_ENTITY_NAME, "sensorDataId", id.toString());
            for (Entity sensorData: filteredSensorData) {
                sensorData.delete();
            }
        });
    }

    /**
     * Takes a result set and reduces it to match the offset and limit
     *
     * @param entities The results
     * @param offset Where to start listing the records
     * @param limit How many results should be shown
     */
    private void mapResultSetToList(ArrayList<SensorData> sensorDataList,
                                    EntityIterable entities, int offset, int limit) {
        int counter = 1;
        int offsetEnd = offset + limit;
        for (Entity sensorData: entities) {
            if (counter > offset && counter <= offsetEnd) {
                sensorDataList.add(this.getSensorDataFromEntity(sensorData));
            }

            counter++;
        }
    }

    /**
     * Return SensorData object based on an Entity
     *
     * @param entity The Entity with the data
     * @return The SensorData object
     */
    private SensorData getSensorDataFromEntity(Entity entity) {
        // make sure data is not null
        String data = entity.getBlobString("data");
        if (data == null) {
            data = "";
        }

        return new SensorData(
                UUID.fromString((String) entity.getProperty("sensorDataId")),
                (int) entity.getProperty("sensorId"),
                (long) entity.getProperty("timestamp"),
                data.getBytes());
    }
}
