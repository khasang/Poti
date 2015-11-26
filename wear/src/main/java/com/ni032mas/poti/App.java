package com.ni032mas.poti;

import android.app.Application;

/**
 * Created by aleksandr.marmyshev on 26.11.2015.
 */
public class App extends Application {
    AppData appData;
    @Override
    public void onCreate() {
        super.onCreate();
        SaveLoadDataJSON saveLoadDataJSON = new SaveLoadDataJSON(getApplicationContext());
        appData = (AppData) saveLoadDataJSON.loadJSON("AppData");
    }
}
