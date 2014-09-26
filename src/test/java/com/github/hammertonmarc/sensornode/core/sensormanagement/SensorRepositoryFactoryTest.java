package com.github.hammertonmarc.sensornode.core.sensormanagement;

import org.junit.Test;

import static org.hamcrest.core.IsInstanceOf.instanceOf;
import static org.junit.Assert.assertThat;

/**
 * @author Marc Hammerton
 */
public class SensorRepositoryFactoryTest {
    @Test
    public void testGetRepository() throws Exception {
        assertThat(SensorRepositoryFactory.getRepository(),
                instanceOf(SensorRepository.class));
    }
}
