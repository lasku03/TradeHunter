package com.mondragon.tradehunter.demo.test_controllers;

import static org.junit.Assert.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.mondragon.tradehunter.demo.controllers.SimulationController;
import com.mondragon.tradehunter.demo.simulation.Economic;
import com.mondragon.tradehunter.demo.simulation.Political;
import com.mondragon.tradehunter.demo.simulation.SimulationMaker;
import com.mondragon.tradehunter.demo.simulation.Social;

class SimulationControllerTest {

    private SimulationController controller;

    @BeforeEach
    public void setUp() {
        controller = new SimulationController();
    }

    @Test
    void testStartSimulation() throws InterruptedException {
        controller.startSimulation();
        SimulationMaker simulationMaker = controller.getSimulationMaker();

        for (Social social : simulationMaker.getSocials()) {
            assertTrue(social.isAlive(), "Social thread should be alive");
        }

        for (Economic economic : simulationMaker.getEconomics()) {
            assertTrue(economic.isAlive(), "Economic thread should be alive");
        }

        for (Political political : simulationMaker.getPoliticals()) {
            assertTrue(political.isAlive(), "Political thread should be alive");
        }

        assertTrue(simulationMaker.getDowJones().isAlive(), "DowJones thread should be alive");
        assertTrue(simulationMaker.getPrediction().isAlive(), "Prediction thread should be alive");

        controller.stopSimulation();

        for (Social social : simulationMaker.getSocials()) {
            assertFalse(social.isAlive(), "Social thread should not be alive");
        }

        for (Economic economic : simulationMaker.getEconomics()) {
            assertFalse(economic.isAlive(), "Economic thread should not be alive");
        }

        for (Political political : simulationMaker.getPoliticals()) {
            assertFalse(political.isAlive(), "Political thread should not be alive");
        }

        assertFalse(simulationMaker.getDowJones().isAlive(), "DowJones thread should not be alive");
        assertFalse(simulationMaker.getPrediction().isAlive(), "Prediction thread should not be alive");

        controller.startSimulation();
    }

    @Test
    void testGetSetSimulationMaker() {
        SimulationMaker simulationMaker = new SimulationMaker();
        controller.setSimulationMaker(simulationMaker);
        assertEquals(simulationMaker, controller.getSimulationMaker());
    }
}
