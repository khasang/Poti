package com.ni032mas.poti;

import android.app.Application;

import java.util.ArrayList;

/**
 * Created by aleksandr.marmyshev on 26.11.2015.
 */
public class App extends Application {
    ArrayList<WearableTimer> timers;
    WearableTimer lastTimer;
    @Override
    public void onCreate() {
        super.onCreate();
    }
}
