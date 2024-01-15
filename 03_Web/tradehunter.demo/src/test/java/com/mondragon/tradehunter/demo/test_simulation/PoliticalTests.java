package com.mondragon.tradehunter.demo.test_simulation;

import org.junit.jupiter.api.Test;

import static org.junit.Assert.assertEquals;
import static org.easymock.EasyMock.*;

import java.security.SecureRandom;

import org.junit.jupiter.api.BeforeEach;

import com.mondragon.tradehunter.demo.simulation.Political;
import com.mondragon.tradehunter.demo.simulation.Simulation;

class PoliticalTests {
    
    private Political political;
    private Simulation simulation;

    @BeforeEach
    void setUp() {
        simulation = new Simulation();
        political = new Political(simulation, "Test Political", 0, 100);
    }

    @Test
    void testConstructor() {
        assertEquals(simulation, political.getSimulation());
        assertEquals("Test Political", political.getName());
        assertEquals(0, political.getMin(), 0.01);
        assertEquals(100, political.getMax(), 0.01);
        assertEquals(50, political.getValue(), 0.01);
    }

    @Test
    void testSetSimulation() {
        Simulation testSimulation = new Simulation();
        political.setSimulation(testSimulation);
        assertEquals(testSimulation, political.getSimulation());
    }

    @Test
    void testSetMin() {
        political.setMin(50);
        assertEquals(50, political.getMin(), 0.01);
    }

    @Test
    void testSetMax() {
        political.setMax(200);
        assertEquals(200, political.getMax(), 0.01);
    }

    @Test
    void testSetValue() {
        political.setValue(150);
        assertEquals(150, political.getValue(), 0.01);
    }

    @Test
    void testSetRandom() {
        SecureRandom random = new SecureRandom();
        political.setRand(random);
        assertEquals(random, political.getRand());
    }

    @Test
    void testGiveValue() throws InterruptedException {
        SecureRandom rand = createMock(SecureRandom.class);

        expect(rand.nextDouble(0, 100)).andReturn(40.5);
        expect(rand.nextInt(100, 400)).andReturn(250);

        replay(rand);

        political.setRand(rand);

        political.giveValue();

        assertEquals(40.5, political.getValue(), 0.0001);

        verify(rand);
    }
}
