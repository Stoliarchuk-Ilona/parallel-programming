package org.example.bouncing_priority;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.Iterator;

public class BallCanvas extends JPanel {
    private ArrayList<Ball> balls = new ArrayList<>();

    public void add(Ball b) {
        this.balls.add(b);
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D) g;
        for (Iterator<Ball> iterator = balls.iterator(); iterator.hasNext(); ) {
            Ball b = iterator.next();
            b.draw(g2);
        }
    }
}
