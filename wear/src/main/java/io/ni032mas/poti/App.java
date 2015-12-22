package io.ni032mas.poti;

import android.app.Application;
import android.content.Context;

import java.util.ArrayList;

public class App extends Application {
    AppData appData;
    SaveLoadDataJSON dataJSON;

    @Override
    public void onCreate() {
        super.onCreate();
        Context context = getApplicationContext();
        dataJSON = new SaveLoadDataJSON(context);
        dataJSON.appData = new AppData();
        appData = (AppData) dataJSON.loadJSON();
        if (appData == null) {
            appData = new AppData();
        }
        if (appData.timers == null) {
            appData.timers = new ArrayList<>();
            for (int i = 1; i < 4; i++) {
                WearableTimer timer = new WearableTimer(new ColorTimer(getResources().getColor(R.color.blue500), "Blue"), context);
                timer.setDuration(5 * i * 1000);
                appData.timers.add(timer);
            }
            dataJSON.saveJSON(appData);
        }
    }
}
