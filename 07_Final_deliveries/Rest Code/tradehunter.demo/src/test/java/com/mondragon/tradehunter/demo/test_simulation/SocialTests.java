package com.mondragon.tradehunter.demo.test_simulation;

import org.junit.jupiter.api.Test;

import static org.junit.Assert.assertEquals;
import static org.easymock.EasyMock.*;

import java.security.SecureRandom;

import org.junit.jupiter.api.BeforeEach;

import com.mondragon.tradehunter.demo.simulation.Social;
import com.mondragon.tradehunter.demo.simulation.Simulation;

class SocialTests {
    
    private Social social;
    private Simulation simulation;

    @BeforeEach
    void setUp() {
        simulation = new Simulation(null);
        social = new Social(simulation, "Test Social", "Test", 0, 100);
    }

    @Test
    void testConstructor() {
        assertEquals(simulation, social.getSimulation());
        assertEquals("Test Social", social.getName());
        assertEquals(0, social.getMin(), 0.01);
        assertEquals(100, social.getMax(), 0.01);
        assertEquals(50, social.getValue(), 0.01);
    }

    @Test
    void testSetSimulation() {
        Simulation testSimulation = new Simulation(null);
        social.setSimulation(testSimulation);
        assertEquals(testSimulation, social.getSimulation());
    }

    @Test
    void testSetMin() {
        social.setMin(50);
        assertEquals(50, social.getMin(), 0.01);
    }

    @Test
    void testSetMax() {
        social.setMax(200);
        assertEquals(200, social.getMax(), 0.01);
    }

    @Test
    void testSetValue() {
        social.setValue(150);
        assertEquals(150, social.getValue(), 0.01);
    }

    @Test
    void testSetRandom() {
        SecureRandom random = new SecureRandom();
        social.setRand(random);
        assertEquals(random, social.getRand());
    }

    @Test
    void testSetDbName() {
        social.setDbName("Test db");
        assertEquals("Test db", social.getDbName());
    }

    @Test
    void testGiveValue() throws InterruptedException {
        SecureRandom rand = createMock(SecureRandom.class);

        expect(rand.nextDouble(0, 100)).andReturn(40.5);
        expect(rand.nextInt(100, 400)).andReturn(250);

        replay(rand);

        social.setRand(rand);

        social.giveValue();

        assertEquals(40.5, social.getValue(), 0.0001);

        verify(rand);
    }
}
