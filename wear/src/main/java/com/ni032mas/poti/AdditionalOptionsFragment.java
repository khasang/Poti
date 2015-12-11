package com.ni032mas.poti;

import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.wearable.view.WearableListView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public class AdditionalOptionsFragment extends Fragment {

    public static AdditionalOptionsFragment newInstance() {
        AdditionalOptionsFragment fragment = new AdditionalOptionsFragment();
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.additional_options_fragment, container, false);
        WearableListView wearableListView = (WearableListView) view.findViewById(R.id.wlv_additional_options);
        wearableListView.setAdapter(new AdditionalOptionsWearableListViewAdapter(inflater, getActivity()));
        wearableListView.setClickListener(new WearableListView.ClickListener() {
            @Override
            public void onClick(WearableListView.ViewHolder viewHolder) {
                int position = viewHolder.getAdapterPosition();
            }

            @Override
            public void onTopEmptyRegionClick() {

            }
        });
        return view;
    }
}
