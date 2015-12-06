package com.ni032mas.poti;

import android.app.Fragment;
import android.app.FragmentManager;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

public class GeneralSettingsFragment extends Fragment {
    App app;
    AppData appData;

    public static GeneralSettingsFragment newInstance() {
        GeneralSettingsFragment fragment = new GeneralSettingsFragment();
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        app = (App) getActivity().getApplication();
        appData = app.appData;
        View view = inflater.inflate(R.layout.general_setting, container, false);
        TextView tvDuration = (TextView) view.findViewById(R.id.tv_duration);
        tvDuration.setText(convertDuration(appData.lastTimer.getDuration()));
        tvDuration.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentManager fragmentManager = getFragmentManager();
                fragmentManager.beginTransaction()
                        .replace(R.id.frame_layout, SetDurationFragment.newInstance())
                        .commit();
            }
        });
        return view;
    }

    public static String convertDuration(long duration) {
        long hour = duration / 1000 / 60 / 24;
        long minute = (duration / 1000 / 60) > 58 ? (duration / 1000 / 60) % 60 : (duration / 1000 / 60);
        long second = (duration / 1000) > 58 ? (duration / 1000) % 60 : (duration / 1000);
        return (hour > 9 ? hour : "0" + hour) + ":" + (minute > 9 ? minute : "0" + minute) + ":" + (second > 9 ? second : "0" + second);
    }

    @Override
    public void onResume() {
        super.onResume();
        Log.d("TAG","OnResume");
    }
}
