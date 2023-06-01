package org.example.e_journal;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Objects;

public class Journal {
    private List<Group> groups;

    public Journal(List<Group> groups) {
        this.groups = groups;
    }

    public void putMark(String groupName, Integer studentName, double mark, String teacherName) {
        Group group = groups.stream()
                .filter(x -> Objects.equals(x.getGroupName(), groupName))
                .findFirst()
                .orElseThrow(NoSuchElementException::new);
        synchronized (group.getStudents().get(studentName)) {
            group.getStudents().get(studentName).add(new Mark(mark, teacherName));
        }
    }

    public void show() {
        for (Group group : groups) {
            System.out.printf("Group name: %6s\n", group.getGroupName());

            List<Integer> sortedStudentNames = group.getStudents().keySet().stream()
                    .sorted()
                    .toList();
            for (Integer studentName : sortedStudentNames) {
                System.out.printf("Student %5s %s", studentName, "-");

                List<Mark> marks = group.getStudents().get(studentName);
                for (Mark mark : marks) {
                    System.out.printf("%25s", mark);
                }
                System.out.println();
            }
            System.out.println();
        }
    }
}
