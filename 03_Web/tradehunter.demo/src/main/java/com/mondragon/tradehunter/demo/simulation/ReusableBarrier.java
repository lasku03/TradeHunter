package com.mondragon.tradehunter.demo.simulation;

import java.util.concurrent.Semaphore;

public class ReusableBarrier {

    private int numThreads;
    private int numWaiting;

    private Semaphore turnstile;
    private Semaphore turnstile2;
    private Semaphore mutex;

    public ReusableBarrier(int numThreads) {
        this.numThreads = numThreads;
        this.numWaiting = 0;
        this.turnstile = new Semaphore(1);
        this.turnstile2 = new Semaphore(0);
        this.mutex = new Semaphore(1);
    }

    public void waitBarrier() throws InterruptedException {

        this.turnstile.acquire();
        this.turnstile.release();

        this.mutex.acquire();
        this.numWaiting++;
        if (numWaiting == numThreads) {
            this.turnstile.acquire();
            this.turnstile2.release();
        }
        this.mutex.release();

        this.turnstile2.acquire();
        this.turnstile2.release();

        this.mutex.acquire();
        this.numWaiting--;
        if (numWaiting == 0) {
            this.turnstile2.acquire();
            this.turnstile.release();
        }
        this.mutex.release();
    }

    public int getNumWaiting() {
        return numWaiting;
    }

    public void setNumWaiting(int numWaiting) {
        this.numWaiting = numWaiting;
    }

    public int getNumThreads() {
        return numThreads;
    }

    public void setNumThreads(int numThreads) {
        this.numThreads = numThreads;
    }

    public Semaphore getTurnstile() {
        return turnstile;
    }

    public void setTurnstile(Semaphore turnstile) {
        this.turnstile = turnstile;
    }

    public Semaphore getTurnstile2() {
        return turnstile2;
    }

    public void setTurnstile2(Semaphore turnstile2) {
        this.turnstile2 = turnstile2;
    }

    public Semaphore getMutex() {
        return mutex;
    }

    public void setMutex(Semaphore mutex) {
        this.mutex = mutex;
    }
}
