package io.khasang.poti.fragments;

import android.app.Fragment;
import android.app.FragmentManager;
import android.content.Intent;
import android.os.Bundle;
import android.support.wearable.view.CircledImageView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import io.khasang.poti.AppData;
import io.khasang.poti.notifications.NotificationTimer;
import io.khasang.poti.R;
import io.khasang.poti.json.SaveLoadDataJSON;
import io.khasang.poti.WearableTimer;
import io.khasang.poti.activity.AdditionalOptionsActivity;

public class GeneralSettingsFragment extends Fragment {
    AppData appData;
    private TextView tvDuration;
    SaveLoadDataJSON saveLoadDataJSON;


    public static GeneralSettingsFragment newInstance() {
        GeneralSettingsFragment fragment = new GeneralSettingsFragment();
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        saveLoadDataJSON = new SaveLoadDataJSON(getActivity().getApplicationContext());
        appData = AppData.getInstance(getActivity().getApplicationContext());
        View view = inflater.inflate(R.layout.general_setting, container, false);
        tvDuration = (TextView) view.findViewById(R.id.tv_duration);
        tvDuration.setTextColor(appData.getLastTimer().getColor().color);
        tvDuration.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentManager fragmentManager = getFragmentManager();
                fragmentManager.beginTransaction()
                        .replace(R.id.frame_layout, SetDurationFragment.newInstance())
                        .commit();
            }
        });
        CircledImageView cbDelete = (CircledImageView) view.findViewById(R.id.cb_delete);
        cbDelete.setFocusable(true);
        cbDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                appData.timers.remove(appData.getLastTimer());
                saveLoadDataJSON.saveJSON(appData);
                getActivity().finish();
            }
        });
        CircledImageView cbStart = (CircledImageView) view.findViewById(R.id.cb_start);
        cbStart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (appData.getLastTimer() != null && appData.getLastTimer().getDuration() != 0L) {
                    NotificationTimer notificationTimer = new NotificationTimer(getActivity(), appData.getLastTimer(), appData.getIndexLastTimer());
                    notificationTimer.setupTimer();
                }
            }
        });
        CircledImageView cbSetting = (CircledImageView) view.findViewById(R.id.cb_setting);
        cbSetting.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity().getApplicationContext(), AdditionalOptionsActivity.class);
                startActivity(intent);
            }
        });
//        cbStart.setRippleColor(getResources().getColor(R.color.grey600));
//        cbDelete.setRippleColor(getResources().getColor(R.color.grey600));
//        cbSetting.setRippleColor(getResources().getColor(R.color.grey600));
        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        tvDuration.setText(WearableTimer.getDurationString(appData.getLastTimer().getDuration()));
        tvDuration.setTextColor(appData.getLastTimer().getColor().color);
    }
}
