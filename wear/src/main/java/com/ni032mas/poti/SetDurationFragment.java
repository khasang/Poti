package com.ni032mas.poti;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.wearable.view.WatchViewStub;
import android.support.wearable.view.WearableListView;
import android.util.Log;

public class SetDurationFragment extends Activity {
    String tag = "tag";
    public static final int NUMBER_OF_TIMES = 60;
    public static final int NUMBER_OF_HOUR = 24;
    private ListViewItem[] mTimeOptionsSecond = new ListViewItem[NUMBER_OF_TIMES];
    private ListViewItem[] mTimeOptionsMinute = new ListViewItem[NUMBER_OF_TIMES];
    private ListViewItem[] mTimeOptionsHour = new ListViewItem[NUMBER_OF_HOUR];
    private WearableListView mWearableListViewSecond;
    private WearableListView mWearableListViewMinute;
    private WearableListView mWearableListViewHour;
    long durationSecond;
    long durationMinute;
    long durationHour;
    NotificationTimer notificationTimer = new NotificationTimer(this);
    public static final String RETURN_KEY = "return duration";
    public static final int DURATION_REQUEST_CODE = 11;
    App app;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        app = (App) getApplicationContext();
        for (int i = 0; i < NUMBER_OF_TIMES; i++) {
            mTimeOptionsSecond[i] = new ListViewItem(i < 10 ? "0" + i : i + "", i * 1000);
            mTimeOptionsMinute[i] = new ListViewItem(i < 10 ? "0" + i : i + "", i * 60 * 1000);
        }
        for (int i = 0; i < NUMBER_OF_HOUR; i++) {
            mTimeOptionsHour[i] = new ListViewItem(i < 10 ? "0" + i : i + "", i * 24 * 60 * 1000);
        }
        setContentView(R.layout.activity_new_timer);

        final WatchViewStub stub = (WatchViewStub) findViewById(R.id.new_watch_view_stub);
        stub.setOnLayoutInflatedListener(new WatchViewStub.OnLayoutInflatedListener() {
            @Override
            public void onLayoutInflated(WatchViewStub stub) {
                mWearableListViewSecond = (WearableListView) stub.findViewById(R.id.times_list_view_second);
                mWearableListViewSecond.setAdapter(new TimerWearableListViewAdapter(getApplicationContext(), mTimeOptionsSecond));
                mWearableListViewSecond.setClickListener(new WearableListView.ClickListener() {
                    @Override
                    public void onClick(WearableListView.ViewHolder viewHolder) {
                        finish();
                    }

                    @Override
                    public void onTopEmptyRegionClick() {
                    }
                });
                mWearableListViewSecond.addOnCentralPositionChangedListener(new WearableListView.OnCentralPositionChangedListener() {
                    @Override
                    public void onCentralPositionChanged(int i) {
                        durationSecond = mTimeOptionsSecond[i].duration;
                        Log.d(tag, durationSecond / 1000 + " секунд");
                    }
                });
                mWearableListViewMinute = (WearableListView) stub.findViewById(R.id.times_list_view_minute);
                mWearableListViewMinute.setAdapter(new TimerWearableListViewAdapter(getApplicationContext(), mTimeOptionsMinute));
                mWearableListViewMinute.setClickListener(new WearableListView.ClickListener() {
                    @Override
                    public void onClick(WearableListView.ViewHolder viewHolder) {
                        finish();
                    }

                    @Override
                    public void onTopEmptyRegionClick() {
                    }
                });
                mWearableListViewMinute.addOnCentralPositionChangedListener(new WearableListView.OnCentralPositionChangedListener() {
                    @Override
                    public void onCentralPositionChanged(int i) {
                        durationMinute = mTimeOptionsMinute[i].duration;
                        Log.d(tag, durationMinute / 1000 / 60 + " минут");
                    }
                });
                mWearableListViewHour = (WearableListView) stub.findViewById(R.id.times_list_view_hour);
                mWearableListViewHour.setAdapter(new TimerWearableListViewAdapter(getApplicationContext(), mTimeOptionsHour));
                mWearableListViewHour.setClickListener(new WearableListView.ClickListener() {
                    @Override
                    public void onClick(WearableListView.ViewHolder viewHolder) {
                        finish();
                    }

                    @Override
                    public void onTopEmptyRegionClick() {
                    }
                });
                mWearableListViewHour.addOnCentralPositionChangedListener(new WearableListView.OnCentralPositionChangedListener() {
                    @Override
                    public void onCentralPositionChanged(int i) {
                        durationHour = mTimeOptionsHour[i].duration;
                        Log.d(tag, durationHour / 1000 / 60 / 24 + " часов");
                    }
                });
                if (app.lastTimer != null) {
                    long hour = app.lastTimer.getDuration() / 1000 / 60 / 24;
                    long minute = app.lastTimer.getDuration() / 1000 / 60;
                    long second = (app.lastTimer.getDuration() / 1000) > 58 ? (app.lastTimer.getDuration() / 1000) % 60 : (app.lastTimer.getDuration() / 1000);
                    mWearableListViewHour.smoothScrollToPosition((int) hour);
                    mWearableListViewMinute.smoothScrollToPosition((int) minute);
                    mWearableListViewSecond.smoothScrollToPosition((int) second);
                }
            }

        });
    }

    @Override
    public void finish() {
        if (app.lastTimer != null) {
            app.lastTimer.setDuration(durationHour + durationMinute + durationSecond);
        }
        Intent newIntent = new Intent(this, MainActivity.class);
        newIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivityForResult(newIntent, DURATION_REQUEST_CODE);
//        Intent intent = new Intent();
//        intent.putExtra(RETURN_KEY, durationHour + durationMinute + durationSecond);
//        setResult(RESULT_OK, intent);
        super.finish();
    }
}
