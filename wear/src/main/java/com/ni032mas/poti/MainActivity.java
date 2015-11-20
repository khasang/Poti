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

        //// FIXME: 21.11.2015 код для тестирования - список должен браться из Shared Preferences
        timers = new ArrayList<>();
        WearableTimer timerN = new WearableTimer();
        timerN.setName("New");
        timers.add(timerN);
        for (int i = 1; i < 4; i++) {
            WearableTimer timer = new WearableTimer();
            timer.setName(5 * i + " sec");
            timer.setDuration(5 * i * 1000);
            timers.add(timer);
        }

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