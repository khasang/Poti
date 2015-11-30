package com.ni032mas.poti;

import android.app.Application;

/**
 * Created by aleksandr.marmyshev on 26.11.2015.
 */
public class App extends Application {
    public static AppData appData;
    public static SaveLoadDataJSON<AppData> saveLoadDataJSON;
    public static final String DATA_MAP_KEY = "timers";

    @Override
    public void onCreate() {
        super.onCreate();
        saveLoadDataJSON = new SaveLoadDataJSON<>(getApplicationContext());
        appData = saveLoadDataJSON.loadJSON(DATA_MAP_KEY);
        if (appData == null) {
            WearableTimer wearableTimer = new WearableTimer();
            wearableTimer.setName("New");
            appData = new AppData();
            appData.wearableTimers.add(wearableTimer);
            saveLoadDataJSON.saveJSON(appData, DATA_MAP_KEY);
        }
    }
}
