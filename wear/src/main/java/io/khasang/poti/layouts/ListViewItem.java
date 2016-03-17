package io.khasang.poti.layouts;

public class ListViewItem {
    public long duration;
    public String label;

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
