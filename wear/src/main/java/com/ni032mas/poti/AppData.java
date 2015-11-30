package com.ni032mas.poti;

import java.util.ArrayList;

/**
 * Created by aleksandr.marmyshev on 26.11.2015.
 */
public class AppData {
    public ArrayList<WearableTimer> wearableTimers;
    public WearableTimer lastTimer;

    public AppData() {
        wearableTimers = new ArrayList<>();
        lastTimer = new WearableTimer();
//        wearableTimers = new WearableTimer[2];
//        wearableTimers[0].setName("Timer1");
//        wearableTimers[1].setName("Timer1");
//        lastTimer = wearableTimers[1];
    }
}
