package com.ni032mas.poti;

/**
 * Created by aleksandr.marmyshev on 26.11.2015.
 */
public class AppData {
    WearableTimer[] wearableTimers;
    WearableTimer lastTimer;

    public AppData() {
        wearableTimers = new WearableTimer[2];
        wearableTimers[0].setName("Timer1");
        wearableTimers[1].setName("Timer1");
        lastTimer = wearableTimers[1];
    }
}
