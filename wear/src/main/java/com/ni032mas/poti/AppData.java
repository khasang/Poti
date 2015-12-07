package com.ni032mas.poti;

import java.util.ArrayList;

/**
 * Created by aleksandr.marmyshev on 03.12.2015.
 */
public class AppData {
    ArrayList<WearableTimer> timers;

    WearableTimer getLastTimer() {
        for (WearableTimer timer : timers) {
            if (timer.isLastTimer) {
                return timer;
            }
        }
        return null;
    }

    public void setLastTimer(int i) {
        for (WearableTimer timer : timers) {
            timer.isLastTimer = false;
        }
        this.timers.get(i).isLastTimer = true;
    }
}
