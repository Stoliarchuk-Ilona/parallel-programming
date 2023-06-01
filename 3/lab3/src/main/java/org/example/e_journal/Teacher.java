package org.example.e_journal;

import java.util.List;

public class Teacher extends Thread {
    private String name;
    private List<Group> groups;
    private Journal journal;
    private int weeks;

    public Teacher(String name, List<Group> groups, Journal journal, int weeks) {
        this.name = name;
        this.groups = groups;
        this.journal = journal;
        this.weeks = weeks;
    }

    @Override
    public void run() {
        for (int i = 0; i < weeks; i++) {
            for (Group group : groups) {
                for (Integer student : group.getStudents().keySet()) {
                    double mark = (double) (Math.round(100 * Math.random() * 100)) / 100;
                    journal.putMark(group.getGroupName(), student, mark, name);
                }
            }
        }
    }
}
