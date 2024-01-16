package edu.mondragon.simulation;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;

public class ValueSender {
    public static void main(String[] args) {
        // Crear una cola bloqueante con capacidad de 5 elementos
        BlockingQueue<String> blockingQueue = new ArrayBlockingQueue<>(5);

        // Hilo productor
        Thread producerThread = new Thread(() -> {
            try {
                while (true) {
                    blockingQueue.put("Dato 1");
                    blockingQueue.put("Dato 2");
                    blockingQueue.put("Dato 3");
                    Thread.sleep(100);
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });

        // Hilo consumidor
        Thread consumerThread = new Thread(() -> {
            try {
                while (true) {
                    String data = blockingQueue.take();
                    System.out.println("Dato recibido: " + data);
                    Thread.sleep(1000);
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });

        // Iniciar los hilos
        producerThread.start();
        consumerThread.start();

        try {
            Thread.sleep(10000);
            producerThread.join();
            producerThread.interrupt();
            consumerThread.join();
            consumerThread.interrupt();
        } catch (InterruptedException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

    }
}