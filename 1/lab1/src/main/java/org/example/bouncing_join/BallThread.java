package org.example.bouncing_join;

public class BallThread extends Thread {
    private Ball b;

    private BallThread previousThread;

    public BallThread(Ball ball){
        this.b = ball;
        this.previousThread = null;
    }

    public BallThread(Ball ball, BallThread previousThread) {
        this.previousThread = previousThread;
        this.b = ball;
    }

    @Override
    public void run() {
        try {
            if (previousThread != null) {
                previousThread.join();
            }
            for (int i = 1; i < 1000; i++) {
                b.move();
                System.out.println("Thread name = "
                        + Thread.currentThread().getName());
                Thread.sleep(5);
            }
        } catch (InterruptedException ex) {
        }
    }
}
