package com.mondragon.tradehunter.demo.simulation;

import java.security.SecureRandom;

public class Prediction extends Thread {
    private SecureRandom rand;
    private Simulation simulation;
    private Social[] socials;
    private Economic[] economics;
    private Political[] politicals;
    private DowJones dowJones;

    private double predictedValue;

    public Prediction(Simulation simulation, Social[] socials, Economic[] economics, Political[] politicals, DowJones dowJones) {
        super("Prediction");
        this.rand = new SecureRandom();
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

    public void makePrediction() {
        predictedValue = askForPrediction();
        System.out.println("\t" + this.getName() + ": " + predictedValue);
    }

    public double askForPrediction() {
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

    public void paintGraph() {
        // Call to paint the graph with the new value
        System.out.println("\t\t\t(Prediction) painting in graph");
    }

    public Simulation getSimulation() {
        return simulation;
    }

    public void setSimulation(Simulation simulation) {
        this.simulation = simulation;
    }

    public Social[] getSocials() {
        return socials;
    }

    public void setSocials(Social[] socials) {
        this.socials = socials;
    }

    public Economic[] getEconomics() {
        return economics;
    }

    public void setEconomics(Economic[] economics) {
        this.economics = economics;
    }

    public Political[] getPoliticals() {
        return politicals;
    }

    public void setPoliticals(Political[] politicals) {
        this.politicals = politicals;
    }

    public DowJones getDowJones() {
        return dowJones;
    }

    public void setDowJones(DowJones dowJones) {
        this.dowJones = dowJones;
    }

    public SecureRandom getRand() {
        return rand;
    }

    public void setRand(SecureRandom rand) {
        this.rand = rand;
    }

    public double getPredictedValue() {
        return predictedValue;
    }

    public void setPredictedValue(double predictedValue) {
        this.predictedValue = predictedValue;
    }
}
