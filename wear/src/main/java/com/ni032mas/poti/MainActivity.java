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
                WearableListView wearableListView = (WearableListView) stub.findViewById(R.id.settings_list);
                TimersWearableAdapter settingsAdapter = new TimersWearableAdapter(LayoutInflater.from(MainActivity.this), appData.timers);
                wearableListView.setAdapter(settingsAdapter);
                wearableListView.addOnCentralPositionChangedListener(new WearableListView.OnCentralPositionChangedListener() {
                    @Override
                    public void onCentralPositionChanged(int i) {
                        appData.lastTimer = appData.timers.get(i);
                    }
                });
                wearableListView.scrollToPosition(appData.timers.indexOf(appData.lastTimer));
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

    @Override
    protected void onResume() {
        super.onResume();
        initLayout();
    }
}