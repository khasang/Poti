package com.ni032mas.poti;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.support.wearable.view.GridPagerAdapter;
import android.support.wearable.view.WearableListView;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;

public class TimerPagerAdapter extends GridPagerAdapter {
    Context context;
    Activity activity;
    ArrayList<WearableTimer> timersList = new ArrayList<>();
    ArrayList<String> namesList = new ArrayList<>();
    public static final String[] SETTINGS = new String[]{"Change", "Delete"};

    public TimerPagerAdapter(Activity activity, ArrayList<WearableTimer> timers) {
        this.context = activity.getApplicationContext();
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
                Intent newIntent = new Intent(context, TimerFeaturesActivity.class);
                newIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                context.startActivity(newIntent);
                activity.finish();
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
    public Object instantiateItem(ViewGroup viewGroup, int row, int coloumn) {
        View v;
        if (coloumn == 0) {
            v = View.inflate(context, R.layout.settings_wlistview, null);

            WearableListView wearableListViewSettings = (WearableListView) v.findViewById(R.id.settings_list);
            wearableListViewSettings.setGreedyTouchMode(true);
            SettingsWearableAdapter settingsAdapter = new SettingsWearableAdapter(context, namesList);
            wearableListViewSettings.setAdapter(settingsAdapter);
            wearableListViewSettings.setClickListener(timersClickListener);
        } else {
            v = View.inflate(context, R.layout.settings_wlistview, null);

            WearableListView wearableListViewTimers = (WearableListView) v.findViewById(R.id.settings_list);
            wearableListViewTimers.setGreedyTouchMode(true);
            SettingsWearableAdapter settingsAdapter = new SettingsWearableAdapter(context, SETTINGS);
            wearableListViewTimers.setAdapter(settingsAdapter);
            wearableListViewTimers.setClickListener(settingsClickListener);
        }
        viewGroup.addView(v);
        return v;
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
