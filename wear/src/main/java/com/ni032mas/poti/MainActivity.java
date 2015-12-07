package com.ni032mas.poti;

import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.os.Bundle;
import android.support.wearable.view.DotsPageIndicator;
import android.support.wearable.view.GridViewPager;
import android.support.wearable.view.WatchViewStub;
import android.support.wearable.view.WearableListView;
import android.view.LayoutInflater;
import android.view.View;

import java.io.FileDescriptor;
import java.io.PrintWriter;
import java.util.ArrayList;

public class MainActivity extends Activity {
    App app;
    AppData appData;
    ArrayList<WearableTimer> wearableTimers;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initLayout();
    }

    private void initLayout() {
        setContentView(R.layout.activity_main);
        final WatchViewStub stub = (WatchViewStub) findViewById(R.id.watch_view_stub);
        stub.setOnLayoutInflatedListener(new WatchViewStub.OnLayoutInflatedListener() {
            @Override
            public void onLayoutInflated(WatchViewStub stub) {
                app = (App) getApplication();
                appData = app.appData;
                wearableTimers = initArray(appData);
                WearableListView wearableListView = (WearableListView) stub.findViewById(R.id.settings_list);
                TimersWearableAdapter settingsAdapter = new TimersWearableAdapter(LayoutInflater.from(MainActivity.this), wearableTimers);
                wearableListView.setAdapter(settingsAdapter);
                wearableListView.addOnCentralPositionChangedListener(new WearableListView.OnCentralPositionChangedListener() {
                    @Override
                    public void onCentralPositionChanged(int i) {
                        appData.lastTimer = wearableTimers.get(i);
                    }
                });
                int indexArr = appData.timers.indexOf(appData.lastTimer);
                if (indexArr >= 0
                        && indexArr <= appData.timers.size() - 1
                        && indexArr <= wearableTimers.size()
                        && appData.timers.size() != 0) {
                    wearableListView.scrollToPosition(appData.timers.indexOf(appData.lastTimer) + 1);
                } else {
                    wearableListView.scrollToPosition(0);
                }
                wearableListView.setClickListener(new WearableListView.ClickListener() {
                    @Override
                    public void onClick(WearableListView.ViewHolder viewHolder) {
                        if (viewHolder.getPosition() == 0) {
                            appData.timers.add(new WearableTimer());
                            appData.lastTimer = appData.timers.get(appData.timers.size() - 1);
                            app.dataJSON.saveJSON(appData, app.DATA);
                        }
                        Intent intent = new Intent(getApplicationContext(), ActivityFragment.class);
                        startActivity(intent);
                    }

                    @Override
                    public void onTopEmptyRegionClick() {

                    }
                });
            }
        });
    }

    private ArrayList<WearableTimer> initArray(AppData appData) {
        ArrayList<WearableTimer> timers = new ArrayList<>();
        timers.add(new WearableTimer(getResources().getString(R.string.new_label)));
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