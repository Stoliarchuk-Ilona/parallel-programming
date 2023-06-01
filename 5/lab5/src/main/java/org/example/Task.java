package org.example;

public class Task {
    private final long delay;

    public Task(long delay) {
        this.delay = delay;
    }

    public void execute() throws InterruptedException {
        Thread.sleep(delay);
    }
}
