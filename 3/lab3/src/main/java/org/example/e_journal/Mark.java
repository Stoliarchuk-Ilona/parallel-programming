package org.example.e_journal;

public class Mark {
    private double mark;
    private String putBy;

    public Mark(double mark, String putBy) {
        this.mark = mark;
        this.putBy = putBy;
    }

    @Override
    public String toString() {
        return mark + " (" + putBy + ")";
    }
}
