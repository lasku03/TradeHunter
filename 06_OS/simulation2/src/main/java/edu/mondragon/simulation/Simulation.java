package edu.mondragon.simulation;

import java.util.concurrent.Semaphore;

public class Simulation {

    private int nDowJones;
    private int nEconomics;
    private int nPoliticals;
    private int nSocials;
    private int politicalToPaint;
    private int socialToPaint;
    private boolean predictionInProgress;
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

    public Simulation() {
        this.nDowJones = 0;
        this.nEconomics = 0;
        this.nPoliticals = 0;
        this.nSocials = 0;
        this.politicalToPaint = 0;
        this.socialToPaint = 0;
        this.predictionInProgress = false;
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
            prediction.paintGraph();
        } else if (nEconomics == 3) {
            prediction.makePrediction();
            graphMutex.release();
            economicWait.release();
            economicDone.acquire();
            prediction.paintGraph();
        } else if (politicalToPaint == 3) {
            prediction.makePrediction();
            graphMutex.release();
            politicalWait.release();
            politicalDone.acquire();
            prediction.paintGraph();
        } else if (politicalToPaint == 2 && socialToPaint == 1) {
            prediction.makePrediction();
            graphMutex.release();
            politicalWait.release();
            socialWait.release();
            politicalDone.acquire();
            socialDone.acquire();
            prediction.paintGraph();
        } else if (socialToPaint == 3) {
            prediction.makePrediction();
            graphMutex.release();
            socialWait.release();
            socialDone.acquire();
            prediction.paintGraph();
        } else if (socialToPaint == 2 && politicalToPaint == 1) {
            prediction.makePrediction();
            graphMutex.release();
            socialWait.release();
            politicalWait.release();
            socialDone.acquire();
            politicalDone.acquire();
            prediction.paintGraph();
        }
        mutex.release();
    }

    public void waitDowJonesValues(DowJones dowJones) throws InterruptedException {
        dowJonesMutex.acquire();
        getMutex();

        dowJones.giveValue();
        nDowJones++;
        predictionInProgress = true;
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
        predictionInProgress = false;

        graphMutex.release();
    }

    public void waitEconomicValues(Economic economic) throws InterruptedException {
        economicMutex.acquire();
        getMutex();

        economic.giveValue();
        nEconomics++;
        if (nEconomics == 3) {
            predictionInProgress = true;
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
            predictionInProgress = false;   
        } else {
            economicWait.release();
        }
        graphMutex.release();
    }

    public void waitPoliticalValues(Political political) throws InterruptedException {
        boolean isLast = false;

        getMutex();

        political.giveValue();
        nPoliticals++;
        if (nPoliticals == 3) {
            releasePoliticals(3);
            isLast = true;
            predictionInProgress = true;
        } else if (nPoliticals == 2 && nSocials >= 1) {
            releasePoliticals(2);
            releaseSocials(1);
            isLast = true;
            predictionInProgress = true;
        } else if (nPoliticals == 1 && nSocials >= 2) {
            releasePoliticals(1);
            releaseSocials(2);
            isLast = true;
            predictionInProgress = true;
        } else {
            mutex.release();
        }

        barrier.waitBarrier();      

        politicalToPaint++;

        if ((politicalToPaint == 3) || (politicalToPaint == 2 && socialToPaint == 1) || (politicalToPaint == 1 && socialToPaint == 2)) {    
            predictionWait.release();
        }
        
        if (isLast) {
            mutex.release();
        }

        politicalWait.acquire();
    }

    public void waitPoliticalGraphsPainted(Political political) throws InterruptedException {
        graphMutex.acquire();

        political.paintGraph();
        politicalToPaint--;
        if (politicalToPaint == 0) {
            politicalDone.release();
            if (socialToPaint == 0) {
                predictionInProgress = false;
            }
        } else if (politicalToPaint > 0) {
            politicalWait.release();
        }
        graphMutex.release();
    }

    public void waitSocialValues(Social social) throws InterruptedException {
        boolean isLast = false;

        getMutex();

        social.giveValue();
        nSocials++;
        if (nSocials == 3) {
            releaseSocials(3);
            isLast = true;
            predictionInProgress = true;
        } else if (nSocials == 2 && nPoliticals >= 1) {
            releaseSocials(2);
            releasePoliticals(1);
            isLast = true;
            predictionInProgress = true;
        } else if (nSocials == 1 && nPoliticals >= 2) {
            releaseSocials(1);
            releasePoliticals(2);
            isLast = true;
            predictionInProgress = true;
        } else {
            mutex.release();
        }

        barrier.waitBarrier();
        
        socialToPaint++;

        if ((socialToPaint == 3) || (socialToPaint == 2 && politicalToPaint == 1) || (socialToPaint == 1 && politicalToPaint == 2)) {
            predictionWait.release();
        }
        
        if (isLast) {
            mutex.release();
        }
        
        socialWait.acquire();
    }

    public void waitSocialGraphsPainted(Social social) throws InterruptedException {
        graphMutex.acquire();

        social.paintGraph();
        socialToPaint--;
        if (socialToPaint == 0) {
            socialDone.release();
            if (politicalToPaint == 0) {
                predictionInProgress = false;
            }
        } else if (socialToPaint > 0) {
            socialWait.release();
        }
        graphMutex.release();
    }

    private void releasePoliticals(int n) {
        for (int i = 0; i < n; i++) {
            nPoliticals--;
        }
    }

    private void releaseSocials(int n) {
        for (int i = 0; i < n; i++) {
            nSocials--;
        }
    }

    private void getMutex() throws InterruptedException {
        do {
            mutex.acquire();
            if (predictionInProgress) {
                mutex.release();
                Thread.sleep(100);
            }
        } while(predictionInProgress);
    }
}
