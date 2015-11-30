package com.ni032mas.poti;

import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

public class GeneralSettingsFragment extends Fragment {
    App app;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        app = (App) getActivity().getApplication();
        View view = inflater.inflate(R.layout.general_setting, container, false);
        TextView tvDuration = (TextView) view.findViewById(R.id.tv_duration);
        tvDuration.setText(convertDuration(app.lastTimer.getDuration()));
        tvDuration.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SetDurationFragment setDurationFragment = new SetDurationFragment();
                FragmentManager fragmentManager = getFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.replace(R.id.frame_layout, setDurationFragment);
                fragmentTransaction.addToBackStack(null);
                fragmentTransaction.commit();
            }
        });
        return view;
    }

    public static String convertDuration(long duration) {
        long hour = duration / 1000 / 60 / 24;
        long minute = duration / 1000 / 60;
        long second = (duration / 1000) > 58 ? (duration / 1000) % 60 : (duration / 1000);
        String s = (hour > 9 ? hour : "0" + hour) + ":" + (minute > 9 ? minute : "0" + minute) + ":" + (second > 9 ? second : "0" + second);
        return s;
    }
}
