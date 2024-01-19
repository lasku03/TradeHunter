package edu.mondragon.simulation;

import java.util.ArrayList;
import java.util.List;
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
    private ValueSender valueSender;
    private List<GraphValue> graphValues;

    public Simulation(ValueSender valueSender) {
        this.nDowJones = 0;
        this.nEconomics = 0;
        this.nPoliticals = 0;
        this.nSocials = 0;
        this.politicalToPaint = 0;
        this.socialToPaint = 0;
        this.predictionInProgress = false;
        this.mutex = new Semaphore(1, true);
        this.graphMutex = new Semaphore(0, true);
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
        this.valueSender = valueSender;
        this.graphValues = new ArrayList<>();
    }

    public void waitValues(Prediction prediction) throws InterruptedException {
        predictionWait.acquire();
        mutex.acquire();
        
        prediction.makePrediction();
        graphValues.add(new GraphValue(prediction.getName(), prediction.getPredictedValue()));
        graphMutex.release();

        if (nDowJones == 1) {
            dowJonesWait.release();
            dowJonesDone.acquire();
        } else if (nEconomics == 3) {
            economicWait.release();
            economicDone.acquire();
        } else if (politicalToPaint == 3) {
            politicalWait.release();
            politicalDone.acquire();
        } else if (politicalToPaint == 2 && socialToPaint == 1) {
            politicalWait.release();
            socialWait.release();
            politicalDone.acquire();
            socialDone.acquire();
        } else if (socialToPaint == 3) {
            socialWait.release();
            socialDone.acquire();
        } else {
            socialWait.release();
            politicalWait.release();
            socialDone.acquire();
            politicalDone.acquire();
        }

        valueSender.putInQueue(graphValues);
        graphValues.clear();
        mutex.release();
    }

    public void waitDowJonesValues(DowJones dowJones) throws InterruptedException {
        dowJonesMutex.acquire();
        getTheMutex();

        dowJones.giveValue();
        nDowJones++;
        predictionInProgress = true;
        predictionWait.release();

        mutex.release();
        dowJonesWait.acquire();
    }

    public void waitDowJonesPredictionDone(DowJones dowJones) throws InterruptedException {
        graphMutex.acquire();
        
        nDowJones--;
        graphValues.add(new GraphValue(dowJones.getName(), dowJones.getValue()));

        dowJonesDone.release();
        dowJonesMutex.release();
        predictionInProgress = false;

        graphMutex.release();
    }

    public void waitEconomicValues(Economic economic) throws InterruptedException {
        economicMutex.acquire();
        getTheMutex();

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

    public void waitEconomicPredictionDone(Economic economic) throws InterruptedException {
        graphMutex.acquire();
        
        nEconomics--;
        graphValues.add(new GraphValue(economic.getName(), economic.getValue()));
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

        getTheMutex();

        political.giveValue();
        nPoliticals++;
        if (nPoliticals == 3) {
            releasePoliticals(3);
            isLast = true;
            predictionInProgress = true;
        } else if (nPoliticals == 2 && nSocials == 1) {
            releasePoliticals(2);
            releaseSocials(1);
            isLast = true;
            predictionInProgress = true;
        } else if (nPoliticals == 1 && nSocials == 2) {
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

    public void waitPoliticalPredictionDone(Political political) throws InterruptedException {
        graphMutex.acquire();

        politicalToPaint--;
        graphValues.add(new GraphValue(political.getName(), political.getValue()));
        if (politicalToPaint == 0) {
            politicalDone.release();
            if (socialToPaint == 0) {
                predictionInProgress = false;
            }
        } else {
            politicalWait.release();
        }

        graphMutex.release();
    }

    public void waitSocialValues(Social social) throws InterruptedException {
        boolean isLast = false;

        getTheMutex();

        social.giveValue();
        nSocials++;
        if (nSocials == 3) {
            releaseSocials(3);
            isLast = true;
            predictionInProgress = true;
        } else if (nSocials == 2 && nPoliticals == 1) {
            releaseSocials(2);
            releasePoliticals(1);
            isLast = true;
            predictionInProgress = true;
        } else if (nSocials == 1 && nPoliticals == 2) {
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

    public void waitSocialPredictionDone(Social social) throws InterruptedException {
        graphMutex.acquire();

        socialToPaint--;
        graphValues.add(new GraphValue(social.getName(), social.getValue()));
        if (socialToPaint == 0) {
            socialDone.release();
            if (politicalToPaint == 0) {
                predictionInProgress = false;
            }
        } else {
            socialWait.release();
        }
        graphMutex.release();
    }

    public void releasePoliticals(int n) {
        for (int i = 0; i < n; i++) {
            nPoliticals--;
        }
    }

    public void releaseSocials(int n) {
        for (int i = 0; i < n; i++) {
            nSocials--;
        }
    }

    public void getTheMutex() throws InterruptedException {
        do {
            mutex.acquire();
            if (predictionInProgress) {
                mutex.release();
            }
        } while(predictionInProgress);
    }

    public int getnDowJones() {
        return nDowJones;
    }
    public void setnDowJones(int nDowJones) {
        this.nDowJones = nDowJones;
    }
    public int getnEconomics() {
        return nEconomics;
    }
    public void setnEconomics(int nEconomics) {
        this.nEconomics = nEconomics;
    }
    public int getnPoliticals() {
        return nPoliticals;
    }
    public void setnPoliticals(int nPoliticals) {
        this.nPoliticals = nPoliticals;
    }
    public int getnSocials() {
        return nSocials;
    }
    public void setnSocials(int nSocials) {
        this.nSocials = nSocials;
    }
    public int getPoliticalToPaint() {
        return politicalToPaint;
    }
    public void setPoliticalToPaint(int politicalToPaint) {
        this.politicalToPaint = politicalToPaint;
    }
    public int getSocialToPaint() {
        return socialToPaint;
    }
    public void setSocialToPaint(int socialToPaint) {
        this.socialToPaint = socialToPaint;
    }
    public boolean isPredictionInProgress() {
        return predictionInProgress;
    }
    public void setPredictionInProgress(boolean predictionInProgress) {
        this.predictionInProgress = predictionInProgress;
    }
    public Semaphore getGraphMutex() {
        return graphMutex;
    }
    public void setGraphMutex(Semaphore graphMutex) {
        this.graphMutex = graphMutex;
    }
    public Semaphore getDowJonesMutex() {
        return dowJonesMutex;
    }
    public void setDowJonesMutex(Semaphore dowJonesMutex) {
        this.dowJonesMutex = dowJonesMutex;
    }
    public Semaphore getEconomicMutex() {
        return economicMutex;
    }
    public void setEconomicMutex(Semaphore economicMutex) {
        this.economicMutex = economicMutex;
    }
    public Semaphore getPredictionWait() {
        return predictionWait;
    }
    public void setPredictionWait(Semaphore predictionWait) {
        this.predictionWait = predictionWait;
    }
    public Semaphore getDowJonesWait() {
        return dowJonesWait;
    }
    public void setDowJonesWait(Semaphore dowJonesWait) {
        this.dowJonesWait = dowJonesWait;
    }
    public Semaphore getDowJonesDone() {
        return dowJonesDone;
    }
    public void setDowJonesDone(Semaphore dowJonesDone) {
        this.dowJonesDone = dowJonesDone;
    }
    public Semaphore getEconomicWait() {
        return economicWait;
    }
    public void setEconomicWait(Semaphore economicWait) {
        this.economicWait = economicWait;
    }
    public Semaphore getEconomicDone() {
        return economicDone;
    }
    public void setEconomicDone(Semaphore economicDone) {
        this.economicDone = economicDone;
    }
    public Semaphore getPoliticalWait() {
        return politicalWait;
    }
    public void setPoliticalWait(Semaphore politicalWait) {
        this.politicalWait = politicalWait;
    }
    public Semaphore getPoliticalDone() {
        return politicalDone;
    }
    public void setPoliticalDone(Semaphore politicalDone) {
        this.politicalDone = politicalDone;
    }
    public Semaphore getSocialWait() {
        return socialWait;
    }
    public void setSocialWait(Semaphore socialWait) {
        this.socialWait = socialWait;
    }
    public Semaphore getSocialDone() {
        return socialDone;
    }
    public void setSocialDone(Semaphore socialDone) {
        this.socialDone = socialDone;
    }
    public ReusableBarrier getBarrier() {
        return barrier;
    }
    public void setBarrier(ReusableBarrier barrier) {
        this.barrier = barrier;
    }
    public Semaphore getMutex() {
        return mutex;
    }
    public void setMutex(Semaphore mutex) {
        this.mutex = mutex;
    }
}

