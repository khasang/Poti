package com.ni032mas.poti;

/**
 * Created by aleksandr.marmyshev on 12.11.2015.
 */
public class ListViewItem {
    // Duration in milliseconds.
    long duration;
    // Label to display.
    String label;

    public ListViewItem(String label, long duration) {
        this.label = label;
        this.duration = duration;
    }

    @Override
    public String toString() {
        return label;
    }
}
