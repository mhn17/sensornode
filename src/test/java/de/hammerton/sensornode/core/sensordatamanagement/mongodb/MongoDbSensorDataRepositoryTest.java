package de.hammerton.sensornode.core.sensordatamanagement.mongodb;

import de.hammerton.sensornode.core.sensordatamanagement.SensorData;
import com.mongodb.BasicDBObject;
import com.mongodb.DBCollection;
import com.mongodb.DBCursor;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.runners.MockitoJUnitRunner;

import java.util.ArrayList;

@RunWith(MockitoJUnitRunner.class)
public class MongoDbSensorDataRepositoryTest {

    @Mock
    private DBCollection collection = null;

    private MongoDbSensorDataRepository repository = null;

    @Before
    public void setUp() throws Exception {
        this.repository = new MongoDbSensorDataRepository(collection);
    }

    @After
    public void tearDown() throws Exception {
        this.repository = null;
    }

    @Test
    public void testAdd() throws Exception {
        SensorData sensorData = Mockito.mock(SensorData.class);
        this.repository.add(sensorData);

        Mockito.verify(this.collection).insert(Mockito.any(BasicDBObject.class));
    }

    @Test
    public void testFindBySensorId() throws Exception {
        BasicDBObject result = new BasicDBObject("id", 5)
                .append("name", "sensorName")
                .append("timestamp", 1)
                .append("data", new byte[4]);

        DBCursor cursor = Mockito.mock(DBCursor.class);
        Mockito.when(cursor.hasNext()).thenReturn(true).thenReturn(false);
        Mockito.doReturn(result).when(cursor).next();
        Mockito.doReturn(cursor).when(this.collection).find(Mockito.any(BasicDBObject.class));

        ArrayList<SensorData> sensorDataList = this.repository.findBySensorId(5);
        Assert.assertEquals(1, sensorDataList.size());
        Assert.assertSame(SensorData.class, sensorDataList.get(0).getClass());
        Assert.assertEquals(5, sensorDataList.get(0).getSensorId());
    }
}