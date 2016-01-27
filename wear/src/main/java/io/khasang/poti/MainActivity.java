package io.khasang.poti;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.wearable.view.WearableListView;
import android.view.LayoutInflater;
import android.view.View;

import java.util.ArrayList;

import io.khasang.poti.util.Constants;

public class MainActivity extends Activity {
    AppData appData;
    ArrayList<WearableTimer> wearableTimers;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initLayout();
    }

    private void initLayout() {
        setContentView(R.layout.activity_main);
        appData = AppData.getInstance(getApplicationContext());
        if (appData.getLastTimer() != null && appData.getLastTimer().getDuration() == 0L) {
            appData.timers.remove(appData.getIndexLastTimer());
        }
        wearableTimers = initArray(appData);
        final WearableListView wearableListView = (WearableListView) findViewById(R.id.timers_listview);
        final TimersWearableAdapter settingsAdapter = new TimersWearableAdapter(LayoutInflater.from(MainActivity.this), wearableTimers, MainActivity.this);
        wearableListView.setAdapter(settingsAdapter);
        wearableListView.addOnCentralPositionChangedListener(new WearableListView.OnCentralPositionChangedListener() {
            @Override
            public void onCentralPositionChanged(int i) {
                if (i - 1 >= 0 && i - 1 < appData.timers.size()) {
                    appData.setLastTimer(i - 1);
                }
            }

        });
        int indexArr = appData.timers.indexOf(appData.getLastTimer());
        if (indexArr >= 0
                && indexArr <= appData.timers.size() - 1
                && indexArr <= wearableTimers.size()
                && appData.timers.size() != 0) {
            wearableListView.scrollToPosition(appData.timers.indexOf(appData.getLastTimer()) + 1);
        } else {
            wearableListView.scrollToPosition(0);
        }
    }

    private ArrayList<WearableTimer> initArray(AppData appData) {
        ArrayList<WearableTimer> timers = new ArrayList<>();
        timers.add(new WearableTimer(getResources().getString(R.string.new_label), getApplicationContext()));
        for (WearableTimer timer : appData.timers) {
            timers.add(timer);
        }
        return timers;
    }

    @Override
    protected void onResume() {
        super.onResume();
        initLayout();
    }
}