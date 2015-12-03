package com.ni032mas.poti;

import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.wearable.view.WearableListView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * Created by aleksandr.marmyshev on 30.11.2015.
 */
public class TimersFragment extends Fragment {
    App app;
    AppData appData;
    TimerFragmentPagerAdapter pagerAdapter;
    int i;

    public static TimersFragment newInstance(TimerFragmentPagerAdapter pagerAdapter) {
        TimersFragment fragment = new TimersFragment();
        fragment.pagerAdapter = pagerAdapter;
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        app = (App) getActivity().getApplication();
        appData = app.appData;
        View view = inflater.inflate(R.layout.timers_listview, container, false);
        WearableListView wearableListViewSettings = (WearableListView) view.findViewById(R.id.settings_list);
        wearableListViewSettings.setGreedyTouchMode(true);
        final TimersWearableAdapter settingsAdapter = new TimersWearableAdapter(inflater, appData.timers);
        wearableListViewSettings.setAdapter(settingsAdapter);
        wearableListViewSettings.addOnCentralPositionChangedListener(new WearableListView.OnCentralPositionChangedListener() {
            @Override
            public void onCentralPositionChanged(int i) {
                appData.lastTimer = appData.timers.get(i);
//                FragmentManager fragmentManager = getFragmentManager();
//                fragmentManager.beginTransaction().remove(pagerAdapter.fragmentSecondPage).commit();
//                pagerAdapter.fragmentSecondPage = GeneralSettingsFragment.newInstance(pagerAdapter);
//                pagerAdapter.notifyDataSetChanged();
                app.dataJSON.saveJSON(appData, app.DATA);
            }
        });
        wearableListViewSettings.scrollToPosition(appData.timers.indexOf(appData.lastTimer));
        return view;
    }
}
