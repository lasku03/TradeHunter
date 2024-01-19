package com.mondragon.tradehunter.demo.test_simulation;

import static org.easymock.EasyMock.createMock;
import static org.easymock.EasyMock.expect;
import static org.easymock.EasyMock.replay;
import static org.easymock.EasyMock.verify;
import static org.junit.jupiter.api.Assertions.*;

import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Semaphore;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.mondragon.tradehunter.demo.simulation.Prediction;
import com.mondragon.tradehunter.demo.simulation.DowJones;
import com.mondragon.tradehunter.demo.simulation.Economic;
import com.mondragon.tradehunter.demo.simulation.GraphValue;
import com.mondragon.tradehunter.demo.simulation.Political;
import com.mondragon.tradehunter.demo.simulation.ReusableBarrier;
import com.mondragon.tradehunter.demo.simulation.Simulation;
import com.mondragon.tradehunter.demo.simulation.Social;
import com.mondragon.tradehunter.demo.simulation.ValueSender;

class SimulationTests {

    private Simulation simulation;

    @BeforeEach
    void setUp() {
        simulation = new Simulation(new ValueSender());
    }

    @Test
    void testWaitValues1() throws InterruptedException {
        Prediction prediction = new Prediction(simulation, new Social[0], new Economic[0], new Political[0], new DowJones(simulation, "Test", 0, 100));
        
        Thread predictionThread = new Thread(() -> {
            try {
                simulation.waitValues(prediction);
            } catch (InterruptedException e) {
                fail("InterruptedException occurred");
            }
        });

        List<GraphValue> graphValues = new ArrayList<>();
        graphValues.add(new GraphValue(null, 0));
        graphValues.add(new GraphValue(null, 0));
        simulation.getPredictionWait().release();
        simulation.setnDowJones(1);
        simulation.getDowJonesDone().release();

        predictionThread.start();
        predictionThread.join();
        predictionThread.interrupt();

        assertEquals(50, prediction.getPredictedValue());
        assertEquals(1, simulation.getGraphMutex().availablePermits());
        assertEquals(1, simulation.getDowJonesWait().availablePermits());
        assertEquals(0, simulation.getDowJonesDone().availablePermits());
        assertEquals(0, simulation.getGraphValues().size());
    }

    @Test
    void testWaitValues2() throws InterruptedException {
        Prediction prediction = new Prediction(simulation, new Social[0], new Economic[0], new Political[0], new DowJones(simulation, "Test", 0, 100));
        
        Thread predictionThread = new Thread(() -> {
            try {
                simulation.waitValues(prediction);
            } catch (InterruptedException e) {
                fail("Interrupted exception occurred");
            }
        });

        List<GraphValue> graphValues = new ArrayList<>();
        graphValues.add(new GraphValue(null, 0));
        graphValues.add(new GraphValue(null, 0));
        simulation.getPredictionWait().release();
        simulation.setnEconomics(3);
        simulation.getEconomicDone().release();

        predictionThread.start();
        predictionThread.join();
        predictionThread.interrupt();

        assertEquals(50, prediction.getPredictedValue());
        assertEquals(1, simulation.getGraphMutex().availablePermits());
        assertEquals(1, simulation.getEconomicWait().availablePermits());
        assertEquals(0, simulation.getEconomicDone().availablePermits());
        assertEquals(0, simulation.getGraphValues().size());
    }

    @Test
    void testWaitValues3() throws InterruptedException {
        Prediction prediction = new Prediction(simulation, new Social[0], new Economic[0], new Political[0], new DowJones(simulation, "Test", 0, 100));
        
        Thread predictionThread = new Thread(() -> {
            try {
                simulation.waitValues(prediction);
            } catch (InterruptedException e) {
                fail("InterruptedException occurred");
            }
        });

        List<GraphValue> graphValues = new ArrayList<>();
        graphValues.add(new GraphValue(null, 0));
        graphValues.add(new GraphValue(null, 0));
        simulation.getPredictionWait().release();
        simulation.setPoliticalToPaint(3);
        simulation.getPoliticalDone().release();

        predictionThread.start();
        predictionThread.join();
        predictionThread.interrupt();

        assertEquals(50, prediction.getPredictedValue());
        assertEquals(1, simulation.getGraphMutex().availablePermits());
        assertEquals(1, simulation.getPoliticalWait().availablePermits());
        assertEquals(0, simulation.getPoliticalDone().availablePermits());
        assertEquals(0, simulation.getGraphValues().size());
    }

    @Test
    void testWaitValues4() throws InterruptedException {
        Prediction prediction = new Prediction(simulation, new Social[0], new Economic[0], new Political[0], new DowJones(simulation, "Test", 0, 100));
        
        Thread predictionThread = new Thread(() -> {
            try {
                simulation.waitValues(prediction);
            } catch (InterruptedException e) {
                fail("InterruptedException occurred");
            }
        });

        List<GraphValue> graphValues = new ArrayList<>();
        graphValues.add(new GraphValue(null, 0));
        graphValues.add(new GraphValue(null, 0));
        simulation.getPredictionWait().release();
        simulation.setPoliticalToPaint(2);
        simulation.setSocialToPaint(1);
        simulation.getPoliticalDone().release();
        simulation.getSocialDone().release();

        predictionThread.start();
        predictionThread.join();
        predictionThread.interrupt();

        assertEquals(50, prediction.getPredictedValue());
        assertEquals(1, simulation.getGraphMutex().availablePermits());
        assertEquals(1, simulation.getPoliticalWait().availablePermits());
        assertEquals(1, simulation.getSocialWait().availablePermits());
        assertEquals(0, simulation.getPoliticalDone().availablePermits());
        assertEquals(0, simulation.getSocialDone().availablePermits());
        assertEquals(0, simulation.getGraphValues().size());
    }

    @Test
    void testWaitValues5() throws InterruptedException {
        Prediction prediction = new Prediction(simulation, new Social[0], new Economic[0], new Political[0], new DowJones(simulation, "Test", 0, 100));
        
        Thread predictionThread = new Thread(() -> {
            try {
                simulation.waitValues(prediction);
            } catch (InterruptedException e) {
                fail("InterruptedException occurred");
            }
        });

        List<GraphValue> graphValues = new ArrayList<>();
        graphValues.add(new GraphValue(null, 0));
        graphValues.add(new GraphValue(null, 0));
        simulation.getPredictionWait().release();
        simulation.setSocialToPaint(3);
        simulation.getSocialDone().release();

        predictionThread.start();
        predictionThread.join();
        predictionThread.interrupt();

        assertEquals(50, prediction.getPredictedValue());
        assertEquals(1, simulation.getGraphMutex().availablePermits());
        assertEquals(1, simulation.getSocialWait().availablePermits());
        assertEquals(0, simulation.getSocialDone().availablePermits());
        assertEquals(0, simulation.getGraphValues().size());
    }

    @Test
    void testWaitValues6() throws InterruptedException {
        Prediction prediction = new Prediction(simulation, new Social[0], new Economic[0], new Political[0], new DowJones(simulation, "Test", 0, 100));
        
        Thread predictionThread = new Thread(() -> {
            try {
                simulation.waitValues(prediction);
            } catch (InterruptedException e) {
                fail("InterruptedException occurred");
            }
        });

        List<GraphValue> graphValues = new ArrayList<>();
        graphValues.add(new GraphValue(null, 0));
        graphValues.add(new GraphValue(null, 0));
        simulation.getPredictionWait().release();
        simulation.setSocialToPaint(2);
        simulation.setPoliticalToPaint(1);
        simulation.getSocialDone().release();
        simulation.getPoliticalDone().release();

        predictionThread.start();
        predictionThread.join();
        predictionThread.interrupt();

        assertEquals(50, prediction.getPredictedValue());
        assertEquals(1, simulation.getGraphMutex().availablePermits());
        assertEquals(1, simulation.getSocialWait().availablePermits());
        assertEquals(1, simulation.getPoliticalWait().availablePermits());
        assertEquals(0, simulation.getSocialDone().availablePermits());
        assertEquals(0, simulation.getPoliticalDone().availablePermits());
        assertEquals(0, simulation.getGraphValues().size());
    }

    @Test
    void testWaitValues7() throws InterruptedException {
        Prediction prediction = new Prediction(simulation, new Social[0], new Economic[0], new Political[0], new DowJones(simulation, "Test", 0, 100));
        
        Thread predictionThread = new Thread(() -> {
            try {
                simulation.waitValues(prediction);
            } catch (InterruptedException e) {
                fail("InterruptedException occurred");
            }
        });

        List<GraphValue> graphValues = new ArrayList<>();
        graphValues.add(new GraphValue(null, 0));
        graphValues.add(new GraphValue(null, 0));
        simulation.getPredictionWait().release();
        simulation.setSocialToPaint(2);
        simulation.setPoliticalToPaint(2);
        simulation.getSocialDone().release();
        simulation.getPoliticalDone().release();

        predictionThread.start();
        predictionThread.join();
        predictionThread.interrupt();

        assertEquals(50, prediction.getPredictedValue());
        assertEquals(1, simulation.getGraphMutex().availablePermits());
        assertEquals(1, simulation.getPoliticalWait().availablePermits());
        assertEquals(1, simulation.getSocialWait().availablePermits());
        assertEquals(0, simulation.getPoliticalDone().availablePermits());
        assertEquals(0, simulation.getSocialDone().availablePermits());
        assertEquals(0, simulation.getGraphValues().size());
    }

    @Test
    void testWaitValues8() throws InterruptedException {
        Prediction prediction = new Prediction(simulation, new Social[0], new Economic[0], new Political[0], new DowJones(simulation, "Test", 0, 100));
        
        Thread predictionThread = new Thread(() -> {
            try {
                simulation.waitValues(prediction);
            } catch (InterruptedException e) {
                fail("InterruptedException occurred");
            }
        });

        List<GraphValue> graphValues = new ArrayList<>();
        graphValues.add(new GraphValue(null, 0));
        graphValues.add(new GraphValue(null, 0));
        simulation.getPredictionWait().release();
        simulation.setSocialToPaint(1);
        simulation.setPoliticalToPaint(1);
        simulation.getSocialDone().release();
        simulation.getPoliticalDone().release();

        predictionThread.start();
        predictionThread.join();
        predictionThread.interrupt();

        assertEquals(50, prediction.getPredictedValue());
        assertEquals(1, simulation.getGraphMutex().availablePermits());
        assertEquals(1, simulation.getPoliticalWait().availablePermits());
        assertEquals(1, simulation.getSocialWait().availablePermits());
        assertEquals(0, simulation.getSocialDone().availablePermits());
        assertEquals(0, simulation.getPoliticalDone().availablePermits());
        assertEquals(0, simulation.getGraphValues().size());
    }
    
    @Test
    void testWaitDowJonesValues() throws InterruptedException {
        DowJones dowJones = new DowJones(simulation, "Test", 0, 100);
        SecureRandom rand = createMock(SecureRandom.class);

        expect(rand.nextInt(100, 400)).andReturn(0);
        expect(rand.nextDouble(0, 100)).andReturn(40.5);

        replay(rand);

        dowJones.setRand(rand);

        Thread dowJonesThread = new Thread(() -> {
            try {
                simulation.waitDowJonesValues(dowJones);
            } catch (InterruptedException e) {
                fail("InterruptedException occurred");
            }
        });

        dowJonesThread.start();
        simulation.getDowJonesWait().release();
        dowJonesThread.join();
        dowJonesThread.interrupt();

        // Thread.sleep(100); // Give it some time to acquire the semaphore
        assertEquals(1, simulation.getnDowJones());
        assertEquals(40.5, dowJones.getValue(), 0.01);
        assertTrue(simulation.isPredictionInProgress());

        verify(rand);
    }

    @Test
    void testWaitDowJonesPredictionDone() throws InterruptedException {
        DowJones dowJones = new DowJones(simulation, "Test", 0, 100);

        Thread dowJonesThread = new Thread(() -> {
            try {
                simulation.waitDowJonesPredictionDone(dowJones);
            } catch (InterruptedException e) {
                fail("InterruptedException occurred");
            }
        });

        simulation.getGraphMutex().release();
        simulation.setnDowJones(1);
        simulation.getDowJonesMutex().acquire();

        dowJonesThread.start();
        dowJonesThread.join();
        dowJonesThread.interrupt();

        assertEquals(0, simulation.getnDowJones());
        assertEquals(1, simulation.getDowJonesDone().availablePermits());
        assertEquals(1, simulation.getDowJonesMutex().availablePermits());
        assertFalse(simulation.isPredictionInProgress());
    }

    @Test
    void testWaitEconomicValues1() throws InterruptedException {
        Economic economic = new Economic(simulation, "Test Economic", "Test", 0, 100);
        SecureRandom rand = createMock(SecureRandom.class);

        expect(rand.nextInt(100, 400)).andReturn(0);
        expect(rand.nextDouble(0, 100)).andReturn(40.5);

        replay(rand);

        Thread economicThread = new Thread(() -> {
            try {
                simulation.waitEconomicValues(economic);
            } catch (InterruptedException e) {
                fail("InterruptedException occurred");
            }
        });

        economic.setRand(rand);

        economicThread.start();
        simulation.getEconomicWait().release();
        economicThread.join();
        economicThread.interrupt();

        assertEquals(1, simulation.getnEconomics());
        assertFalse(simulation.isPredictionInProgress());
        assertEquals(1, simulation.getEconomicMutex().availablePermits());
        assertEquals(40.5, economic.getValue(), 0.01);

        verify(rand);
    }

    @Test
    void testWaitEconomicPredictionDone1() throws InterruptedException {
        Economic economic = new Economic(simulation, "Test Economic", "Test", 0, 100);

        Thread economicThread = new Thread(() -> {
            try {
                simulation.waitEconomicPredictionDone(economic);
            } catch (InterruptedException e) {
                fail("InterruptedException occurred");
            }
        });

        simulation.getGraphMutex().release();
        simulation.setnEconomics(1);
        simulation.setPredictionInProgress(true);

        economicThread.start();
        economicThread.join();
        economicThread.interrupt();

        assertEquals(0, simulation.getnEconomics());
        assertEquals(1, simulation.getEconomicDone().availablePermits());
        assertFalse(simulation.isPredictionInProgress());
    }

    @Test
    void testWaitEconomicValues2() throws InterruptedException {
        Economic economic = new Economic(simulation, "Test Economic", "Test", 0, 100);
        SecureRandom rand = createMock(SecureRandom.class);

        expect(rand.nextInt(100, 400)).andReturn(0);
        expect(rand.nextDouble(0, 100)).andReturn(40.5);

        replay(rand);

        Thread economicThread = new Thread(() -> {
            try {
                simulation.waitEconomicValues(economic);
            } catch (InterruptedException e) {
                fail("InterruptedException occurred");
            }
        });

        economic.setRand(rand);
        simulation.setnEconomics(2);

        economicThread.start();
        simulation.getEconomicWait().release();
        economicThread.join();
        economicThread.interrupt();

        assertEquals(3, simulation.getnEconomics());
        assertTrue(simulation.isPredictionInProgress());
        assertEquals(1, simulation.getPredictionWait().availablePermits());
        assertEquals(40.5, economic.getValue(), 0.01);

        verify(rand);
    }

    @Test
    void testWaitEconomicPredictionDone2() throws InterruptedException {
        Economic economic = new Economic(simulation, "Test Economic", "Test", 0, 100);

        Thread economicThread = new Thread(() -> {
            try {
                simulation.waitEconomicPredictionDone(economic);
            } catch (InterruptedException e) {
                fail("InterruptedException occurred");
            }
        });

        simulation.getGraphMutex().release();
        simulation.setnEconomics(3);
        simulation.setPredictionInProgress(true);

        economicThread.start();
        economicThread.join();
        economicThread.interrupt();

        assertEquals(2, simulation.getnEconomics());
        assertEquals(1, simulation.getEconomicWait().availablePermits());
        assertTrue(simulation.isPredictionInProgress());
    }

    //1 political and 0 social
    @Test
    void testWaitPoliticalValues1() throws InterruptedException {
        Political political = new Political(simulation, "Test Political", "Test", 0, 100);
        SecureRandom rand = createMock(SecureRandom.class);

        expect(rand.nextInt(100, 400)).andReturn(0);
        expect(rand.nextDouble(0, 100)).andReturn(40.5);

        replay(rand);

        Thread politicalThread = new Thread(() -> {
            try {
                simulation.waitPoliticalValues(political);
            } catch (InterruptedException e) {
                fail("InterruptedException occurred");
            }
        });

        political.setRand(rand);
        simulation.getBarrier().setNumWaiting(2);

        politicalThread.start();
        simulation.getPoliticalWait().release();
        politicalThread.join();
        politicalThread.interrupt();

        assertEquals(1, simulation.getnPoliticals());
        assertEquals(1, simulation.getPoliticalToPaint());
        assertEquals(0, simulation.getSocialToPaint());
        assertFalse(simulation.isPredictionInProgress());
        assertEquals(40.5, political.getValue(), 0.01);

        verify(rand);
    }

    // 1 political and 1 social
    @Test
    void testWaitPoliticalValues2() throws InterruptedException {
        Political political = new Political(simulation, "Test Political", "Test", 0, 100);
        SecureRandom rand = createMock(SecureRandom.class);

        expect(rand.nextInt(100, 400)).andReturn(0);
        expect(rand.nextDouble(0, 100)).andReturn(40.5);

        replay(rand);

        Thread politicalThread = new Thread(() -> {
            try {
                simulation.waitPoliticalValues(political);
            } catch (InterruptedException e) {
                fail("InterruptedException occurred");
            }
        });

        political.setRand(rand);
        simulation.setnSocials(1);
        simulation.getBarrier().setNumWaiting(2);

        politicalThread.start();
        simulation.getPoliticalWait().release();
        politicalThread.join();
        politicalThread.interrupt();

        assertEquals(1, simulation.getnPoliticals());
        assertEquals(1, simulation.getnSocials());
        assertEquals(1, simulation.getPoliticalToPaint());
        assertFalse(simulation.isPredictionInProgress());
        assertEquals(40.5, political.getValue(), 0.01);

        verify(rand);
    }

    // 2 political and 0 social
    @Test
    void testWaitPoliticalValues3() throws InterruptedException {
        Political political = new Political(simulation, "Test Political", "Test", 0, 100);
        SecureRandom rand = createMock(SecureRandom.class);

        expect(rand.nextInt(100, 400)).andReturn(0);
        expect(rand.nextDouble(0, 100)).andReturn(40.5);

        replay(rand);

        Thread politicalThread = new Thread(() -> {
            try {
                simulation.waitPoliticalValues(political);
            } catch (InterruptedException e) {
                fail("InterruptedException occurred");
            }
        });

        political.setRand(rand);
        simulation.setnPoliticals(1);
        simulation.setnSocials(0);
        simulation.setPoliticalToPaint(1);
        simulation.getBarrier().setNumWaiting(2);

        politicalThread.start();
        simulation.getPoliticalWait().release();
        politicalThread.join();
        politicalThread.interrupt();

        assertEquals(2, simulation.getnPoliticals());
        assertEquals(0, simulation.getnSocials());
        assertEquals(2, simulation.getPoliticalToPaint());
        assertFalse(simulation.isPredictionInProgress());
        assertEquals(40.5, political.getValue(), 0.01);

        verify(rand);
    }

    // There are 3 political
    @Test
    void testWaitPoliticalValues4() throws InterruptedException {
        Political political = new Political(simulation, "Test Political", "Test", 0, 100);
        SecureRandom rand = createMock(SecureRandom.class);

        expect(rand.nextInt(100, 400)).andReturn(0);
        expect(rand.nextDouble(0, 100)).andReturn(40.5);

        replay(rand);

        Thread politicalThread = new Thread(() -> {
            try {
                simulation.waitPoliticalValues(political);
            } catch (InterruptedException e) {
                fail("InterruptedException occurred");
            }
        });
        
        simulation.getBarrier().setNumWaiting(2);
        simulation.setnPoliticals(2);
        simulation.setPoliticalToPaint(2);
        political.setRand(rand);

        politicalThread.start();
        simulation.getPoliticalWait().release();
        politicalThread.join();
        politicalThread.interrupt();

        assertEquals(40.5, political.getValue(), 0.01);
        assertEquals(0, simulation.getnPoliticals());
        assertEquals(3, simulation.getPoliticalToPaint());
        assertTrue(simulation.isPredictionInProgress());
        assertEquals(1, simulation.getPredictionWait().availablePermits());

        verify(rand);
    }

    // There are 2 political and 1 social
    @Test
    void testWaitPoliticalValues5() throws InterruptedException {
        Political political = new Political(simulation, "Test Political", "Test", 0, 100);
        SecureRandom rand = createMock(SecureRandom.class);

        expect(rand.nextInt(100, 400)).andReturn(0);
        expect(rand.nextDouble(0, 100)).andReturn(40.5);

        replay(rand);

        Thread politicalThread = new Thread(() -> {
            try {
                simulation.waitPoliticalValues(political);
            } catch (InterruptedException e) {
                fail("InterruptedException occurred");
            }
        });
        
        simulation.getBarrier().setNumWaiting(2);
        simulation.setnPoliticals(1);
        simulation.setnSocials(1);
        simulation.setPoliticalToPaint(1);
        simulation.setSocialToPaint(1);
        political.setRand(rand);

        politicalThread.start();
        simulation.getPoliticalWait().release();
        politicalThread.join();
        politicalThread.interrupt();

        assertEquals(40.5, political.getValue(), 0.01);
        assertEquals(0, simulation.getnPoliticals());
        assertEquals(0, simulation.getnSocials());
        assertEquals(2, simulation.getPoliticalToPaint());
        assertEquals(1, simulation.getSocialToPaint());
        assertTrue(simulation.isPredictionInProgress());
        assertEquals(1, simulation.getPredictionWait().availablePermits());

        verify(rand);
    }

    // There are 1 political and 2 socials
    @Test
    void testWaitPoliticalValues6() throws InterruptedException {
        Political political = new Political(simulation, "Test Political", "Test", 0, 100);
        SecureRandom rand = createMock(SecureRandom.class);

        expect(rand.nextInt(100, 400)).andReturn(0);
        expect(rand.nextDouble(0, 100)).andReturn(40.5);

        replay(rand);

        Thread politicalThread = new Thread(() -> {
            try {
                simulation.waitPoliticalValues(political);
            } catch (InterruptedException e) {
                fail("InterruptedException occurred");
            }
        });
        
        simulation.getBarrier().setNumWaiting(2);
        simulation.setnPoliticals(0);
        simulation.setnSocials(2);
        simulation.setPoliticalToPaint(0);
        simulation.setSocialToPaint(2);
        political.setRand(rand);

        politicalThread.start();
        simulation.getPoliticalWait().release();
        politicalThread.join();
        politicalThread.interrupt();

        assertEquals(40.5, political.getValue(), 0.01);
        assertEquals(0, simulation.getnPoliticals());
        assertEquals(0, simulation.getnSocials());
        assertEquals(1, simulation.getPoliticalToPaint());
        assertEquals(2, simulation.getSocialToPaint());
        assertTrue(simulation.isPredictionInProgress());
        assertEquals(1, simulation.getPredictionWait().availablePermits());

        verify(rand);
    }

    // 1 politicalToPaint and 0 socialToPaint
    @Test
    void testWaitPoliticalPredictionDone1() throws InterruptedException {
        Political political = new Political(simulation, "Test Political", "Test", 0, 100);

        Thread politicalThread = new Thread(() -> {
            try {
                simulation.waitPoliticalPredictionDone(political);
            } catch (InterruptedException e) {
                fail("InterruptedException occurred");
            }
        });

        simulation.getGraphMutex().release();
        simulation.setPoliticalToPaint(1);
        simulation.setPredictionInProgress(true);

        politicalThread.start();
        politicalThread.join();
        politicalThread.interrupt();

        assertEquals(0, simulation.getPoliticalToPaint());
        assertEquals(1, simulation.getPoliticalDone().availablePermits());
        assertFalse(simulation.isPredictionInProgress());
    }

    // 1 politicalToPaint and 2 socialToPaint
    @Test
    void testWaitPoliticalPredictionDone2() throws InterruptedException {
        Political political = new Political(simulation, "Test Political", "Test", 0, 100);

        Thread politicalThread = new Thread(() -> {
            try {
                simulation.waitPoliticalPredictionDone(political);
            } catch (InterruptedException e) {
                fail("InterruptedException occurred");
            }
        });

        simulation.getGraphMutex().release();
        simulation.setPoliticalToPaint(1);
        simulation.setSocialToPaint(2);
        simulation.setPredictionInProgress(true);

        politicalThread.start();
        politicalThread.join();
        politicalThread.interrupt();

        assertEquals(0, simulation.getPoliticalToPaint());
        assertEquals(2, simulation.getSocialToPaint());
        assertEquals(1, simulation.getPoliticalDone().availablePermits());
        assertTrue(simulation.isPredictionInProgress());
    }

    // 2 politicalToPaint and 1 socialToPaint
    @Test
    void testWaitPoliticalPredictionDone3() throws InterruptedException {
        Political political = new Political(simulation, "Test Political", "Test", 0, 100);

        Thread politicalThread = new Thread(() -> {
            try {
                simulation.waitPoliticalPredictionDone(political);
            } catch (InterruptedException e) {
                fail("InterruptedException occurred");
            }
        });

        simulation.getGraphMutex().release();
        simulation.setPoliticalToPaint(2);
        simulation.setSocialToPaint(1);
        simulation.setPredictionInProgress(true);

        politicalThread.start();
        politicalThread.join();
        politicalThread.interrupt();

        assertEquals(1, simulation.getPoliticalToPaint());
        assertEquals(1, simulation.getSocialToPaint());
        assertEquals(1, simulation.getPoliticalWait().availablePermits());
        assertTrue(simulation.isPredictionInProgress());
    }

    //1 social and 0 political
    @Test
    void testWaitSocialValues1() throws InterruptedException {
        Social social = new Social(simulation, "Test Social", "Test", 0, 100);
        SecureRandom rand = createMock(SecureRandom.class);

        expect(rand.nextInt(100, 400)).andReturn(0);
        expect(rand.nextDouble(0, 100)).andReturn(40.5);

        replay(rand);

        Thread socialThread = new Thread(() -> {
            try {
                simulation.waitSocialValues(social);
            } catch (InterruptedException e) {
                fail("InterruptedException occurred");
            }
        });

        social.setRand(rand);
        simulation.getBarrier().setNumWaiting(2);

        socialThread.start();
        simulation.getSocialWait().release();
        socialThread.join();
        socialThread.interrupt();

        assertEquals(1, simulation.getnSocials());
        assertEquals(1, simulation.getSocialToPaint());
        assertEquals(0, simulation.getPoliticalToPaint());
        assertFalse(simulation.isPredictionInProgress());
        assertEquals(40.5, social.getValue(), 0.01);

        verify(rand);
    }

    // 1 social and 1 political
    @Test
    void testWaitSocialValues2() throws InterruptedException {
        Social social = new Social(simulation, "Test Social", "Test", 0, 100);
        SecureRandom rand = createMock(SecureRandom.class);

        expect(rand.nextInt(100, 400)).andReturn(0);
        expect(rand.nextDouble(0, 100)).andReturn(40.5);

        replay(rand);

        Thread socialThread = new Thread(() -> {
            try {
                simulation.waitSocialValues(social);
            } catch (InterruptedException e) {
                fail("InterruptedException occurred");
            }
        });

        social.setRand(rand);
        simulation.setnPoliticals(1);
        simulation.getBarrier().setNumWaiting(2);

        socialThread.start();
        simulation.getSocialWait().release();
        socialThread.join();
        socialThread.interrupt();

        assertEquals(1, simulation.getnSocials());
        assertEquals(1, simulation.getnPoliticals());
        assertEquals(1, simulation.getSocialToPaint());
        assertFalse(simulation.isPredictionInProgress());
        assertEquals(40.5, social.getValue(), 0.01);

        verify(rand);
    }

    // 2 social and 0 political
    @Test
    void testWaitSocialValues3() throws InterruptedException {
        Social social = new Social(simulation, "Test Social", "Test", 0, 100);
        SecureRandom rand = createMock(SecureRandom.class);

        expect(rand.nextInt(100, 400)).andReturn(0);
        expect(rand.nextDouble(0, 100)).andReturn(40.5);

        replay(rand);

        Thread socialThread = new Thread(() -> {
            try {
                simulation.waitSocialValues(social);
            } catch (InterruptedException e) {
                fail("InterruptedException occurred");
            }
        });

        social.setRand(rand);
        simulation.setnSocials(1);
        simulation.setnPoliticals(0);
        simulation.setSocialToPaint(1);
        simulation.getBarrier().setNumWaiting(2);

        socialThread.start();
        simulation.getSocialWait().release();
        socialThread.join();
        socialThread.interrupt();

        assertEquals(2, simulation.getnSocials());
        assertEquals(0, simulation.getnPoliticals());
        assertEquals(2, simulation.getSocialToPaint());
        assertFalse(simulation.isPredictionInProgress());
        assertEquals(40.5, social.getValue(), 0.01);

        verify(rand);
    }

    // There are 3 social
    @Test
    void testWaitSocialValues4() throws InterruptedException {
        Social social = new Social(simulation, "Test Social", "Test", 0, 100);
        SecureRandom rand = createMock(SecureRandom.class);

        expect(rand.nextInt(100, 400)).andReturn(0);
        expect(rand.nextDouble(0, 100)).andReturn(40.5);

        replay(rand);

        Thread socialThread = new Thread(() -> {
            try {
                simulation.waitSocialValues(social);
            } catch (InterruptedException e) {
                fail("InterruptedException occurred");
            }
        });
        
        simulation.getBarrier().setNumWaiting(2);
        simulation.setnSocials(2);
        simulation.setSocialToPaint(2);
        social.setRand(rand);

        socialThread.start();
        simulation.getSocialWait().release();
        socialThread.join();
        socialThread.interrupt();

        assertEquals(40.5, social.getValue(), 0.01);
        assertEquals(0, simulation.getnSocials());
        assertEquals(3, simulation.getSocialToPaint());
        assertTrue(simulation.isPredictionInProgress());
        assertEquals(1, simulation.getPredictionWait().availablePermits());

        verify(rand);
    }

    // There are 2 social and 1 political
    @Test
    void testWaitSocialValues5() throws InterruptedException {
        Social social = new Social(simulation, "Test Social", "Test", 0, 100);
        SecureRandom rand = createMock(SecureRandom.class);

        expect(rand.nextInt(100, 400)).andReturn(0);
        expect(rand.nextDouble(0, 100)).andReturn(40.5);

        replay(rand);

        Thread socialThread = new Thread(() -> {
            try {
                simulation.waitSocialValues(social);
            } catch (InterruptedException e) {
                fail("InterruptedException occurred");
            }
        });
        
        simulation.getBarrier().setNumWaiting(2);
        simulation.setnSocials(1);
        simulation.setnPoliticals(1);
        simulation.setSocialToPaint(1);
        simulation.setPoliticalToPaint(1);
        social.setRand(rand);

        socialThread.start();
        simulation.getSocialWait().release();
        socialThread.join();
        socialThread.interrupt();

        assertEquals(40.5, social.getValue(), 0.01);
        assertEquals(0, simulation.getnSocials());
        assertEquals(0, simulation.getnPoliticals());
        assertEquals(2, simulation.getSocialToPaint());
        assertEquals(1, simulation.getPoliticalToPaint());
        assertTrue(simulation.isPredictionInProgress());
        assertEquals(1, simulation.getPredictionWait().availablePermits());

        verify(rand);
    }

    // There are 1 social and 2 politicals
    @Test
    void testWaitSocialValues6() throws InterruptedException {
        Social social = new Social(simulation, "Test Social", "Test", 0, 100);
        SecureRandom rand = createMock(SecureRandom.class);

        expect(rand.nextInt(100, 400)).andReturn(0);
        expect(rand.nextDouble(0, 100)).andReturn(40.5);

        replay(rand);

        Thread socialThread = new Thread(() -> {
            try {
                simulation.waitSocialValues(social);
            } catch (InterruptedException e) {
                fail("InterruptedException occurred");
            }
        });
        
        simulation.getBarrier().setNumWaiting(2);
        simulation.setnSocials(0);
        simulation.setnPoliticals(2);
        simulation.setSocialToPaint(0);
        simulation.setPoliticalToPaint(2);
        social.setRand(rand);

        socialThread.start();
        simulation.getSocialWait().release();
        socialThread.join();
        socialThread.interrupt();

        assertEquals(40.5, social.getValue(), 0.01);
        assertEquals(0, simulation.getnSocials());
        assertEquals(0, simulation.getnPoliticals());
        assertEquals(1, simulation.getSocialToPaint());
        assertEquals(2, simulation.getPoliticalToPaint());
        assertTrue(simulation.isPredictionInProgress());
        assertEquals(1, simulation.getPredictionWait().availablePermits());

        verify(rand);
    }

    // 1 socialToPaint and 0 politicalToPaint
    @Test
    void testWaitSocialPredictionDone1() throws InterruptedException {
        Social social = new Social(simulation, "Test Social", "Test", 0, 100);

        Thread socialThread = new Thread(() -> {
            try {
                simulation.waitSocialPredictionDone(social);
            } catch (InterruptedException e) {
                fail("InterruptedException occurred");
            }
        });

        simulation.getGraphMutex().release();
        simulation.setSocialToPaint(1);
        simulation.setPredictionInProgress(true);

        socialThread.start();
        socialThread.join();
        socialThread.interrupt();

        assertEquals(0, simulation.getSocialToPaint());
        assertEquals(1, simulation.getSocialDone().availablePermits());
        assertFalse(simulation.isPredictionInProgress());
    }

    // 1 socialToPaint and 2 politicalToPaint
    @Test
    void testWaitSocialPredictionDone2() throws InterruptedException {
        Social social = new Social(simulation, "Test Social", "Test", 0, 100);

        Thread socialThread = new Thread(() -> {
            try {
                simulation.waitSocialPredictionDone(social);
            } catch (InterruptedException e) {
                fail("InterruptedException occurred");
            }
        });

        simulation.getGraphMutex().release();
        simulation.setSocialToPaint(1);
        simulation.setPoliticalToPaint(2);
        simulation.setPredictionInProgress(true);

        socialThread.start();
        socialThread.join();
        socialThread.interrupt();

        assertEquals(0, simulation.getSocialToPaint());
        assertEquals(2, simulation.getPoliticalToPaint());
        assertEquals(1, simulation.getSocialDone().availablePermits());
        assertTrue(simulation.isPredictionInProgress());
    }

    // 2 socialToPaint and 1 politicalToPaint
    @Test
    void testWaitSocialPredictionDone3() throws InterruptedException {
        Social social = new Social(simulation, "Test Social", "Test", 0, 100);

        Thread socialThread = new Thread(() -> {
            try {
                simulation.waitSocialPredictionDone(social);
            } catch (InterruptedException e) {
                fail("InterruptedException occurred");
            }
        });

        simulation.getGraphMutex().release();
        simulation.setSocialToPaint(2);
        simulation.setPoliticalToPaint(1);
        simulation.setPredictionInProgress(true);

        socialThread.start();
        socialThread.join();
        socialThread.interrupt();

        assertEquals(1, simulation.getSocialToPaint());
        assertEquals(1, simulation.getPoliticalToPaint());
        assertEquals(1, simulation.getSocialWait().availablePermits());
        assertTrue(simulation.isPredictionInProgress());
    }

    @Test
    void testReleasePoliticals() {
        simulation.setnPoliticals(2);
        simulation.releasePoliticals(2);
        assertEquals(0, simulation.getnPoliticals());
    }

    @Test
    void testReleasePoliticals2() {
        simulation.setnPoliticals(0);
        simulation.releasePoliticals(0);
        assertEquals(0, simulation.getnPoliticals());
    }

    @Test
    void testReleaseSocials() {
        simulation.setnSocials(2);
        simulation.releaseSocials(2);
        assertEquals(0, simulation.getnSocials());
    }

    @Test
    void testReleaseSocials2() {
        simulation.setnSocials(0);
        simulation.releaseSocials(0);
        assertEquals(0, simulation.getnSocials());
    }

    @Test
    void testGetTheMutex1() throws InterruptedException {
        Thread mutexThread = new Thread(() -> {
            try {
                simulation.getTheMutex();
            } catch (InterruptedException e) {
                fail("InterruptedException occurred");
            }
        });

        simulation.setPredictionInProgress(false);

        mutexThread.start();
        mutexThread.join();
        mutexThread.interrupt();

        assertEquals(0, simulation.getMutex().availablePermits());
    }

    @Test
    void testGetTheMutex2() throws InterruptedException {
        Thread mutexThread = new Thread(() -> {
            try {
                simulation.getTheMutex();
            } catch (InterruptedException e) {
                fail("InterruptedException occurred");
            }
        });

        simulation.setPredictionInProgress(true);

        mutexThread.start();
        
        assertEquals(1, simulation.getMutex().availablePermits());
        simulation.setPredictionInProgress(false);
        
        mutexThread.join();
        mutexThread.interrupt();
    }

    @Test
    void testAndGetSetNDowJones() {
        assertEquals(0, simulation.getnDowJones());
        simulation.setnDowJones(5);
        assertEquals(5, simulation.getnDowJones());
    }

    @Test
    void testAndGetSetNEconomics() {
        assertEquals(0, simulation.getnEconomics());
        simulation.setnEconomics(3);
        assertEquals(3, simulation.getnEconomics());
    }

    @Test
    void testAndGetSetNPoliticals() {
        assertEquals(0, simulation.getnPoliticals());
        simulation.setnPoliticals(2);
        assertEquals(2, simulation.getnPoliticals());
    }

    @Test
    void testAndGetSetNSocials() {
        assertEquals(0, simulation.getnSocials());
        simulation.setnSocials(4);
        assertEquals(4, simulation.getnSocials());
    }

    @Test
    void testAndGetSetPoliticalToPaint() {
        assertEquals(0, simulation.getPoliticalToPaint());
        simulation.setPoliticalToPaint(1);
        assertEquals(1, simulation.getPoliticalToPaint());
    }

    @Test
    void testAndGetSetSocialToPaint() {
        assertEquals(0, simulation.getSocialToPaint());
        simulation.setSocialToPaint(3);
        assertEquals(3, simulation.getSocialToPaint());
    }

    @Test
    void testAndGetSetPredictionInProgress() {
        assertFalse(simulation.isPredictionInProgress());
        simulation.setPredictionInProgress(true);
        assertTrue(simulation.isPredictionInProgress());
    }

    @Test
    void testGetSetGraphMutex() {
        Semaphore testSemaphore = new Semaphore(1);
        simulation.setGraphMutex(testSemaphore);
        assertEquals(testSemaphore, simulation.getGraphMutex());
    }

    @Test
    void testGetSetMutex() {
        Semaphore testSemaphore = new Semaphore(1);
        simulation.setMutex(testSemaphore);
        assertEquals(testSemaphore, simulation.getMutex());
    }

    @Test
    void testGetSetDowJonesMutex() {
        Semaphore testSemaphore = new Semaphore(1);
        simulation.setDowJonesMutex(testSemaphore);
        assertEquals(testSemaphore, simulation.getDowJonesMutex());
    }

    @Test
    void testGetSetEconomicMutex() {
        Semaphore testSemaphore = new Semaphore(1);
        simulation.setEconomicMutex(testSemaphore);
        assertEquals(testSemaphore, simulation.getEconomicMutex());
    }

    @Test
    void testGetSetPredictionWait() {
        Semaphore testSemaphore = new Semaphore(1);
        simulation.setPredictionWait(testSemaphore);
        assertEquals(testSemaphore, simulation.getPredictionWait());
    }

    @Test
    void testGetSetDowJonesWait() {
        Semaphore testSemaphore = new Semaphore(1);
        simulation.setDowJonesWait(testSemaphore);
        assertEquals(testSemaphore, simulation.getDowJonesWait());
    }

    @Test
    void testGetSetDowJonesDone() {
        Semaphore testSemaphore = new Semaphore(1);
        simulation.setDowJonesDone(testSemaphore);
        assertEquals(testSemaphore, simulation.getDowJonesDone());
    }

    @Test
    void testGetSetEconomicWait() {
        Semaphore testSemaphore = new Semaphore(1);
        simulation.setEconomicWait(testSemaphore);
        assertEquals(testSemaphore, simulation.getEconomicWait());
    }

    @Test
    void testGetSetEconomicDone() {
        Semaphore testSemaphore = new Semaphore(1);
        simulation.setEconomicDone(testSemaphore);
        assertEquals(testSemaphore, simulation.getEconomicDone());
    }

    @Test
    void testGetSetPoliticalWait() {
        Semaphore testSemaphore = new Semaphore(1);
        simulation.setPoliticalWait(testSemaphore);
        assertEquals(testSemaphore, simulation.getPoliticalWait());
    }

    @Test
    void testGetSetPoliticalDone() {
        Semaphore testSemaphore = new Semaphore(1);
        simulation.setPoliticalDone(testSemaphore);
        assertEquals(testSemaphore, simulation.getPoliticalDone());
    }

    @Test
    void testGetSetSocialWait() {
        Semaphore testSemaphore = new Semaphore(1);
        simulation.setSocialWait(testSemaphore);
        assertEquals(testSemaphore, simulation.getSocialWait());
    }

    @Test
    void testGetSetSocialDone() {
        Semaphore testSemaphore = new Semaphore(1);
        simulation.setSocialDone(testSemaphore);
        assertEquals(testSemaphore, simulation.getSocialDone());
    }

    @Test
    void testGetSetBarrier() {
        ReusableBarrier testBarrier = new ReusableBarrier(3);
        simulation.setBarrier(testBarrier);
        assertEquals(testBarrier, simulation.getBarrier());
    }

    @Test
    void testGetSetValueSender() {
        ValueSender valueSender = new ValueSender();
        simulation.setValueSender(valueSender);
        assertEquals(valueSender, simulation.getValueSender());
    }

    @Test
    void testGetSetGraphValues() {
        List<GraphValue> graphValues = new ArrayList<>();
        simulation.setGraphValues(graphValues);
        assertEquals(graphValues, simulation.getGraphValues());
    }
}
