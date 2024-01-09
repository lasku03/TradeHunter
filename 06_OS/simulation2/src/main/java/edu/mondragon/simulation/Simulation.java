package edu.mondragon.simulation;

import java.util.concurrent.Semaphore;

public class Simulation {

    private int nDowJones;
    private int nEconomics;
    private int nPoliticals;
    private int nSocials;
    private int politicalToPaint;
    private int socialToPaint;
    private Semaphore mutex;
    private Semaphore graphMutex;
    private Semaphore dowJonesMutex;
    private Semaphore economicMutex;
    private Semaphore predictionWait;
    private Semaphore dowJonesWait;
    private Semaphore dowJonesDone;
    private Semaphore economicWait;
    private Semaphore economicDone;
    private Semaphore politicalWait;
    private Semaphore politicalDone;
    private Semaphore socialWait;
    private Semaphore socialDone;
    private ReusableBarrier barrier;
    private Semaphore politicalQueue;
    private Semaphore socialQueue;

    public Simulation() {
        this.nDowJones = 0;
        this.nEconomics = 0;
        this.nPoliticals = 0;
        this.nSocials = 0;
        this.politicalToPaint = 0;
        this.socialToPaint = 0;
        this.mutex = new Semaphore(1, true);
        this.graphMutex = new Semaphore(1, true);
        this.dowJonesMutex = new Semaphore(1);
        this.economicMutex = new Semaphore(1);
        this.predictionWait = new Semaphore(0);
        this.dowJonesWait = new Semaphore(0);
        this.dowJonesDone = new Semaphore(0);
        this.economicWait = new Semaphore(0, true);
        this.economicDone = new Semaphore(0);
        this.politicalWait = new Semaphore(0, true);
        this.politicalDone = new Semaphore(0);
        this.socialWait = new Semaphore(0, true);
        this.socialDone = new Semaphore(0);
        this.politicalQueue = new Semaphore(0);
        this.socialQueue = new Semaphore(0);
        this.barrier = new ReusableBarrier(3);
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
        } else if (politicalToPaint == 3) {
            prediction.makePrediction();
            graphMutex.release();
            politicalWait.release();
            politicalDone.acquire();
        } else if (politicalToPaint == 2 && socialToPaint == 1) {
            prediction.makePrediction();
            graphMutex.release();
            politicalWait.release();
            socialWait.release();
            politicalDone.acquire();
            socialDone.acquire();
        } else if (socialToPaint == 3) {
            prediction.makePrediction();
            graphMutex.release();
            socialWait.release();
            socialDone.acquire();
        } else if (socialToPaint == 2 && politicalToPaint == 1) {
            prediction.makePrediction();
            graphMutex.release();
            socialWait.release();
            politicalWait.release();
            socialDone.acquire();
            politicalDone.acquire();
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
        boolean isLast = false;

        mutex.acquire();

        political.giveValue();
        nPoliticals++;
        if (nPoliticals == 3) {
            releasePoliticals(3);
            isLast = true;
        } else if (nPoliticals == 2 && nSocials >= 1) {
            releasePoliticals(2);
            releaseSocials(1);
            isLast = true;
        } else if (nPoliticals == 1 && nSocials >= 2) {
            releasePoliticals(1);
            releaseSocials(2);
            isLast = true;
        } else {
            mutex.release();
        }

        //politicalQueue.acquire();

        barrier.waitBarrier();

        if (!isLast) {
            mutex.acquire();
        }

        politicalToPaint++;

        if ((politicalToPaint == 3) || (politicalToPaint == 2 && socialToPaint == 1) || (politicalToPaint == 1 && socialToPaint == 2)) {
            predictionWait.release();
        }
        
        mutex.release();

        politicalWait.acquire();
    }

    public void waitPoliticalGraphsPainted(Political political) throws InterruptedException {
        graphMutex.acquire();

        political.paintGraph();
        politicalToPaint--;
        if (politicalToPaint == 0) {
            politicalDone.release();
        } else if (politicalToPaint > 0) {
            politicalWait.release();
        }
        graphMutex.release();
    }

    public void waitSocialValues(Social social) throws InterruptedException {
        boolean isLast = false;

        mutex.acquire();

        social.giveValue();
        nSocials++;
        if (nSocials == 3) {
            releaseSocials(3);
            isLast = true;
        } else if (nSocials == 2 && nPoliticals >= 1) {
            releaseSocials(2);
            releasePoliticals(1);
            isLast = true;
        } else if (nSocials == 1 && nPoliticals >= 2) {
            releaseSocials(1);
            releasePoliticals(2);
            isLast = true;
        } else {
            mutex.release();
        }

        //socialQueue.acquire();

        barrier.waitBarrier();

        if (!isLast) {
            mutex.acquire();
        }

        socialToPaint++;

        if ((socialToPaint == 3) || (socialToPaint == 2 && politicalToPaint == 1) || (socialToPaint == 1 && politicalToPaint == 2)) {
            predictionWait.release();
        }
        
        mutex.release();
        
        socialWait.acquire();
    }

    public void waitSocialGraphsPainted(Social social) throws InterruptedException {
        graphMutex.acquire();

        social.paintGraph();
        socialToPaint--;
        if (socialToPaint == 0) {
            socialDone.release();
        } else if (socialToPaint > 0) {
            socialWait.release();
        }
        graphMutex.release();
    }

    private void releasePoliticals(int n) {
        for (int i = 0; i < n; i++) {
            politicalQueue.release();
            nPoliticals--;
        }
    }

    private void releaseSocials(int n) {
        for (int i = 0; i < n; i++) {
            socialQueue.release();
            nSocials--;
        }
    }
}
