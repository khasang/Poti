package io.khasang.poti;

import android.app.Activity;
import android.os.Bundle;
import android.support.wearable.view.WearableListView;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Display;
import android.view.LayoutInflater;

import java.util.ArrayList;

import io.khasang.poti.adapters.TimersWearableAdapter;

public class MainActivity extends Activity {
    AppData appData;
    ArrayList<WearableTimer> wearableTimers;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // TODO: 23.02.2016 for debug
//        Display display = getWindowManager().getDefaultDisplay();
//        int width = display.getWidth();
//        int height = display.getHeight();
//        Log.d("LOG", "width: " + width + ", height: " + height);
//        switch (getResources().getDisplayMetrics().densityDpi) {
//            case DisplayMetrics.DENSITY_LOW:
//                Log.d("LOG", "Low");
//                break;
//            case DisplayMetrics.DENSITY_MEDIUM:
//                Log.d("LOG", "Medium");
//                break;
//            case DisplayMetrics.DENSITY_HIGH:
//                Log.d("LOG", "High");
//                break;
//            case DisplayMetrics.DENSITY_XHIGH:
//                Log.d("LOG", "Xhigh");
//                break;
//        }
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