package de.hammerton.sensornode.core.sensordatamanagement.repository;

import de.hammerton.sensornode.core.sensordatamanagement.SensorData;
import jetbrains.exodus.entitystore.*;
import org.apache.commons.configuration.ConfigurationException;
import org.apache.commons.configuration.SubnodeConfiguration;
import org.apache.commons.configuration.XMLConfiguration;
import org.junit.*;
import org.junit.runner.RunWith;
import org.mockito.runners.MockitoJUnitRunner;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.UUID;

@RunWith(MockitoJUnitRunner.class)
public class XodusDbSensorDataRepositoryTest {

    private static String configurationFile = "config.xml";
    private static String dbFile = "";

    private PersistentEntityStore entityStore = null;
    private XodusDbSensorDataRepository repository = null;

    @BeforeClass
    public static void init() throws ConfigurationException {
        XMLConfiguration config = new XMLConfiguration(configurationFile);
        SubnodeConfiguration dbConfig = config.configurationAt("database");
        dbFile = dbConfig.getString("file");
    }

    @Before
    public void setUp() throws Exception {
        this.entityStore = PersistentEntityStores.newInstance(dbFile);
        this.repository = new XodusDbSensorDataRepository(entityStore);
    }

    @After
    public void tearDown() throws Exception {
        this.repository = null;
        this.entityStore.clear();
        this.entityStore.close();
    }

    @Test
    public void testAdd() throws Exception {
        UUID id = UUID.randomUUID();
        SensorData sensorData = new SensorData(id, 5, 1, new byte[4]);
        this.repository.add(sensorData);

        this.entityStore.executeInReadonlyTransaction(txn -> {
            final EntityIterable filteredSensorData = txn.find(XodusDbSensorDataRepository.SENSOR_DATA_ENTITY_NAME,
                    "sensorDataId", id.toString());
            Assert.assertEquals(1, filteredSensorData.size());
        });
    }

    @Test
    public void testFind() throws Exception {
        this.entityStore.executeInTransaction(txn -> {
            // create more entities than the default limit value to check the default limit is returned
            int limit = XodusDbSensorDataRepository.DEFAULT_LIMIT + 2;
            for(int i=1; i<=limit; i++) {
                this.createEntity(txn, UUID.randomUUID(), i, 1, new byte[4]);
            }
        });

        ArrayList<SensorData> sensorDataList = this.repository.find();

        Assert.assertEquals(XodusDbSensorDataRepository.DEFAULT_LIMIT, sensorDataList.size());
        Assert.assertSame(SensorData.class, sensorDataList.get(0).getClass());
        Assert.assertEquals(1, sensorDataList.get(0).getSensorId());
        Assert.assertEquals(2, sensorDataList.get(1).getSensorId());
    }

    @Test
    public void testFindWithOffsetAndLimit() throws Exception {
        this.entityStore.executeInTransaction(txn -> {
            // create more entities than the default limit value to check the default limit is returned
            int limit = 10;
            for(int i=1; i<=limit; i++) {
                this.createEntity(txn, UUID.randomUUID(), i, 1, new byte[4]);
            }
        });

        ArrayList<SensorData> sensorDataList = this.repository.find(3, 3);

        Assert.assertEquals(3, sensorDataList.size());
        Assert.assertSame(SensorData.class, sensorDataList.get(0).getClass());
        Assert.assertEquals(4, sensorDataList.get(0).getSensorId());
        Assert.assertEquals(5, sensorDataList.get(1).getSensorId());
        Assert.assertEquals(6, sensorDataList.get(2).getSensorId());
    }

    @Test
    public void testFindBySensorId() throws Exception {
        this.entityStore.executeInTransaction(txn -> {
            this.createEntity(txn, UUID.randomUUID(), 5, 1, new byte[4]);
            this.createEntity(txn, UUID.randomUUID(), 6, 1, new byte[4]);
        });

        ArrayList<SensorData> sensorDataList = this.repository.findBySensorId(5, 0, 10);
        Assert.assertEquals(1, sensorDataList.size());
        Assert.assertSame(SensorData.class, sensorDataList.get(0).getClass());
        Assert.assertEquals(5, sensorDataList.get(0).getSensorId());
    }

    @Test
    public void testRemove() throws Exception {
        UUID id = UUID.randomUUID();

        this.entityStore.executeInTransaction(txn -> {
            this.createEntity(txn, id, 5, 1, new byte[4]);
            this.createEntity(txn, UUID.randomUUID(), 6, 1, new byte[4]);
        });

        this.repository.remove(id);

        this.entityStore.executeInReadonlyTransaction(txn -> {
            final EntityIterable filteredSensorData = txn.find(XodusDbSensorDataRepository.SENSOR_DATA_ENTITY_NAME,
                    "sensorDataId", id.toString());
            Assert.assertEquals(0, filteredSensorData.size());
        });
    }

    /**
     * Helper for creating an Entity
     *
     * @param id The sensor data ID
     * @param sensorId The sensor ID
     * @param timestamp The timestamp
     * @param data The data
     * @return The newly created Entity
     */
    private Entity createEntity(StoreTransaction txn, UUID id, int sensorId, long timestamp, byte[] data) {
        Entity entity = txn.newEntity(XodusDbSensorDataRepository.SENSOR_DATA_ENTITY_NAME);
        entity.setProperty("sensorDataId", id.toString());
        entity.setProperty("sensorId", sensorId);
        entity.setProperty("timestamp", timestamp);
        entity.setBlobString("data", Arrays.toString(data));

        return entity;
    }
}