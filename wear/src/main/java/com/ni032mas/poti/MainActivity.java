package com.ni032mas.poti;

import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.support.wearable.view.DotsPageIndicator;
import android.support.wearable.view.GridViewPager;
import android.support.wearable.view.WatchViewStub;

import java.io.FileDescriptor;
import java.io.PrintWriter;
import java.util.ArrayList;

public class MainActivity extends Activity {
    GridViewPager gridViewPager;
    ArrayList<WearableTimer> timers;
    App app;
    public static final int DURATION_REQUEST_CODE = 11;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        app = ((App) getApplicationContext());
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
        App application = (App) getApplicationContext();
        application.timers = timers;
        if (application.lastTimer == null) {
            application.lastTimer = timers.get(1);
        }

        final WatchViewStub stub = (WatchViewStub) findViewById(R.id.watch_view_stub);
        stub.setOnLayoutInflatedListener(new WatchViewStub.OnLayoutInflatedListener() {
            @Override
            public void onLayoutInflated(WatchViewStub stub) {
                gridViewPager = (GridViewPager) stub.findViewById(R.id.grid_view_pager);
                gridViewPager.setAdapter(new TimerFragmentPagerAdapter(getFragmentManager(), getApplicationContext()));
            }
        });
    }

}