package com.ni032mas.poti;

import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.wearable.view.WatchViewStub;
import android.support.wearable.view.WearableListView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public class SetDurationFragment extends Fragment {
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
    public static final String RETURN_KEY = "return duration";
    App app;
    TimerFragmentPagerAdapter pagerAdapter;

    public static SetDurationFragment newInstance(TimerFragmentPagerAdapter pagerAdapter) {
        SetDurationFragment fragment = new SetDurationFragment();
        fragment.pagerAdapter = pagerAdapter;
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        app = (App) getActivity().getApplication();
        for (int i = 0; i < NUMBER_OF_TIMES; i++) {
            mTimeOptionsSecond[i] = new ListViewItem(i < 10 ? "0" + i : i + "", i * 1000);
            mTimeOptionsMinute[i] = new ListViewItem(i < 10 ? "0" + i : i + "", i * 60 * 1000);
        }
        for (int i = 0; i < NUMBER_OF_HOUR; i++) {
            mTimeOptionsHour[i] = new ListViewItem(i < 10 ? "0" + i : i + "", i * 24 * 60 * 1000);
        }
        View view = inflater.inflate(R.layout.timer_set_timer, container, false);
        mWearableListViewSecond = (WearableListView) view.findViewById(R.id.times_list_view_second);
        mWearableListViewSecond.setGreedyTouchMode(true);
        mWearableListViewSecond.setAdapter(new TimerWearableListViewAdapter(inflater, mTimeOptionsSecond));
        mWearableListViewSecond.setClickListener(new WearableListView.ClickListener() {
            @Override
            public void onClick(WearableListView.ViewHolder viewHolder) {
                replaceFragment();
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
        mWearableListViewMinute = (WearableListView) view.findViewById(R.id.times_list_view_minute);
        mWearableListViewMinute.setGreedyTouchMode(true);
        mWearableListViewMinute.setAdapter(new TimerWearableListViewAdapter(inflater, mTimeOptionsMinute));
        mWearableListViewMinute.setClickListener(new WearableListView.ClickListener() {
            @Override
            public void onClick(WearableListView.ViewHolder viewHolder) {
                replaceFragment();
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
        mWearableListViewHour = (WearableListView) view.findViewById(R.id.times_list_view_hour);
        mWearableListViewHour.setGreedyTouchMode(true);
        mWearableListViewHour.setAdapter(new TimerWearableListViewAdapter(inflater, mTimeOptionsHour));
        mWearableListViewHour.setClickListener(new WearableListView.ClickListener() {
            @Override
            public void onClick(WearableListView.ViewHolder viewHolder) {
                replaceFragment();
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
            mWearableListViewHour.scrollToPosition((int) hour);
            mWearableListViewMinute.scrollToPosition((int) minute);
            mWearableListViewSecond.scrollToPosition((int) second);
//            TODO закомменитрован видимый переход в нужную позицию
//            mWearableListViewHour.smoothScrollToPosition((int) hour);
//            mWearableListViewMinute.smoothScrollToPosition((int) minute);
//            mWearableListViewSecond.smoothScrollToPosition((int) second);
        }
        return view;
    }

    private void replaceFragment() {
        app.lastTimer.setDuration(durationHour + durationMinute + durationSecond);
        FragmentManager fragmentManager = getFragmentManager();
        fragmentManager.beginTransaction().remove(SetDurationFragment.this).commit();
        pagerAdapter.fragmentSecondPage = GeneralSettingsFragment.newInstance(pagerAdapter);
        pagerAdapter.notifyDataSetChanged();
    }
}
