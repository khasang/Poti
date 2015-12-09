package com.ni032mas.poti;

public class WearableTimer {
    private long duration;
    private String vibration;
    private int cycle;
    private int color;
    private String name;
    boolean isLastTimer;

    public WearableTimer() {
    }

    public WearableTimer(String name) {
        this.name = name;
    }

    public long getDuration() {
        return duration;
    }

    public void setDuration(long duration) {
        this.duration = duration;
    }

    public String getVibration() {
        return vibration;
    }

    public void setVibration(String vibration) {
        this.vibration = vibration;
    }

    public int getCycle() {
        return cycle;
    }

    public void setCycle(int cycle) {
        this.cycle = cycle;
    }

    public int getColor() {
        return color;
    }

    public void setColor(int color) {
        this.color = color;
    }

    public String getName() {
        if (name == null || name == "") {
            return convertDuration(duration);
        } else {
            return name;
        }
    }

    public void setName(String name) {
        this.name = name;
    }


    public static String convertDuration(long duration) {
        long hour = duration / 1000 / 60 / 60;
        long minute = (duration / 1000 / 60) > 58 ? (duration / 1000 / 60) % 60 : (duration / 1000 / 60);
        long second = (duration / 1000) > 58 ? (duration / 1000) % 60 : (duration / 1000);
        return (hour > 9 ? hour : "0" + hour) + ":" + (minute > 9 ? minute : "0" + minute) + ":" + (second > 9 ? second : "0" + second);
    }
}
