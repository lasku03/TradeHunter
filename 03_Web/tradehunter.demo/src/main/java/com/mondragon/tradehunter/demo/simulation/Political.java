package com.mondragon.tradehunter.demo.simulation;

import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.List;

import com.mondragon.tradehunter.demo.controllers.SimulationController;

public class Political extends Thread {

    private Simulation simulation;
    private SecureRandom rand;
    private double min;
    private double max;
    private double value;

    public Political(Simulation simulation, String name, double min, double max) {
        super(name);
        this.simulation = simulation;
        this.rand = new SecureRandom();
        this.min = min;
        this.max = max;
        this.value = (max + min) / 2;
    }

    @Override
    public void run() {
        while (!this.isInterrupted()) {
            try {
                Thread.sleep(rand.nextInt(1000, 5000));
                simulation.waitPoliticalValues(this);
                simulation.waitPoliticalGraphsPainted(this);
            } catch (Exception e) {
                this.interrupt();
            }
        }
    }

    public void giveValue() throws InterruptedException {
        value = rand.nextDouble(min, max);
        Thread.sleep(rand.nextInt(100, 400));
    }

    public void paintGraph() throws Exception {
        // Call to paint the graph with the new value
        List<GraphValue> graphValues = new ArrayList<>();
        graphValues.add(new GraphValue(getName(), value));

        SimulationController.sendValues(graphValues);
        Thread.sleep(rand.nextInt(100, 400));
    }

    public double getValue() {
        return value;
    }

    public void setValue(double value) {
        this.value = value;
    }

    public double getMin() {
        return min;
    }

    public void setMin(double min) {
        this.min = min;
    }

    public double getMax() {
        return max;
    }

    public void setMax(double max) {
        this.max = max;
    }

    public Simulation getSimulation() {
        return simulation;
    }

    public void setSimulation(Simulation simulation) {
        this.simulation = simulation;
    }

    public SecureRandom getRand() {
        return rand;
    }

    public void setRand(SecureRandom rand) {
        this.rand = rand;
    }
}
