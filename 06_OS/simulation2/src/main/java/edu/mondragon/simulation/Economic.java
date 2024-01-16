package edu.mondragon.simulation;

import java.util.Random;

public class Economic extends Thread {

    private Simulation simulation;
    private Random rand;
    private double min;
    private double max;
    private double value;

    public Economic(Simulation simulation, String name, double min, double max) {
        super(name);
        this.simulation = simulation;
        this.rand = new Random();
        this.min = min;
        this.max = max;
        this.value = max + min / 2;
    }

    @Override
    public void run() {
        while (!this.isInterrupted()) {
            try {
                Thread.sleep(rand.nextInt(1000, 5000));
                simulation.waitEconomicValues(this);
                simulation.waitEconomicGraphsPainted(this);
            } catch (InterruptedException e) {
                this.interrupt();
            }
        }
    }

    public void giveValue() throws InterruptedException {
        value = rand.nextDouble(min, max);
        System.out.println("(Economic) " + this.getName() + "'s value: " + value);
        Thread.sleep(rand.nextInt(100, 400));
    }

    public void paintGraph() throws InterruptedException {
        // Call to paint the graph with the new value
        System.out.println("\t\t(Economic) " + this.getName() + " painting in graph");
        Thread.sleep(rand.nextInt(100, 400));
    }

    public double getValue() {
        return value;
    }
}
