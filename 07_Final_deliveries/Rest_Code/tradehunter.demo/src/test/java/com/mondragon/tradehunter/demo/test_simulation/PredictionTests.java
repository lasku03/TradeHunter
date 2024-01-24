package com.mondragon.tradehunter.demo.test_simulation;

import org.junit.jupiter.api.Test;

import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;

import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.List;

import org.easymock.EasyMock;
import org.junit.jupiter.api.BeforeEach;

import com.mondragon.tradehunter.demo.simulation.DowJones;
import com.mondragon.tradehunter.demo.simulation.Economic;
import com.mondragon.tradehunter.demo.simulation.GraphValue;
import com.mondragon.tradehunter.demo.simulation.Political;
import com.mondragon.tradehunter.demo.simulation.Prediction;
import com.mondragon.tradehunter.demo.simulation.Simulation;
import com.mondragon.tradehunter.demo.simulation.Social;

class PredictionTests {

    private Simulation simulation;
    private Social[] socials;
    private Economic[] economics;
    private Political[] politicals;
    private DowJones dowJones;
    private Prediction prediction;

    @BeforeEach
    void setUp() {
        socials = new Social[2];
        economics = new Economic[2];
        politicals = new Political[2];
        simulation = new Simulation(null);
        dowJones = new DowJones(simulation, "Test", 0, 10);
        prediction = new Prediction(simulation, socials, economics, politicals, dowJones);
    }

    @Test
    void testConstructor() {
        assertEquals("Prediction", prediction.getName());
        assertEquals(simulation, prediction.getSimulation());
        assertArrayEquals(socials, prediction.getSocials());
        assertArrayEquals(economics, prediction.getEconomics());
        assertArrayEquals(politicals, prediction.getPoliticals());
        assertEquals(dowJones, prediction.getDowJones());
    }

    @Test
    void testSetSimulation() {
        Simulation testSimulation = new Simulation(null);
        prediction.setSimulation(testSimulation);
        assertEquals(testSimulation, prediction.getSimulation());
    }

    @Test
    void testSetSocials() {
        Social [] testSocials = new Social[5];
        prediction.setSocials(testSocials);
        assertArrayEquals(testSocials, prediction.getSocials());
    }

    @Test
    void testSetEconomics() {
        Economic [] testEconomics = new Economic[5];
        prediction.setEconomics(testEconomics);
        assertArrayEquals(testEconomics, prediction.getEconomics());
    }

    @Test
    void testSetPoliticals() {
        Political [] testPoliticals = new Political[5];
        prediction.setPoliticals(testPoliticals);
        assertArrayEquals(testPoliticals, prediction.getPoliticals());
    }

    @Test
    void testSetDowJones() {
        DowJones testDowJones = new DowJones(simulation, "Test", 0, 1000);
        prediction.setDowJones(testDowJones);
        assertEquals(testDowJones, prediction.getDowJones());
    }

    @Test
    void testSetRandom() {
        SecureRandom random = new SecureRandom();
        prediction.setRand(random);
        assertEquals(random, prediction.getRand());
    }

    @Test
    void testSetValues() {
        List<GraphValue> values = new ArrayList<>();
        prediction.setValues(values);
        assertEquals(values, prediction.getValues());
    }

    @Test
    void testMakePrediction() throws InterruptedException {
        Prediction predictionMock = EasyMock.partialMockBuilder(Prediction.class)
                .addMockedMethod("askForPrediction", List.class)
                .createMock();
                
        socials = new Social[1];
        economics = new Economic[1];
        politicals = new Political[1];

        socials[0] = new Social(simulation, "Death rate", "Test", 0, 5);
        politicals[0] = new Political(simulation, "Unemployment rate", "Test", 0, 10);
        economics[0] = new Economic(simulation, "Euro", "Test", 3, 9);

        predictionMock.setSocials(socials);
        predictionMock.setPoliticals(politicals);
        predictionMock.setEconomics(economics);
        predictionMock.setDowJones(new DowJones(simulation, "Test", 0, 10));

        double expectedPrediction = 18.5;

        predictionMock.setValues(new ArrayList<>());
        EasyMock.expect(predictionMock.askForPrediction(predictionMock.getValues())).andReturn(expectedPrediction);

        EasyMock.replay(predictionMock);

        predictionMock.makePrediction();

        assertEquals(expectedPrediction, predictionMock.getPredictedValue(), 0.01);

        EasyMock.verify(predictionMock);
    }
}
