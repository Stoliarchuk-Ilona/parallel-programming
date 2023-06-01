package org.example.e_journal;

import java.util.ArrayList;
import java.util.List;

public class Main {
    public static void main(String[] args) throws InterruptedException {
        List<Group> groups = new ArrayList<>(3);
        groups.add(new Group("ІТ-01", 19));
        groups.add(new Group("ІТ-02", 26));
        groups.add(new Group("ІТ-03", 22));

        Journal journal = new Journal(groups);
        int weeks = 3;

        Teacher[] teachers = new Teacher[4];
        teachers[0] = new Teacher("Lecturer", groups, journal, weeks);
        teachers[1] = new Teacher("Assistant 1", groups, journal, weeks);
        teachers[2] = new Teacher("Assistant 2", groups, journal, weeks);
        teachers[3] = new Teacher("Assistant 3", groups, journal, weeks);

        for (int i = 0; i < teachers.length; i++) {
            teachers[i].start();
        }

        for (int i = 0; i < teachers.length; i++) {
            teachers[i].join();
        }

        journal.show();
    }
}
