package com.ni032mas.poti;

import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
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
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        app = (App) getActivity().getApplication();
        View view = inflater.inflate(R.layout.timers_listview, container, false);
        WearableListView wearableListViewSettings = (WearableListView) view.findViewById(R.id.settings_list);
        wearableListViewSettings.setGreedyTouchMode(true);
        final TimersWearableAdapter settingsAdapter = new TimersWearableAdapter(inflater, app.timers);
        wearableListViewSettings.setAdapter(settingsAdapter);
        wearableListViewSettings.setClickListener(new WearableListView.ClickListener() {
            @Override
            public void onClick(WearableListView.ViewHolder viewHolder) {
                int position = viewHolder.getPosition();
                app.lastTimer = app.timers.get(position);
                GeneralSettingsFragment generalSettingsFragment = new GeneralSettingsFragment();
                FragmentManager fragmentManager = getFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.replace(R.id.frame_layout, generalSettingsFragment);
                fragmentTransaction.addToBackStack(null);
                fragmentTransaction.commit();
            }

            @Override
            public void onTopEmptyRegionClick() {

            }
        });
        wearableListViewSettings.addOnCentralPositionChangedListener(new WearableListView.OnCentralPositionChangedListener() {
            @Override
            public void onCentralPositionChanged(int i) {
                app.lastTimer = app.timers.get(i);
            }
        });
        return view;
    }
}
