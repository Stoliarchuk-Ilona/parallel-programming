package org.example;

import java.util.LinkedList;
import java.util.Queue;

public class Buffer {
    private final Queue<Task> queue;
    private final long capacity;

    public Buffer(long capacity) {
        this.capacity = capacity;
        queue = new LinkedList<>();
    }

    public Task take() {
        return queue.poll();
    }

    public void put(Task task) {
        queue.add(task);
    }

    public boolean isFull() {
        return queue.size() == capacity;
    }

    public boolean isEmpty() {
        return queue.isEmpty();
    }

    public int getSize() {
        return queue.size();
    }
}
