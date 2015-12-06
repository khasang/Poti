package com.ni032mas.poti;

import android.app.Application;

import java.util.ArrayList;

/**
 * Created by aleksandr.marmyshev on 26.11.2015.
 */
public class App extends Application {
    AppData appData;
    String DATA = "DATA";
    SaveLoadDataJSON dataJSON;

    @Override
    public void onCreate() {
        super.onCreate();
        dataJSON = new SaveLoadDataJSON(getApplicationContext());
        dataJSON.appData = new AppData();
        appData = (AppData) dataJSON.loadJSON(DATA);
        if (appData == null) {
            appData = new AppData();
        }
        if (appData.timers == null) {
            appData.timers = new ArrayList<>();
            for (int i = 1; i < 4; i++) {
                WearableTimer timer = new WearableTimer();
                timer.setDuration(5 * i * 1000);
                appData.timers.add(timer);
            }
            appData.lastTimer = appData.timers.get(0);
            dataJSON.saveJSON(appData, DATA);
        }
    }
}
