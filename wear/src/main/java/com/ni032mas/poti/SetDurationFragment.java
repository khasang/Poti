package com.ni032mas.poti;

import android.app.Fragment;
import android.app.FragmentManager;
import android.os.Bundle;
import android.support.annotation.Nullable;
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
    AppData appData;
    SaveLoadDataJSON saveLoadDataJSON;

    public static SetDurationFragment newInstance() {
        SetDurationFragment fragment = new SetDurationFragment();
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        App app = (App) getActivity().getApplication();
        appData = app.appData;
        saveLoadDataJSON = new SaveLoadDataJSON(getActivity().getApplicationContext());
        for (int i = 0; i < NUMBER_OF_TIMES; i++) {
            mTimeOptionsSecond[i] = new ListViewItem(i < 10 ? "0" + i : i + "", i * 1000);
            mTimeOptionsMinute[i] = new ListViewItem(i < 10 ? "0" + i : i + "", i * 1000 * 60);
        }
        for (int i = 0; i < NUMBER_OF_HOUR; i++) {
            mTimeOptionsHour[i] = new ListViewItem(i < 10 ? "0" + i : i + "", i * 1000 * 60 * 60);
        }
        View view = inflater.inflate(R.layout.set_duration_listview, container, false);
        mWearableListViewSecond = (WearableListView) view.findViewById(R.id.times_list_view_second);
        mWearableListViewSecond.setGreedyTouchMode(true);
        mWearableListViewSecond.setAdapter(new SetDurationWearableListViewAdapter(inflater, mTimeOptionsSecond));
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
        mWearableListViewMinute.setAdapter(new SetDurationWearableListViewAdapter(inflater, mTimeOptionsMinute));
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
        mWearableListViewHour.setAdapter(new SetDurationWearableListViewAdapter(inflater, mTimeOptionsHour));
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
        if (appData.getLastTimer() != null) {
            long duration = appData.getLastTimer().getDuration();
            long hour = duration / 1000 / 60 / 60;
            long minute = (duration / 1000 / 60) > 58 ? (duration / 1000 / 60) % 60 : (duration / 1000 / 60);
            long second = (duration / 1000) > 58 ? (duration / 1000) % 60 : (duration / 1000);
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
        appData.getLastTimer().setDuration(durationHour + durationMinute + durationSecond);
        saveLoadDataJSON.saveJSON(appData);
        FragmentManager fragmentManager = getFragmentManager();
        fragmentManager.beginTransaction()
                .replace(R.id.frame_layout, GeneralSettingsFragment.newInstance())
                .commit();
    }
}
