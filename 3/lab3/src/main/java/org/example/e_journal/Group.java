package org.example.e_journal;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Group {
    private String groupName;
    private Map<Integer, List<Mark>> students;

    public Group(String groupName, int numOfStudents) {
        this.groupName = groupName;
        this.students = new HashMap<>();
        setGroup(numOfStudents);
    }

    public void setGroup(int numOfStudents) {
        for (int i = 0; i < numOfStudents; i++) {
            students.put(i + 1, new ArrayList<>());
        }
    }

    public String getGroupName() {
        return groupName;
    }

    public Map<Integer, List<Mark>> getStudents() {
        return students;
    }
}
