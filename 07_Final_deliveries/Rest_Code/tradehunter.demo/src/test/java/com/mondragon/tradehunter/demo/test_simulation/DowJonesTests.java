package com.mondragon.tradehunter.demo.test_simulation;

import org.junit.jupiter.api.Test;

import static org.junit.Assert.assertEquals;
import static org.easymock.EasyMock.*;

import java.security.SecureRandom;

import org.junit.jupiter.api.BeforeEach;

import com.mondragon.tradehunter.demo.simulation.DowJones;
import com.mondragon.tradehunter.demo.simulation.Simulation;

class DowJonesTests {
    
    private DowJones dowJones;
    private Simulation simulation;

    @BeforeEach
    void setUp() {
        simulation = new Simulation(null);
        dowJones = new DowJones(simulation, "High_DJ", 0, 100);
    }

    @Test
    void testConstructor() {
        assertEquals(simulation, dowJones.getSimulation());
        assertEquals(0, dowJones.getMin(), 0.01);
        assertEquals(100, dowJones.getMax(), 0.01);
        assertEquals(50, dowJones.getValue(), 0.01);
    }

    @Test
    void testSetSimulation() {
        Simulation testSimulation = new Simulation(null);
        dowJones.setSimulation(testSimulation);
        assertEquals(testSimulation, dowJones.getSimulation());
    }

    @Test
    void testSetMin() {
        dowJones.setMin(50);
        assertEquals(50, dowJones.getMin(), 0.01);
    }

    @Test
    void testSetMax() {
        dowJones.setMax(200);
        assertEquals(200, dowJones.getMax(), 0.01);
    }

    @Test
    void testSetValue() {
        dowJones.setValue(150);
        assertEquals(150, dowJones.getValue(), 0.01);
    }

    @Test
    void testSetDbName() {
        dowJones.setDbName("Test db");
        assertEquals("Test db", dowJones.getDbName());
    }

    @Test
    void testSetRandom() {
        SecureRandom random = new SecureRandom();
        dowJones.setRand(random);
        assertEquals(random, dowJones.getRand());
    }

    @Test
    void testGiveValue() throws InterruptedException {
        SecureRandom rand = createMock(SecureRandom.class);

        expect(rand.nextDouble(0, 100)).andReturn(40.5);
        expect(rand.nextInt(100, 400)).andReturn(250);

        replay(rand);

        dowJones.setRand(rand);

        dowJones.giveValue();

        assertEquals(40.5, dowJones.getValue(), 0.0001);

        verify(rand);
    }
}
