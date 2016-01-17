package de.hammerton.sensornode.core.sensordatamanagement.repository;

import com.couchbase.lite.*;
import de.hammerton.sensornode.core.sensordatamanagement.SensorData;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Matchers;
import org.mockito.Mockito;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

import java.util.*;

@RunWith(PowerMockRunner.class)
@PrepareForTest({Database.class, View.class})
public class CouchbaseLiteDbSensorDataRepositoryTest {

    private Database database = null;
    private CouchbaseLiteDbSensorDataRepository repository = null;

    @Before
    public void setUp() throws Exception {
        this.database = PowerMockito.mock(Database.class);
        this.repository = new CouchbaseLiteDbSensorDataRepository(database);
    }

    @After
    public void tearDown() throws Exception {
        this.repository = null;
    }

    @Test
    public void testAdd() throws Exception {
        UUID id = UUID.randomUUID();
        SensorData sensorData = PowerMockito.mock(SensorData.class);
        Mockito.stub(sensorData.getId()).toReturn(id);

        Document document = PowerMockito.mock(Document.class);
        PowerMockito.when(database.getDocument(id.toString())).thenReturn(document);
        this.repository.add(sensorData);

        Mockito.verify(document).putProperties(Matchers.<Map<String, Object>>any());
    }

    @Test
    @SuppressWarnings("unchecked")
    public void testFind() throws Exception {
        Document result1 = PowerMockito.mock(Document.class);
        Mockito.when(result1.getProperty("_id")).thenReturn(UUID.randomUUID().toString());
        Mockito.when(result1.getProperty("sensorId")).thenReturn(5);
        Mockito.when(result1.getProperty("timestamp")).thenReturn(1);
        Mockito.when(result1.getProperty("data")).thenReturn(new byte[4]);
        QueryRow row1 = Mockito.mock(QueryRow.class);
        Mockito.when(row1.getDocument()).thenReturn(result1);

        Document result2 = PowerMockito.mock(Document.class);
        Mockito.when(result2.getProperty("_id")).thenReturn(UUID.randomUUID().toString());
        Mockito.when(result2.getProperty("sensorId")).thenReturn(6);
        Mockito.when(result2.getProperty("timestamp")).thenReturn(1);
        Mockito.when(result2.getProperty("data")).thenReturn(new byte[4]);
        QueryRow row2 = Mockito.mock(QueryRow.class);
        Mockito.when(row2.getDocument()).thenReturn(result2);

        Query query = PowerMockito.mock(Query.class);
        Mockito.when(database.createAllDocumentsQuery()).thenReturn(query);

        QueryEnumerator enumerator = PowerMockito.mock(QueryEnumerator.class);
        Mockito.when(query.run()).thenReturn(enumerator);
        Mockito.when(enumerator.hasNext()).thenReturn(true).thenReturn(true).thenReturn(false);
        Mockito.doReturn(row1).doReturn(row2).when(enumerator).next();

        ArrayList<SensorData> sensorDataList = this.repository.find();
        Assert.assertEquals(2, sensorDataList.size());
        Assert.assertSame(SensorData.class, sensorDataList.get(0).getClass());
        Assert.assertEquals(5, sensorDataList.get(0).getSensorId());
        Assert.assertEquals(6, sensorDataList.get(1).getSensorId());
    }

    @Test
    public void testFindWithExceptionReturnsEmptyList() throws Exception {
        Query query = PowerMockito.mock(Query.class);
        Mockito.when(database.createAllDocumentsQuery()).thenReturn(query);

        Mockito.when(query.run()).thenThrow(Mockito.mock(CouchbaseLiteException.class));

        ArrayList<SensorData> sensorDataList = this.repository.find();
        Assert.assertEquals(0, sensorDataList.size());
    }

    @Test
    public void testFindBySensorId() throws Exception {
        Document result1 = PowerMockito.mock(Document.class);
        Mockito.when(result1.getProperty("_id")).thenReturn(UUID.randomUUID().toString());
        Mockito.when(result1.getProperty("sensorId")).thenReturn(5);
        Mockito.when(result1.getProperty("timestamp")).thenReturn(1);
        Mockito.when(result1.getProperty("data")).thenReturn(new byte[4]);
        QueryRow row1 = Mockito.mock(QueryRow.class);
        Mockito.when(row1.getDocument()).thenReturn(result1);

        View view = PowerMockito.mock(View.class);
        Mockito.when(database.getView("sensorIdsView")).thenReturn(view);
        Query query = PowerMockito.mock(Query.class);
        Mockito.when(view.createQuery()).thenReturn(query);

        QueryEnumerator enumerator = PowerMockito.mock(QueryEnumerator.class);
        Mockito.when(query.run()).thenReturn(enumerator);
        Mockito.when(enumerator.hasNext()).thenReturn(true).thenReturn(false);
        Mockito.doReturn(row1).when(enumerator).next();

        ArrayList<SensorData> sensorDataList = this.repository.findBySensorId(5);
        Assert.assertEquals(1, sensorDataList.size());
        Assert.assertSame(SensorData.class, sensorDataList.get(0).getClass());
        Assert.assertEquals(5, sensorDataList.get(0).getSensorId());
    }

    @Test
    public void testFindBySensorIdWithExceptionReturnsEmptyList() throws Exception {
        View view = PowerMockito.mock(View.class);
        Mockito.when(database.getView("sensorIdsView")).thenReturn(view);
        Query query = PowerMockito.mock(Query.class);
        Mockito.when(view.createQuery()).thenReturn(query);

        Mockito.when(query.run()).thenThrow(Mockito.mock(CouchbaseLiteException.class));

        ArrayList<SensorData> sensorDataList = this.repository.findBySensorId(1);
        Assert.assertEquals(0, sensorDataList.size());
    }

    @Test
    public void testRemove() throws Exception {
        UUID id = UUID.randomUUID();
        Document document = PowerMockito.mock(Document.class);
        Mockito.when(database.getDocument(id.toString())).thenReturn(document);

        this.repository.remove(id);
        Mockito.verify(document).delete();
    }
}