package edu.mondragon.simulation;

import java.util.Random;

public class Prediction extends Thread {
    private Random rand;
    private Simulation simulation;
    private Social[] socials;
    private Economic[] economics;
    private Political[] politicals;
    private DowJones dowJones;
    private double predictedValue;

    public Prediction(Simulation simulation, Social[] socials, Economic[] economics, Political[] politicals, DowJones dowJones) {
        super("Prediction");
        this.rand = new Random();
        this.simulation = simulation;
        this.socials = socials;
        this.economics = economics;
        this.politicals = politicals;
        this.dowJones = dowJones;
        this.predictedValue = 0;
    }

    @Override
    public void run() {
        while (!this.isInterrupted()) {
            try {
                simulation.waitValues(this);
            } catch (InterruptedException e) {
                this.interrupt();
            }
        }
    }

    public void makePrediction() throws InterruptedException {
        predictedValue = askForPrediction();
        System.out.println("\t" + this.getName() + ": " + predictedValue);
    }

    private double askForPrediction() {
        // This will change once the predictedValue can be made
        double value = 0;
        for (int i = 0; i < socials.length; i++) {
            value += socials[i].getValue();
        }
        for (int i = 0; i < economics.length; i++) {
            value += economics[i].getValue();
        }
        for (int i = 0; i < politicals.length; i++) {
            value += politicals[i].getValue();
        }
        value += dowJones.getValue();
        // Ask the AI server for the prediction
        return value;
    }

    public void paintGraph() throws InterruptedException {
        // Call to paint the graph with the new value
        System.out.println("\t\t\t(Prediction) painting in graph");
        Thread.sleep(rand.nextInt(100, 400));
    }

    public double getPredictedValue() {
        return predictedValue;
    }
    public void setPredictedValue(double predictedValue) {
        this.predictedValue = predictedValue;
    }
}
