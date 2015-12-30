package io.khasang.poti;

import java.util.ArrayList;

public class AppData {
    ArrayList<WearableTimer> timers;
    int positionAdditionalOptions;
    boolean isTimerStart;

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

    public int getIndexLastTimer() {
        for (int i = 0; i < timers.size(); i++) {
            if (timers.get(i).isLastTimer) {
                return i;
            }
        }
        return 0;
    }

    public WearableTimer getTimer(int i) {
        return this.timers.get(i);
    }
}
