package io.ni032mas.poti;

public class ListViewItem {
    long duration;
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
