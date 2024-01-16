package edu.mondragon.simulation;

import java.util.concurrent.Semaphore;

public class Simulation {

    private int nDowJones;
    private int nEconomics;
    private int nPoliticals;
    private int nSocials;
    private Semaphore mutex;
    private Semaphore graphMutex;
    private Semaphore dowJonesMutex;
    private Semaphore economicMutex;
    private Semaphore politicalMutex;
    private Semaphore socialMutex;
    private Semaphore predictionWait;
    private Semaphore dowJonesWait;
    private Semaphore dowJonesDone;
    private Semaphore economicWait;
    private Semaphore economicDone;
    private Semaphore politicalWait;
    private Semaphore politicalDone;
    private Semaphore socialWait;
    private Semaphore socialDone;

    public Simulation() {
        this.nDowJones = 0;
        this.nEconomics = 0;
        this.nPoliticals = 0;
        this.nSocials = 0;
        this.mutex = new Semaphore(1, true);
        this.graphMutex = new Semaphore(1, true);
        this.dowJonesMutex = new Semaphore(1);
        this.economicMutex = new Semaphore(1);
        this.politicalMutex = new Semaphore(1);
        this.socialMutex = new Semaphore(1);
        this.predictionWait = new Semaphore(0);
        this.dowJonesWait = new Semaphore(0);
        this.dowJonesDone = new Semaphore(0);
        this.economicWait = new Semaphore(0, true);
        this.economicDone = new Semaphore(0);
        this.politicalWait = new Semaphore(0, true);
        this.politicalDone = new Semaphore(0);
        this.socialWait = new Semaphore(0, true);
        this.socialDone = new Semaphore(0);
    }

    public void waitValues(Prediction prediction) throws InterruptedException {
        predictionWait.acquire();
        mutex.acquire();
        graphMutex.acquire();
        if (nDowJones == 1) {
            prediction.makePrediction();
            graphMutex.release();
            dowJonesWait.release();
            dowJonesDone.acquire();
        } else if (nEconomics == 3) {
            prediction.makePrediction();
            graphMutex.release();
            economicWait.release();
            economicDone.acquire();
        } else if (nPoliticals == 2) {
            prediction.makePrediction();
            graphMutex.release();
            politicalWait.release();
            politicalDone.acquire();
        } else if (nSocials == 2) {
            prediction.makePrediction();
            graphMutex.release();
            socialWait.release();
            socialDone.acquire();
        }
        mutex.release();
    }

    public void waitDowJonesValues(DowJones dowJones) throws InterruptedException {
        dowJonesMutex.acquire();
        mutex.acquire();

        dowJones.giveValue();
        nDowJones++;
        predictionWait.release();

        mutex.release();
        dowJonesWait.acquire();
    }

    public void waitDowJonesGraphPainted(DowJones dowJones) throws InterruptedException {
        graphMutex.acquire();

        dowJones.paintGraph();
        nDowJones--;

        dowJonesDone.release();
        dowJonesMutex.release();

        graphMutex.release();
    }

    public void waitEconomicValues(Economic economic) throws InterruptedException {
        economicMutex.acquire();
        mutex.acquire();

        economic.giveValue();
        nEconomics++;
        if (nEconomics == 3) {
            predictionWait.release();
        } else {
            economicMutex.release();
        }

        mutex.release();
        economicWait.acquire();
    }

    public void waitEconomicGraphsPainted(Economic economic) throws InterruptedException {
        graphMutex.acquire();
        
        economic.paintGraph();
        nEconomics--;
        if (nEconomics == 0) {
            economicDone.release();
            economicMutex.release();
        } else {
            economicWait.release();
        }
        graphMutex.release();
    }

    public void waitPoliticalValues(Political political) throws InterruptedException {
        politicalMutex.acquire();
        mutex.acquire();

        political.giveValue();
        nPoliticals++;
        if (nPoliticals == 2) {
            predictionWait.release();
        } else {
            politicalMutex.release();
        }

        mutex.release();
        politicalWait.acquire();
    }

    public void waitPoliticalGraphsPainted(Political political) throws InterruptedException {
        graphMutex.acquire();

        political.paintGraph();
        nPoliticals--;
        if (nPoliticals == 0) {
            politicalDone.release();
            politicalMutex.release();
        } else {
            politicalWait.release();
        }
        graphMutex.release();
    }

    public void waitSocialValues(Social social) throws InterruptedException {
        socialMutex.acquire();
        mutex.acquire();

        social.giveValue();
        nSocials++;
        if (nSocials == 2) {
            predictionWait.release();
        } else {
            socialMutex.release();
        }

        mutex.release();
        socialWait.acquire();
    }

    public void waitSocialGraphsPainted(Social social) throws InterruptedException {
        graphMutex.acquire();

        social.paintGraph();
        nSocials--;
        if (nSocials == 0) {
            socialDone.release();
            socialMutex.release();
        } else {
            socialWait.release();
        }
        graphMutex.release();
    }
}
