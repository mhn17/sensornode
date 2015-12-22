package de.hammerton.sensornode.core.sensordatamanagement.repository;

import com.orientechnologies.orient.core.command.OCommandRequest;
import com.orientechnologies.orient.core.db.document.ODatabaseDocumentTx;
import com.orientechnologies.orient.core.iterator.ORecordIteratorClass;
import com.orientechnologies.orient.core.record.impl.ODocument;
import com.orientechnologies.orient.core.sql.OCommandSQL;
import com.orientechnologies.orient.core.sql.query.OSQLSynchQuery;
import de.hammerton.sensornode.core.sensordatamanagement.SensorData;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Matchers;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.runners.MockitoJUnitRunner;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@RunWith(MockitoJUnitRunner.class)
public class OrientDbSensorDataRepositoryTest {

    @Mock
    private ODatabaseDocumentTx db = null;

    private OrientDbSensorDataRepository repository = null;

    @Before
    public void setUp() throws Exception {
        this.repository = new OrientDbSensorDataRepository(this.db, "userName", "password");
    }

    @After
    public void tearDown() throws Exception {
        this.repository = null;
    }

    @Test
    @SuppressWarnings("unchecked")
    public void testFind() throws Exception {
        ODocument result1 = Mockito.mock(ODocument.class);
        Mockito.when(result1.field("id")).thenReturn(UUID.randomUUID().toString());
        Mockito.when(result1.field("sensorId")).thenReturn(5);
        Mockito.when(result1.field("name")).thenReturn("sensorName");
        Mockito.when(result1.field("timestamp")).thenReturn(1L);
        Mockito.when(result1.field("data")).thenReturn(new byte[4]);

        ODocument result2 = Mockito.mock(ODocument.class);
        Mockito.when(result2.field("id")).thenReturn(UUID.randomUUID().toString());
        Mockito.when(result2.field("sensorId")).thenReturn(6);
        Mockito.when(result2.field("name")).thenReturn("sensorName");
        Mockito.when(result2.field("timestamp")).thenReturn(1L);
        Mockito.when(result2.field("data")).thenReturn(new byte[4]);

        ORecordIteratorClass<ODocument> recordIterator = (ORecordIteratorClass<ODocument>) Mockito.mock(ORecordIteratorClass.class);
        Mockito.when(recordIterator.hasNext()).thenReturn(true).thenReturn(true).thenReturn(false);
        Mockito.doReturn(result1).doReturn(result2).when(recordIterator).next();
        Mockito.doReturn(recordIterator).when(this.db).browseClass("SensorData");

        ArrayList<SensorData> sensorDataList = this.repository.find();
        Assert.assertEquals(2, sensorDataList.size());
        Assert.assertSame(SensorData.class, sensorDataList.get(0).getClass());
        Assert.assertEquals(5, sensorDataList.get(0).getSensorId());
        Assert.assertEquals(6, sensorDataList.get(1).getSensorId());
    }

    @Test
    @SuppressWarnings("unchecked")
    public void testFindBySensorId() throws Exception {
        ODocument result = Mockito.mock(ODocument.class);
        Mockito.when(result.field("id")).thenReturn(UUID.randomUUID().toString());
        Mockito.when(result.field("sensorId")).thenReturn(5);
        Mockito.when(result.field("name")).thenReturn("sensorName");
        Mockito.when(result.field("timestamp")).thenReturn(1L);
        Mockito.when(result.field("data")).thenReturn(new byte[4]);

        List<ODocument> documents = new ArrayList<>();
        documents.add(result);
        Mockito.doReturn(documents).when(this.db).query(Matchers.<OSQLSynchQuery<ODocument>>any(), Matchers.eq(5));

        ArrayList<SensorData> sensorDataList = this.repository.findBySensorId(5);
        Assert.assertEquals(1, sensorDataList.size());
        Assert.assertSame(SensorData.class, sensorDataList.get(0).getClass());
        Assert.assertEquals(5, sensorDataList.get(0).getSensorId());
    }

    @Test
    public void testRemove() throws Exception {
        UUID id = UUID.randomUUID();

        OCommandRequest request = Mockito.mock(OCommandRequest.class);
        Mockito.doReturn(request).when(this.db).command(Matchers.any(OCommandSQL.class));

        this.repository.remove(id);
        Mockito.verify(request).execute(Matchers.eq(id.toString()));
        Mockito.verify(this.db).commit();
    }
}