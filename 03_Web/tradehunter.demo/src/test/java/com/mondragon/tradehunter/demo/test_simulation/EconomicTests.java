package com.mondragon.tradehunter.demo.test_simulation;

import org.junit.jupiter.api.Test;

import static org.junit.Assert.assertEquals;
import static org.easymock.EasyMock.*;

import java.security.SecureRandom;

import org.junit.jupiter.api.BeforeEach;

import com.mondragon.tradehunter.demo.simulation.Economic;
import com.mondragon.tradehunter.demo.simulation.Simulation;

class EconomicTests {
    
    private Economic economic;
    private Simulation simulation;

    @BeforeEach
    void setUp() {
        simulation = new Simulation();
        economic = new Economic(simulation, "Test Economic", "Test", 0, 100);
    }

    @Test
    void testConstructor() {
        assertEquals(simulation, economic.getSimulation());
        assertEquals("Test Economic", economic.getName());
        assertEquals(0, economic.getMin(), 0.01);
        assertEquals(100, economic.getMax(), 0.01);
        assertEquals(50, economic.getValue(), 0.01);
    }

    @Test
    void testSetSimulation() {
        Simulation testSimulation = new Simulation();
        economic.setSimulation(testSimulation);
        assertEquals(testSimulation, economic.getSimulation());
    }

    @Test
    void testSetMin() {
        economic.setMin(50);
        assertEquals(50, economic.getMin(), 0.01);
    }

    @Test
    void testSetMax() {
        economic.setMax(200);
        assertEquals(200, economic.getMax(), 0.01);
    }

    @Test
    void testSetValue() {
        economic.setValue(150);
        assertEquals(150, economic.getValue(), 0.01);
    }

    @Test
    void testSetRandom() {
        SecureRandom random = new SecureRandom();
        economic.setRand(random);
        assertEquals(random, economic.getRand());
    }

    @Test
    void testGiveValue() throws InterruptedException {
        SecureRandom rand = createMock(SecureRandom.class);

        expect(rand.nextDouble(0, 100)).andReturn(40.5);
        expect(rand.nextInt(100, 400)).andReturn(250);

        replay(rand);

        economic.setRand(rand);

        economic.giveValue();

        assertEquals(40.5, economic.getValue(), 0.0001);

        verify(rand);
    }
}
