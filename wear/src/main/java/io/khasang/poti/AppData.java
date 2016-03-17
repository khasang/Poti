package io.khasang.poti;

import android.content.Context;

import java.util.ArrayList;

import io.khasang.poti.json.SaveLoadDataJSON;
import io.khasang.poti.util.ColorTimer;

public class AppData {
    public ArrayList<WearableTimer> timers;
    public int positionAdditionalOptions;

    private static AppData appData;

    public static AppData getInstance(Context context) {
        if (appData == null) {
            SaveLoadDataJSON dataJSON = new SaveLoadDataJSON(context);
            dataJSON.appData = new AppData();
            appData = (AppData) dataJSON.loadJSON();
            if (appData == null) {
                appData = new AppData();
            }
            if (appData.timers == null) {
                appData.timers = new ArrayList<>();
                for (int i = 1; i < 4; i++) {
                    WearableTimer timer = new WearableTimer(new ColorTimer(context.getResources().getColor(R.color.blue500), "Blue"), context);
                    timer.setDuration(5 * i * 1000);
                    appData.timers.add(timer);
                }
                dataJSON.saveJSON(appData);
            }
        }
        return appData;
    }

    public WearableTimer getLastTimer() {
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
