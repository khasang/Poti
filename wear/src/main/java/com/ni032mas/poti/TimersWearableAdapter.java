package com.ni032mas.poti;

import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Context;
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
    App app;

    public TimersWearableAdapter(LayoutInflater inflater, ArrayList<WearableTimer> items, Activity activity) {
        this.mInflater = inflater;
        this.mItems = items;
        this.activity = activity;
        this.app = (App) activity.getApplication();
        appData = app.appData;
    }

    @Override
    public WearableListView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new WearableListView.ViewHolder(mInflater.inflate(R.layout.timers_listview_item, null));
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    @Override
    public void onBindViewHolder(WearableListView.ViewHolder viewHolder, final int position) {
        TextView textView = (TextView) viewHolder.itemView.findViewById(R.id.timer_name);
        if (mItems.get(position).getName() == null) {
            textView.setText(convertDuration(mItems.get(position).getDuration()));
        } else {
            textView.setText(mItems.get(position).getName());
        }
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
                    appData.timers.add(new WearableTimer());
                    appData.setLastTimer(appData.timers.size() - 1);
                    app.dataJSON.saveJSON(appData, app.DATA);
                    Intent intent = new Intent(activity.getApplicationContext(), ActivityFragment.class);
                    activity.startActivity(intent);

                }
            });
        } else {
            civPlay.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    appData.setLastTimer(position - 1);
                    NotificationTimer notificationTimer = new NotificationTimer(activity);
                    notificationTimer.setupTimer(appData.getLastTimer().getDuration(), appData.getIndexLastTimer());
                }
            });
            civSetting.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    appData.setLastTimer(position - 1);
                    app.dataJSON.saveJSON(appData, app.DATA);
                    Intent intent = new Intent(activity.getApplicationContext(), ActivityFragment.class);
                    activity.startActivity(intent);
                }
            });
        }
    }

    @Override
    public int getItemCount() {
        return mItems.size();
    }

    public static String convertDuration(long duration) {
        long hour = duration / 1000 / 60 / 60;
        long minute = (duration / 1000 / 60) > 58 ? (duration / 1000 / 60) % 60 : (duration / 1000 / 60);
        long second = (duration / 1000) > 58 ? (duration / 1000) % 60 : (duration / 1000);
        return (hour > 9 ? hour : "0" + hour) + ":" + (minute > 9 ? minute : "0" + minute) + ":" + (second > 9 ? second : "0" + second);
    }
}
