package com.ni032mas.poti;

import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Intent;
import android.os.Build;
import android.support.wearable.view.CircledImageView;
import android.support.wearable.view.WearableListView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;

public class TimersWearableAdapter extends WearableListView.Adapter {
    private ArrayList<WearableTimer> mItems;
    private final LayoutInflater mInflater;
    private Activity activity;
    AppData appData;
    SaveLoadDataJSON saveLoadDataJSON;

    public TimersWearableAdapter(LayoutInflater inflater, ArrayList<WearableTimer> items, Activity activity) {
        this.mInflater = inflater;
        this.mItems = items;
        this.activity = activity;
        App app = (App) activity.getApplication();
        appData = app.appData;
        saveLoadDataJSON = new SaveLoadDataJSON(activity.getApplicationContext());
    }

    @Override
    public WearableListView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new WearableListView.ViewHolder(mInflater.inflate(R.layout.timers_listview_item, null));
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    @Override
    public void onBindViewHolder(WearableListView.ViewHolder viewHolder, final int position) {
        TextView textView = (TextView) viewHolder.itemView.findViewById(R.id.timer_name);
        textView.setText(mItems.get(position).getName());
        viewHolder.itemView.setTag(position);
        CircledImageView civPlay = (CircledImageView) viewHolder.itemView.findViewById(R.id.btn_timerslist_start);
        CircledImageView civSetting = (CircledImageView) viewHolder.itemView.findViewById(R.id.btn_timerslist_setting);
        if (position == 0) {
            civSetting.setCircleHidden(true);
            civSetting.setImageDrawable(null);
            civPlay.setImageDrawable(activity.getApplicationContext().getResources().getDrawable(R.drawable.plus, null));
            civPlay.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    appData.timers.add(new WearableTimer(new ColorTimer(activity.getResources().getColor(R.color.blue500), "Blue"), activity.getApplicationContext()));
                    appData.setLastTimer(appData.timers.size() - 1);
                    saveLoadDataJSON.saveJSON(appData);
                    Intent intent = new Intent(activity.getApplicationContext(), GeneralSettingsActivity.class);
                    activity.startActivity(intent);

                }
            });
        } else {
            civPlay.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    appData.setLastTimer(position - 1);
                    NotificationTimer notificationTimer = new NotificationTimer(activity, appData.getLastTimer(), appData.getIndexLastTimer());
                    notificationTimer.setupTimer();
                }
            });
            civSetting.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    appData.setLastTimer(position - 1);
                    saveLoadDataJSON.saveJSON(appData);
                    Intent intent = new Intent(activity.getApplicationContext(), GeneralSettingsActivity.class);
                    activity.startActivity(intent);
                }
            });
        }
    }

    @Override
    public int getItemCount() {
        return mItems.size();
    }
}
