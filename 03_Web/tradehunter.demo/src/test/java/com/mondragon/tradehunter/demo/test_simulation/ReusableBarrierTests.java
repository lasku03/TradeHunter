package com.mondragon.tradehunter.demo.test_simulation;

import org.junit.jupiter.api.Test;

import com.mondragon.tradehunter.demo.simulation.ReusableBarrier;

import static org.junit.jupiter.api.Assertions.*;

import java.util.concurrent.Semaphore;

class ReusableBarrierTests {

    @Test
    void testConstructor() {
        ReusableBarrier barrier = new ReusableBarrier(5);
        assertEquals(5, barrier.getNumThreads());
        assertEquals(0, barrier.getNumWaiting());
        assertEquals(0, barrier.getTurnstile().getQueueLength());
        assertEquals(0, barrier.getTurnstile2().getQueueLength());
        assertEquals(0, barrier.getMutex().getQueueLength());
    }

    @Test
    void testSetNumThreadsAndGetNumThreads() {
        ReusableBarrier barrier = new ReusableBarrier(5);
        barrier.setNumThreads(6);
        assertEquals(6, barrier.getNumThreads());
    }

    @Test
    void testSetNumWaitingAndGetNumWaiting() {
        ReusableBarrier barrier = new ReusableBarrier(5);
        barrier.setNumWaiting(7);
        assertEquals(7, barrier.getNumWaiting());
    }

    @Test
    void testSetTurnstileAndGetTurnstile() {
        ReusableBarrier barrier = new ReusableBarrier(5);
        Semaphore turnstile = new Semaphore(1);
        barrier.setTurnstile(turnstile);
        assertSame(turnstile, barrier.getTurnstile());
    }

    @Test
    void testSetTurnstile2AndGetTurnstile2() {
        ReusableBarrier barrier = new ReusableBarrier(5);
        Semaphore turnstile2 = new Semaphore(0);
        barrier.setTurnstile2(turnstile2);
        assertSame(turnstile2, barrier.getTurnstile2());
    }

    @Test
    void testSetMutexAndGetMutex() {
        ReusableBarrier barrier = new ReusableBarrier(5);
        Semaphore mutex = new Semaphore(1);
        barrier.setMutex(mutex);
        assertSame(mutex, barrier.getMutex());
    }

    @Test
    void testWaitBarrier() throws InterruptedException {
        int numThreads = 5;
        ReusableBarrier barrier = new ReusableBarrier(numThreads);

        Thread[] threads = new Thread[numThreads];
        for (int i = 0; i < numThreads; i++) {
            threads[i] = new Thread(() -> {
                try {
                    barrier.waitBarrier();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            });
            threads[i].start();
        }

        for (int i = 0; i < numThreads; i++) {
            threads[i].join();
        }

        assertEquals(0, barrier.getNumWaiting());
    }
}
