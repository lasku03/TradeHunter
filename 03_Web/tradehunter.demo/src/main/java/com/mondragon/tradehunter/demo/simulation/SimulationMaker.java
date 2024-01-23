package com.mondragon.tradehunter.demo.simulation;

public class SimulationMaker {

    public static final int NSOCIALS = 3;
    public static final int NECONOMICS = 5;
    public static final int NPOLITICALS = 3;

    private Simulation simulation;
    private Social[] socials;
    private Economic[] economics;
    private Political[] politicals;
    private DowJones dowJones;
    private Prediction prediction;
    private ValueSender valueSender;

    public SimulationMaker() {
        valueSender = new ValueSender();
        simulation = new Simulation(valueSender);

        socials = new Social[NSOCIALS];
        economics = new Economic[NECONOMICS];
        politicals = new Political[NPOLITICALS];
    }

    public void createThreads() {
        createSocialThreads();
        createEconomicThreads();
        createPoliticalThreads();
        dowJones = new DowJones(simulation, "High_DJ", 22500, 32000);
        prediction = new Prediction(simulation, socials, economics, politicals, dowJones);
    }

    public void createSocialThreads() {
        socials[0] = new Social(simulation, "Death rate", "Defunciones", 6, 12);
        socials[1] = new Social(simulation, "Birth rate", "Births", 900, 1100);
        socials[2] = new Social(simulation, "Debt per capita", "Debt_per_capita", 25000, 29000);
    }

    public void createEconomicThreads() {
        economics[0] = new Economic(simulation, "Euribor", "Euribor", -1, 1);
        economics[1] = new Economic(simulation, "IPC", "IPC", -2, 1.5);
        economics[2] = new Economic(simulation, "Euro", "Price_EUR", 1.1, 1.3);
        economics[3] = new Economic(simulation, "Debt", "Total_debt", 1.2, 1.4);
        economics[4] = new Economic(simulation, "Gross Domestic Product", "GDP_Value", -11, 18);
    }

    public void createPoliticalThreads() {
        politicals[0] = new Political(simulation, "Activity rate", "Activos", 22000, 23500);
        politicals[1] = new Political(simulation, "Unemployment rate", "Parados", 3500, 5000);
        politicals[2] = new Political(simulation, "Employment rate", "Ocupados",18000, 20000);
    }

    public void startThreads() {
        for (int i = 0; i < NSOCIALS; i++) {
            socials[i].start();
        }
        for (int i = 0; i < NECONOMICS; i++) {
            economics[i].start();
        }
        for (int i = 0; i < NPOLITICALS; i++) {
            politicals[i].start();
        }
        dowJones.start();
        prediction.start();
        valueSender.start();
    }

    public void interruptThreads() {
        for (int i = 0; i < NSOCIALS; i++) {
            socials[i].interrupt();
        }
        for (int i = 0; i < NECONOMICS; i++) {
            economics[i].interrupt();
        }
        for (int i = 0; i < NPOLITICALS; i++) {
            politicals[i].interrupt();
        }
        dowJones.interrupt();
        prediction.interrupt();
        valueSender.interrupt();
    }

    public void waitEndOfThreads() throws InterruptedException {
        for (int i = 0; i < NSOCIALS; i++) {
            socials[i].join();
        }
        for (int i = 0; i < NECONOMICS; i++) {
            economics[i].join();
        }
        for (int i = 0; i < NPOLITICALS; i++) {
            politicals[i].join();
        }
        dowJones.join();
        prediction.join();
        valueSender.join();
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

    public Prediction getPrediction() {
        return prediction;
    }

    public void setPrediction(Prediction prediction) {
        this.prediction = prediction;
    }

    public void makeSimulation() {
        createThreads();
        startThreads();
    }

    public void stopSimulation() throws InterruptedException {
        interruptThreads();
        waitEndOfThreads();
    }

}