package de.hammerton.sensornode.server;

import junit.framework.TestCase;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.runners.MockitoJUnitRunner;

import static org.junit.Assert.*;

@RunWith(MockitoJUnitRunner.class)
public class HttpServerTest extends TestCase {

    @Mock
    private com.sun.net.httpserver.HttpServer serverInstance = null;

    private HttpServer server = null;

    @Before
    public void setUp() throws Exception {
        super.setUp();

        this.server = new HttpServer();
        this.server.setServerInstance(this.serverInstance);
    }

    @Test
    public void testStart() throws Exception {
        this.server.start();

        Mockito.verify(this.serverInstance).start();
    }

    @Test
    public void testStop() throws Exception {
        this.server.stop();

        Mockito.verify(this.serverInstance).stop(0);
    }

    @Test
    public void testRun() throws Exception {
        this.server.run();

        Mockito.verify(this.serverInstance).start();
    }
}