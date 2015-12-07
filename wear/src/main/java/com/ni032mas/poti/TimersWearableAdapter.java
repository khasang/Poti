package com.ni032mas.poti;

import android.support.wearable.view.CircledImageView;
import android.support.wearable.view.WearableListView;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;

public class TimersWearableAdapter extends WearableListView.Adapter {
    private ArrayList<WearableTimer> mItems;
    private final LayoutInflater mInflater;

    public TimersWearableAdapter(LayoutInflater inflater, ArrayList<WearableTimer> items) {
        mInflater = inflater;
        mItems = items;
    }

    @Override
    public WearableListView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new WearableListView.ViewHolder(mInflater.inflate(R.layout.timers_listview_item, null));
    }

    @Override
    public void onBindViewHolder(WearableListView.ViewHolder viewHolder, int position) {
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
            civPlay.setCircleHidden(true);
            civSetting.setImageDrawable(null);
            civPlay.setImageDrawable(null);
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
