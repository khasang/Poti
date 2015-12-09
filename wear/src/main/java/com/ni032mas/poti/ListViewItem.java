package com.ni032mas.poti;

public class ListViewItem {
    // Duration in milliseconds.
    long duration;
    // Label to display.
    String label;

    public ListViewItem(String label, long duration) {
        this.label = label;
        this.duration = duration;
    }
    public ListViewItem(String label) {
        this.label = label;
    }

    @Override
    public String toString() {
        return label;
    }
}
