package com.ni032mas.poti;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.support.wearable.view.GridPagerAdapter;
import android.support.wearable.view.WearableListView;
import android.view.View;
import android.view.ViewGroup;

import java.lang.reflect.AccessibleObject;

/**
 * Created by ni032_000 on 15.11.2015.
 */
public class TimerPagerAdapter extends GridPagerAdapter {
    Context context;

    public TimerPagerAdapter(Activity activity) {
        this.context = activity.getApplicationContext();
    }
    public static final String[] SETTINGS = new String[]{"New timer", "Delete timer", "Timers list"};
    public static final String[] TIMERS = new String[]{"5 sec", "10 sec", "15 sec"};
    private WearableListView.ClickListener settingsClickListener = new WearableListView.ClickListener() {
        @Override
        public void onClick(WearableListView.ViewHolder viewHolder) {
            int position = viewHolder.getPosition();
            String setting = SETTINGS[position];
            switch (setting) {
                case "New timer":
                    Intent newIntent = new Intent(context, SetupTimerActivity.class);
                    context.startActivity(newIntent);
                    ((Activity)context).finish();
                    break;
                case "Delete timer":
                    break;
                case "Timers list":
                    break;
            }
        }

        @Override
        public void onTopEmptyRegionClick() {
        }
    };

    private WearableListView.ClickListener timersClickListener = new WearableListView.ClickListener() {
        @Override
        public void onClick(WearableListView.ViewHolder viewHolder) {
            int position = viewHolder.getPosition();
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
            SettingsWearableAdapter settingsAdapter = new SettingsWearableAdapter(context, TIMERS);
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
