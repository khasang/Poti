package com.ni032mas.poti;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public class TimersFragment extends Fragment {
    AppData appData;

    public static TimersFragment newInstance() {
        TimersFragment fragment = new TimersFragment();
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        App app = (App) getActivity().getApplication();
        appData = app.appData;
        View view = inflater.inflate(R.layout.timers_listview, container, false);
        return view;
    }
}
