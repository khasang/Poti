package io.khasang.poti;

import android.content.Context;

import io.khasang.poti.util.ColorTimer;

public class WearableTimer {
    private long duration;
    private long[] vibration;

    public void setVibrationName(String vibrationName) {
        this.vibrationName = vibrationName;
    }

    private String vibrationName;
    private int cycle;
    private ColorTimer color;
    private String name;

    public void setVibration(long[] vibration, String vibrationName) {
        this.vibration = vibration;
        this.vibrationName = vibrationName;
    }

    public String getVibrationName() {
        return vibrationName;
    }

    boolean isLastTimer;

    public WearableTimer(Context context) {
        this.vibration = getVibratePattern();
        this.vibrationName = context.getResources().getString(R.string.default_label);
    }

    public WearableTimer(String name, Context context) {
        this.name = name;
        this.vibration = getVibratePattern();
        this.vibrationName = context.getResources().getString(R.string.default_label);
    }

    public WearableTimer(ColorTimer color, Context context) {
        this.color = color;
        this.vibration = getVibratePattern();
        this.vibrationName = context.getResources().getString(R.string.default_label);
    }

    public long getDuration() {
        return duration;
    }

    public void setDuration(long duration) {
        this.duration = duration;
    }

    public long[] getVibration() {
        return vibration;
    }

    public int getCycle() {
        return cycle;
    }

    public void setCycle(int cycle) {
        this.cycle = cycle;
    }

    public ColorTimer getColor() {
        return color;
    }

    public void setColor(ColorTimer color) {
        this.color = color;
    }

    public String getName() {
        if (name == null || name == "") {
            return getDurationString(duration);
        } else {
            return name;
        }
    }

    public void setName(String name) {
        this.name = name;
    }


    public static String getDurationString(long duration) {
        long hour = duration / 1000 / 60 / 60;
        long minute = (duration / 1000 / 60) > 58 ? (duration / 1000 / 60) % 60 : (duration / 1000 / 60);
        long second = (duration / 1000) > 58 ? (duration / 1000) % 60 : (duration / 1000);
        return (hour > 9 ? hour : "0" + hour) + ":" + (minute > 9 ? minute : "0" + minute) + ":" + (second > 9 ? second : "0" + second);
    }

    public static long[] getVibratePattern() {
        long[] patternVibrate = new long[30];
        long l = 0;
        for (int i = 0; i < 30; i++) {
            if (i % 2 > 0 || i == 0) {
                l = l + 30;
                patternVibrate[i] = 100 + l;
            } else {
                patternVibrate[i] = 1000;
            }
        }
        return patternVibrate;
    }
}
