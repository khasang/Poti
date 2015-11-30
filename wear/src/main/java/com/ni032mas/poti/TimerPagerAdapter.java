package com.ni032mas.poti;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.support.wearable.view.GridPagerAdapter;
import android.support.wearable.view.WearableListView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;

public class TimerPagerAdapter extends GridPagerAdapter {
    Context context;
    Activity activity;
    App app;
    ArrayList<WearableTimer> timersList = new ArrayList<>();
    ArrayList<String> namesList = new ArrayList<>();
    public static final String[] SETTINGS = new String[]{"Change", "Delete"};
    public static final int DURATION_REQUEST_CODE = 11;

    public TimerPagerAdapter(Activity activity, ArrayList<WearableTimer> timers) {
        this.context = activity.getApplicationContext();
        this.app = (App) this.context;
        this.activity = activity;
        this.timersList = timers;
        for (WearableTimer wearableTimer : timersList) {
            namesList.add(wearableTimer.getName());
        }
    }

    private WearableListView.ClickListener timersClickListener = new WearableListView.ClickListener() {
        @Override
        public void onClick(WearableListView.ViewHolder viewHolder) {
            int position = viewHolder.getPosition();
            String timer = timersList.get(position).getName();
            if (timer.equals("New")) {
//                Intent newIntent = new Intent(context, TimerFeaturesActivity.class);
//                newIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
//                context.startActivity(newIntent);
//                activity.finish();
            } else {
                NotificationTimer nt = new NotificationTimer(activity);
                nt.setupTimer(timersList.get(position).getDuration());
            }
        }

        @Override
        public void onTopEmptyRegionClick() {
        }
    };

    private WearableListView.ClickListener settingsClickListener = new WearableListView.ClickListener() {
        @Override
        public void onClick(WearableListView.ViewHolder viewHolder) {
            int position = viewHolder.getPosition();
            String setting = SETTINGS[position];
            switch (setting) {
                case "Change":
                    break;
                case "Delete":
                    break;
            }
        }

        @Override
        public void onTopEmptyRegionClick() {
        }
    };

    @Override
    public int getRowCount() {
        return 1;
    }

    @Override
    public int getColumnCount(int i) {
        return 2;
    }

    @Override
    public Object instantiateItem(ViewGroup viewGroup, int row, int column) {
        View v = null;
        if (column == 0) {
            v = View.inflate(context, R.layout.settings_wlistview, null);
            WearableListView wearableListViewSettings = (WearableListView) v.findViewById(R.id.settings_list);
            wearableListViewSettings.setGreedyTouchMode(true);
            final SettingsWearableAdapter settingsAdapter = new SettingsWearableAdapter(context, timersList);
            wearableListViewSettings.setAdapter(settingsAdapter);
            wearableListViewSettings.setClickListener(timersClickListener);
            wearableListViewSettings.addOnCentralPositionChangedListener(new WearableListView.OnCentralPositionChangedListener() {
                @Override
                public void onCentralPositionChanged(int i) {
                    app.lastTimer = timersList.get(i);
                }
            });
        } else if (column == 1) {
            v = View.inflate(context, R.layout.general_setting, null);
            TextView tvDuration = (TextView) v.findViewById(R.id.tv_duration);
            tvDuration.setText(convertDuration(app.lastTimer.getDuration()));
            tvDuration.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent newIntent = new Intent(context, SetDurationFragment.class);
                    newIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    activity.startActivity(newIntent);
                }
            });
        }
        viewGroup.addView(v);
        return v;
    }

    public static String convertDuration(long duration) {
        long hour = duration / 1000 / 60 / 24;
        long minute = duration / 1000 / 60;
        long second = (duration / 1000) > 58 ? (duration / 1000) % 60 : (duration / 1000);
        String s = (hour > 9 ? hour : "0" + hour) + ":" + (minute > 9 ? minute : "0" + minute) + ":" + (second > 9 ? second : "0" + second);
        return s;
    }


    @Override
    public void destroyItem(ViewGroup viewGroup, int i, int i1, Object o) {
//        viewGroup.removeView((View) o);﻿
    }

    @Override
    public boolean isViewFromObject(View view, Object o) {
        return view.equals(o);
    }
}
