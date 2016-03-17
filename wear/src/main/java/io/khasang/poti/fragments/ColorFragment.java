package io.khasang.poti.fragments;

import android.app.Fragment;
import android.app.FragmentManager;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.wearable.view.WearableListView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import io.khasang.poti.AppData;
import io.khasang.poti.R;
import io.khasang.poti.json.SaveLoadDataJSON;
import io.khasang.poti.adapters.ColorWearableListViewAdapter;

public class ColorFragment extends Fragment {
    AppData appData;
    SaveLoadDataJSON saveLoadDataJSON;
    private ColorWearableListViewAdapter adapter;

    public static ColorFragment newInstance() {
        ColorFragment fragment = new ColorFragment();
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        appData = AppData.getInstance(getActivity().getApplicationContext());
        saveLoadDataJSON = new SaveLoadDataJSON(getActivity().getApplicationContext());
        View view = inflater.inflate(R.layout.color_fragment, container, false);
        WearableListView wearableListView = (WearableListView) view.findViewById(R.id.wlv_color);
        adapter = new ColorWearableListViewAdapter(inflater, getActivity());
        wearableListView.setAdapter(adapter);
        wearableListView.setClickListener(new WearableListView.ClickListener() {
            @Override
            public void onClick(WearableListView.ViewHolder viewHolder) {
                appData.getLastTimer().setColor(adapter.colors.get(viewHolder.getAdapterPosition()));
                saveLoadDataJSON.saveJSON(appData);
                replaceFragment();
            }

            @Override
            public void onTopEmptyRegionClick() {

            }
        });
        wearableListView.addOnCentralPositionChangedListener(new WearableListView.OnCentralPositionChangedListener() {
            @Override
            public void onCentralPositionChanged(int i) {
                adapter.centralPosition = i;
            }
        });
        wearableListView.scrollToPosition(adapter.getIndexColor(appData.getLastTimer().getColor().color));
        return view;
    }
    private void replaceFragment() {
        FragmentManager fragmentManager = getFragmentManager();
        fragmentManager.beginTransaction()
                .replace(R.id.frame_layout, AdditionalOptionsFragment.newInstance())
                .commit();
    }
}
