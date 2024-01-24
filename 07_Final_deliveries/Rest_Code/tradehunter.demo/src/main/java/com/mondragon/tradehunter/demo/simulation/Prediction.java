package com.mondragon.tradehunter.demo.simulation;

import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.List;

import com.mondragon.tradehunter.demo.controllers.SimulationController;

public class Prediction extends Thread {
    private SecureRandom rand;
    private Simulation simulation;
    private Social[] socials;
    private Economic[] economics;
    private Political[] politicals;
    private DowJones dowJones;
    private List<GraphValue> values;

    private double predictedValue;

    public Prediction(Simulation simulation, Social[] socials, Economic[] economics, Political[] politicals,
            DowJones dowJones) {
        super("Prediction");
        this.rand = new SecureRandom();
        this.simulation = simulation;
        this.socials = socials;
        this.economics = economics;
        this.politicals = politicals;
        this.dowJones = dowJones;
        this.values = new ArrayList<>();
        this.predictedValue = 0;
    }

    @Override
    public void run() {
        while (!this.isInterrupted()) {
            try {
                simulation.waitValues(this);
            } catch (Exception e) {
                this.interrupt();
            }
        }
    }

    public void makePrediction() throws InterruptedException {
        values.clear();
        for (int i = 0; i < socials.length; i++) {
            values.add(new GraphValue(socials[i].getDbName(), socials[i].getValue()));
        }
        for (int i = 0; i < economics.length; i++) {
            values.add(new GraphValue(economics[i].getDbName(), economics[i].getValue()));
        }
        for (int i = 0; i < politicals.length; i++) {
            values.add(new GraphValue(politicals[i].getDbName(), politicals[i].getValue()));
        }
        values.add(new GraphValue(dowJones.getDbName(), dowJones.getValue()));
        
        predictedValue = askForPrediction(values);
    }

    public double askForPrediction(List<GraphValue> values) throws InterruptedException {
        // Ask the AI server for the prediction
        return SimulationController.sendPredictionValues(values);
        
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

    public List<GraphValue> getValues() {
        return values;
    }
    public void setValues(List<GraphValue> values) {
        this.values = values;
    }

    public double getPredictedValue() {
        return predictedValue;
    }

    public void setPredictedValue(double predictedValue) {
        this.predictedValue = predictedValue;
    }
}
