package com.mondragon.tradehunter.demo.test_simulation;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.mondragon.tradehunter.demo.simulation.Prediction;
import com.mondragon.tradehunter.demo.simulation.SimulationMaker;
import com.mondragon.tradehunter.demo.simulation.DowJones;
import com.mondragon.tradehunter.demo.simulation.Economic;
import com.mondragon.tradehunter.demo.simulation.Political;
import com.mondragon.tradehunter.demo.simulation.Simulation;
import com.mondragon.tradehunter.demo.simulation.Social;

import static org.junit.jupiter.api.Assertions.*;

class SimulationMakerTests {

    private SimulationMaker simulationMaker;

    @BeforeEach
    void setUp() {
        simulationMaker = new SimulationMaker();
    }

    @Test
    void testConstructor() {
        assertNotNull(simulationMaker.getSimulation());
        assertNotNull(simulationMaker.getSocials());
        assertNotNull(simulationMaker.getEconomics());
        assertNotNull(simulationMaker.getPoliticals());
        assertEquals(SimulationMaker.NSOCIALS, simulationMaker.getSocials().length);
        assertEquals(SimulationMaker.NECONOMICS, simulationMaker.getEconomics().length);
        assertEquals(SimulationMaker.NPOLITICALS, simulationMaker.getPoliticals().length);
    }

    @Test
    void testSimulationGetterSetter() {
        Simulation simulation = new Simulation(null);
        simulationMaker.setSimulation(simulation);
        assertSame(simulation, simulationMaker.getSimulation());
    }

    @Test
    void testSocialsGetterSetter() {
        Social[] socials = new Social[SimulationMaker.NSOCIALS];
        for (int i = 0; i < SimulationMaker.NSOCIALS; i++) {
            socials[i] = new Social(new Simulation(null), "Social" + i, "Test", i, i + 1);
        }
        simulationMaker.setSocials(socials);
        assertArrayEquals(socials, simulationMaker.getSocials());
    }

    @Test
    void testEconomicsGetterSetter() {
        Economic[] economics = new Economic[SimulationMaker.NECONOMICS];
        for (int i = 0; i < SimulationMaker.NECONOMICS; i++) {
            economics[i] = new Economic(new Simulation(null), "Economic" + i, "Test", i, i + 1);
        }
        simulationMaker.setEconomics(economics);
        assertArrayEquals(economics, simulationMaker.getEconomics());
    }

    @Test
    void testPoliticalsGetterSetter() {
        Political[] politicals = new Political[SimulationMaker.NPOLITICALS];
        for (int i = 0; i < SimulationMaker.NPOLITICALS; i++) {
            politicals[i] = new Political(new Simulation(null), "Political" + i, "Test", i, i + 1);
        }
        simulationMaker.setPoliticals(politicals);
        assertArrayEquals(politicals, simulationMaker.getPoliticals());
    }

    @Test
    void testDowJonesGetterSetter() {
        DowJones dowJones = new DowJones(new Simulation(null), "Test", 10000, 20000);
        simulationMaker.setDowJones(dowJones);
        assertSame(dowJones, simulationMaker.getDowJones());
    }

    @Test
    void testPredictionGetterSetter() {
        Prediction prediction = new Prediction(new Simulation(null), new Social[SimulationMaker.NSOCIALS], new Economic[SimulationMaker.NECONOMICS], new Political[SimulationMaker.NPOLITICALS], new DowJones(new Simulation(null), "Test", 10000, 20000));
        simulationMaker.setPrediction(prediction);
        assertSame(prediction, simulationMaker.getPrediction());
    }

    @Test
    void testCreateThreads() {
        simulationMaker.createThreads();
        assertNotNull(simulationMaker.getSocials());
        assertNotNull(simulationMaker.getEconomics());
        assertNotNull(simulationMaker.getPoliticals());

        DowJones dowJones = simulationMaker.getDowJones();
        assertEquals(simulationMaker.getSimulation(), dowJones.getSimulation());
        assertEquals(22500, dowJones.getMin());
        assertEquals(32000, dowJones.getMax());

        Prediction prediction = simulationMaker.getPrediction();
        assertEquals(simulationMaker.getSimulation(), prediction.getSimulation());
        assertEquals(simulationMaker.getEconomics(), prediction.getEconomics());
        assertEquals(simulationMaker.getPoliticals(), prediction.getPoliticals());
        assertEquals(simulationMaker.getSocials(), prediction.getSocials());
        assertEquals(simulationMaker.getDowJones(), prediction.getDowJones());
    }

    @Test
    void testCreateSocialThreads() {
        simulationMaker.createSocialThreads();
        Social[] socials = simulationMaker.getSocials();

        assertEquals(3, socials.length);
        assertEquals(simulationMaker.getSimulation(), socials[0].getSimulation());
        assertEquals("Death rate", socials[0].getName());
        assertEquals("Defunciones", socials[0].getDbName());
        assertEquals(6.0, socials[0].getMin());
        assertEquals(12.0, socials[0].getMax());

        assertEquals(simulationMaker.getSimulation(), socials[1].getSimulation());
        assertEquals("Birth rate", socials[1].getName());
        assertEquals("Births", socials[1].getDbName());
        assertEquals(900, socials[1].getMin());
        assertEquals(1100, socials[1].getMax());

        assertEquals(simulationMaker.getSimulation(), socials[2].getSimulation());
        assertEquals("Debt per capita", socials[2].getName());
        assertEquals("Debt_per_capita", socials[2].getDbName());
        assertEquals(25000, socials[2].getMin());
        assertEquals(29000, socials[2].getMax());
    }

    @Test
    void testCreateEconomicThreads1() {
        simulationMaker.createEconomicThreads();
        Economic[] economics = simulationMaker.getEconomics();

        assertEquals(5, economics.length);
        assertEquals(simulationMaker.getSimulation(), economics[0].getSimulation());
        assertEquals("Euribor", economics[0].getName());
        assertEquals("Euribor", economics[0].getDbName());
        assertEquals(-1.0, economics[0].getMin());
        assertEquals(1.0, economics[0].getMax());
    }

    @Test
    void testCreateEconomicThreads2() {
        simulationMaker.createEconomicThreads();
        Economic[] economics = simulationMaker.getEconomics();

        assertEquals(simulationMaker.getSimulation(), economics[1].getSimulation());
        assertEquals("IPC", economics[1].getName());
        assertEquals("IPC", economics[1].getDbName());
        assertEquals(-2.0, economics[1].getMin());
        assertEquals(1.5, economics[1].getMax());
    }

    @Test
    void testCreateEconomicThreads3() {
        simulationMaker.createEconomicThreads();
        Economic[] economics = simulationMaker.getEconomics();

        assertEquals(simulationMaker.getSimulation(), economics[2].getSimulation());
        assertEquals("Euro", economics[2].getName());
        assertEquals("Price_EUR", economics[2].getDbName());
        assertEquals(1.1, economics[2].getMin());
        assertEquals(1.3, economics[2].getMax());
    }

    @Test
    void testCreateEconomicThreads4() {
        simulationMaker.createEconomicThreads();
        Economic[] economics = simulationMaker.getEconomics();

        assertEquals(simulationMaker.getSimulation(), economics[3].getSimulation());
        assertEquals("Debt", economics[3].getName());
        assertEquals("Total_debt", economics[3].getDbName());
        assertEquals(1.2, economics[3].getMin());
        assertEquals(1.4, economics[3].getMax());
    }

    @Test
    void testCreateEconomicThreads5() {
        simulationMaker.createEconomicThreads();
        Economic[] economics = simulationMaker.getEconomics();

        assertEquals(simulationMaker.getSimulation(), economics[4].getSimulation());
        assertEquals("Gross Domestic Product", economics[4].getName());
        assertEquals("GDP_Value", economics[4].getDbName());
        assertEquals(-11, economics[4].getMin());
        assertEquals(18, economics[4].getMax());
    }

    @Test
    void testCreatePoliticalThreads() {
        simulationMaker.createPoliticalThreads();
        Political[] politicals = simulationMaker.getPoliticals();

        assertEquals(3, politicals.length);
        assertEquals(simulationMaker.getSimulation(), politicals[0].getSimulation());
        assertEquals("Activity rate", politicals[0].getName());
        assertEquals("Activos", politicals[0].getDbName());
        assertEquals(22000.0, politicals[0].getMin());
        assertEquals(23500.0, politicals[0].getMax());

        assertEquals(simulationMaker.getSimulation(), politicals[1].getSimulation());
        assertEquals("Unemployment rate", politicals[1].getName());
        assertEquals("Parados", politicals[1].getDbName());
        assertEquals(3500.0, politicals[1].getMin());
        assertEquals(5000.0, politicals[1].getMax());

        assertEquals(simulationMaker.getSimulation(), politicals[2].getSimulation());
        assertEquals("Employment rate", politicals[2].getName());
        assertEquals("Ocupados", politicals[2].getDbName());
        assertEquals(18000.0, politicals[2].getMin());
        assertEquals(20000.0, politicals[2].getMax());
    }

    @Test
    void testStartThreads() {
        simulationMaker.createThreads();
        simulationMaker.startThreads();

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
    }

    @Test
    void testInterruptAndWaitEndOfThreads() {
        simulationMaker.createThreads();
        simulationMaker.startThreads();
        simulationMaker.interruptThreads();
        try {
            simulationMaker.waitEndOfThreads();
        } catch (InterruptedException e) {
        }

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
    }

    @Test
    void testMakeSimulation() throws InterruptedException {
        SimulationMaker simulationMaker = new SimulationMaker();

        simulationMaker.makeSimulation();

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

        simulationMaker.stopSimulation();

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
    }
}
