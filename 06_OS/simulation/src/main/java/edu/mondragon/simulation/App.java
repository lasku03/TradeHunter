package edu.mondragon.simulation;

public class App {

    static final int NSOCIALS = 3;
    static final int NECONOMICS = 5;
    static final int NPOLITICALS = 3;

    private Simulation simulation;
    private Social[] socials;
    private Economic[] economics;
    private Political[] politicals;
    private DowJones dowJones;
    private Prediction prediction;

    public App() {
        simulation = new Simulation();

        socials = new Social[NSOCIALS];
        economics = new Economic[NECONOMICS];
        politicals = new Political[NPOLITICALS];
    }

    public void createThreads() {
        createSocialThreads();
        createEconomicThreads();
        createPoliticalThreads();
        dowJones = new DowJones(simulation, 20000, 38000);
        prediction = new Prediction(simulation, socials, economics, politicals, dowJones);
    }

    public void createSocialThreads() {
        socials[0] = new Social(simulation, "Death rate", 8, 10);
        socials[1] = new Social(simulation, "Birth rate", 7.5, 11.5);
        socials[2] = new Social(simulation, "Migration rate", 0, 14);
    }

    public void createEconomicThreads() {
        economics[0] = new Economic(simulation, "Unemployment rate", 8, 10);
        economics[1] = new Economic(simulation, "Employment rate", 8, 10);
        economics[2] = new Economic(simulation, "Euro", 7.5, 11.5);
        economics[3] = new Economic(simulation, "Inflation rate", 0, 14);
        economics[4] = new Economic(simulation, "Gross Domestic Product", 0, 14);
    }

    public void createPoliticalThreads() {
        politicals[0] = new Political(simulation, "Interest rate", 3, 9);
        politicals[1] = new Political(simulation, "Tariff rate", 4, 21);
        politicals[2] = new Political(simulation, "Public deficit", -8, 0);
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
            Thread.sleep(20000);
        } catch (InterruptedException e1) {
            e1.printStackTrace();
        }
    }
    public static void main(String[] args) {
        App app = new App();

        app.createThreads();
        app.startThreads();
        
        app.waitForSimulationInterruption();

        app.interruptThreads();
        app.waitEndOfThreads();
    }
}