package com.ni032mas.poti;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.wearable.view.GridViewPager;
import android.support.wearable.view.WatchViewStub;
import android.widget.TextView;

import java.util.ArrayList;

public class MainActivity extends Activity {
    GridViewPager gridViewPager;
    ArrayList<WearableTimer> timers;
    WearableTimer lastTimer;
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
        application.lastTimer = timers.get(1);

        final WatchViewStub stub = (WatchViewStub) findViewById(R.id.watch_view_stub);
        stub.setOnLayoutInflatedListener(new WatchViewStub.OnLayoutInflatedListener() {
            @Override
            public void onLayoutInflated(WatchViewStub stub) {
                gridViewPager = (GridViewPager) findViewById(R.id.pager);
                gridViewPager.setAdapter(new TimerPagerAdapter(MainActivity.this, timers));
                gridViewPager.post(new Runnable() {
                    @Override
                    public void run() {
                        gridViewPager.setCurrentItem(0, 1);
                    }
                });
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {
            case DURATION_REQUEST_CODE:
                if (resultCode != RESULT_OK) {
                    return;
                }
                if (data.hasExtra(SetDurationFragment.RETURN_KEY)) {
                    TextView tvDuration = (TextView) gridViewPager.findViewById(R.id.tv_duration);
                    tvDuration.setText(TimerPagerAdapter.convertDuration(app.lastTimer.getDuration()));
                }
                break;
        }
    }
}