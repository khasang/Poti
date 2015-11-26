package com.ni032mas.poti;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.wearable.view.WatchViewStub;
import android.support.wearable.view.WearableListView;
import android.util.Log;

public class SetDurationActivity extends Activity {
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

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
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
            }

        });
    }

    @Override
    public void finish() {
        Intent intent = new Intent();
        intent.putExtra(RETURN_KEY, durationHour + durationMinute + durationSecond);
        setResult(RESULT_OK, intent);
        super.finish();
    }
}
