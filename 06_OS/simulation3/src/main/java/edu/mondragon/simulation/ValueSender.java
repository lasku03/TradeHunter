package edu.mondragon.simulation;

import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;

public class ValueSender extends Thread {

    private BlockingQueue<List<GraphValue>> blockingQueue;
    private SecureRandom rand;

    public ValueSender() {
        this.blockingQueue = new ArrayBlockingQueue<>(3);
        this.rand = new SecureRandom();
    }

    public void putInQueue(List<GraphValue> graphValues) throws InterruptedException {
        List<GraphValue> graphValues2 = new ArrayList<>();
        for (GraphValue graphValue : graphValues) {
            graphValues2.add(new GraphValue(graphValue.getName(), graphValue.getValue()));
        }
        blockingQueue.put(graphValues2);
    }

    @Override
    public void run() {
        while (!this.isInterrupted()) {
            try {
                Thread.sleep(rand.nextInt(1000, 2000));
                List<GraphValue> graphValues = blockingQueue.take();
                sendGraphValues(graphValues);
            } catch (InterruptedException e) {
                this.interrupt();
            }
        }
    }

    public void sendGraphValues(List<GraphValue> graphValues) {
        // SimulationController.sendValues(graphValues);
        System.out.println("\t\tValues painting in graphs:");
        for (GraphValue value : graphValues) {
            System.out.println("\t\t\t" + value.getName() + ": " + value.getValue());
        }
    }

    public BlockingQueue<List<GraphValue>> getBlockingQueue() {
        return blockingQueue;
    }
    public void setBlockingQueue(BlockingQueue<List<GraphValue>> blockingQueue) {
        this.blockingQueue = blockingQueue;
    }
    public SecureRandom getRand() {
        return rand;
    }
    public void setRand(SecureRandom rand) {
        this.rand = rand;
    }
}