package com.mondragon.tradehunter.demo.test_simulation;

import static org.junit.Assert.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;

import org.junit.jupiter.api.Test;

import com.mondragon.tradehunter.demo.simulation.GraphValue;
import com.mondragon.tradehunter.demo.simulation.ValueSender;

class ValueSenderTests {
    
    @Test
    void testConstructor() {
        ValueSender valueSender = new ValueSender();
        assertEquals(3, valueSender.getBlockingQueue().remainingCapacity());
        assertNotNull(valueSender.getRand());
    }

    @Test
    void testPutInQueue() throws InterruptedException {
        ValueSender valueSender = new ValueSender();
        List<GraphValue> graphValues = new ArrayList<>();
        graphValues.add(new GraphValue("TestName", 42.0));

        valueSender.putInQueue(graphValues);

        BlockingQueue<List<GraphValue>> blockingQueue = valueSender.getBlockingQueue();
        
        assertEquals(1, blockingQueue.size());
        List<GraphValue> retrievedValues = blockingQueue.take();
        assertEquals(graphValues.get(0).getName(), retrievedValues.get(0).getName());
        assertEquals(graphValues.get(0).getValue(), retrievedValues.get(0).getValue());
    }

    @Test
    void testSetGetBlockingQueue() {
        ValueSender valueSender = new ValueSender();
        BlockingQueue<List<GraphValue>> newBlockingQueue = new ArrayBlockingQueue<>(5);

        valueSender.setBlockingQueue(newBlockingQueue);

        assertEquals(newBlockingQueue, valueSender.getBlockingQueue());
    }

    @Test
    void testSetGetRand() {
        ValueSender valueSender = new ValueSender();
        SecureRandom rand = new SecureRandom();

        valueSender.setRand(rand);

        assertEquals(rand, valueSender.getRand());
    }
}
