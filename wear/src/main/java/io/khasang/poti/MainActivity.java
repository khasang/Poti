package io.khasang.poti;

import android.app.Activity;
import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.wearable.view.WearableListView;
import android.view.LayoutInflater;

import java.util.ArrayList;

public class MainActivity extends Activity {
    AppData appData;
    ArrayList<WearableTimer> wearableTimers;
    ProgressDialog pDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        new Thread(new Runnable() {
            @Override
            public void run() {
                initLayout();
            }
        }).start();
    }

    private void initLayout() {
        pDialog = new ProgressDialog(MainActivity.this);
        pDialog.setMessage("Загрузка...");
        pDialog.setIndeterminate(false);
        pDialog.setCancelable(false);
        pDialog.show();

        setContentView(R.layout.activity_main);
        App app = (App) getApplication();
        appData = app.appData;
        wearableTimers = initArray(appData);
        final WearableListView wearableListView = (WearableListView) findViewById(R.id.timers_listview);
        final TimersWearableAdapter settingsAdapter = new TimersWearableAdapter(LayoutInflater.from(MainActivity.this),
                wearableTimers, MainActivity.this);
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

        pDialog.cancel();
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