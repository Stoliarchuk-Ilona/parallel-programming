package org.example.bouncing_priority;

public class BallThread extends Thread {
    private Ball b;

    public BallThread(Ball ball) {
        b = ball;
    }

    @Override
    public void run() {
        try {
            for (int i = 1; i < 1000000; i++) {
                b.move();
                System.out.println("Thread name = "
                        + Thread.currentThread().getName() + "; Thread priority = "
                        + Thread.currentThread().getPriority());
                Thread.sleep(5);
            }
        } catch (InterruptedException ex) {
        }
    }
}
