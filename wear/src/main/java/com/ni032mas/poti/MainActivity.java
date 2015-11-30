package com.ni032mas.poti;

import android.app.Activity;
import android.os.Bundle;
import android.support.wearable.view.GridViewPager;
import android.support.wearable.view.WatchViewStub;

import java.util.ArrayList;

public class MainActivity extends Activity {
    GridViewPager gridViewPager;
    ArrayList<WearableTimer> timers;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        timers = App.appData.wearableTimers;


        final WatchViewStub stub = (WatchViewStub) findViewById(R.id.watch_view_stub);
        stub.setOnLayoutInflatedListener(new WatchViewStub.OnLayoutInflatedListener() {
            @Override
            public void onLayoutInflated(WatchViewStub stub) {
                gridViewPager = (GridViewPager) findViewById(R.id.pager);
                gridViewPager.setAdapter(new TimerPagerAdapter(MainActivity.this, timers));
            }
        });
    }
}