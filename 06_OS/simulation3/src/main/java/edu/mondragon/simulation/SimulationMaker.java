package edu.mondragon.simulation;

public class SimulationMaker {

    static final int NSOCIALS = 3;
    static final int NECONOMICS = 5;
    static final int NPOLITICALS = 3;

    private Simulation simulation;
    private Social[] socials;
    private Economic[] economics;
    private Political[] politicals;
    private DowJones dowJones;
    private Prediction prediction;

    public SimulationMaker() {
        simulation = new Simulation();

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
        socials[1] = new Social(simulation, "Birth rate", "Births", 1100, 900);
        socials[2] = new Social(simulation, "Debt per capita", "Debt_per_Capita", 25000, 29000);
    }

    public void createEconomicThreads() {
        economics[0] = new Economic(simulation, "Euribor", "Euribor", -1, 1);
        economics[1] = new Economic(simulation, "IPC", "IPC", -2, 1.5);
        economics[2] = new Economic(simulation, "Euro", "Price_EURO", 1.1, 1.3);
        economics[3] = new Economic(simulation, "Debt", "Debt", 1.2, 1.4);
        economics[4] = new Economic(simulation, "Gross Domestic Product", "GDP", -11, 18);
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
    }

    public void waitEndOfThreads() {
        try {
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
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    private void waitForSimulationInterruption() {
        // This will change in the future to be waiting until the user cancels the simulation
        try {
            Thread.sleep(200000);
        } catch (InterruptedException e1) {
            e1.printStackTrace();
        }
    }
    public static void main(String[] args) {
        SimulationMaker app = new SimulationMaker();

        app.createThreads();
        app.startThreads();
        
        app.waitForSimulationInterruption();

        app.interruptThreads();
        app.waitEndOfThreads();
    }
}