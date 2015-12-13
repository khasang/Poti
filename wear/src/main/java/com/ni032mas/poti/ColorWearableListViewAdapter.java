package com.ni032mas.poti;

import android.app.Activity;
import android.content.Context;
import android.support.wearable.view.WearableListView;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;

public class ColorWearableListViewAdapter extends WearableListView.Adapter{
    private final LayoutInflater mInflater;
    Context context;
    AppData appData;
    ArrayList<ColorTimer> colors = new ArrayList<>();
    int centralPosition;

    public ColorWearableListViewAdapter(LayoutInflater mInflater, Activity activity) {
        this.mInflater = mInflater;
        this.context = activity.getApplicationContext();
        App app = (App) activity.getApplication();
        this.appData = app.appData;
        colors.add(new ColorTimer(context.getResources().getColor(R.color.light_blue500), "Light blue"));
        colors.add(new ColorTimer(context.getResources().getColor(R.color.yellow500), "Yellow"));
        colors.add(new ColorTimer(context.getResources().getColor(R.color.green500), "Green"));
        colors.add(new ColorTimer(context.getResources().getColor(R.color.purple500), "Purple"));
        colors.add(new ColorTimer(context.getResources().getColor(R.color.red500), "Red"));
        colors.add(new ColorTimer(context.getResources().getColor(R.color.blue500), "Blue"));
    }

    @Override
    public WearableListView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        WearableListView.ViewHolder viewHolder = new WearableListView.ViewHolder(mInflater.inflate(R.layout.color_listview_item, null));
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(WearableListView.ViewHolder holder, int position) {
        ColorWearableListViewLayout colorWearableListViewLayout = (ColorWearableListViewLayout) holder.itemView.findViewById(R.id.color_wlv_layout);
        colorWearableListViewLayout.colors = colors;
        colorWearableListViewLayout.position = position;
        TextView tvLabel = (TextView) holder.itemView.findViewById(R.id.tv_color);
        tvLabel.setText(colors.get(position).name);
    }

    @Override
    public int getItemCount() {
        return colors.size();
    }
    public int getIndexColor(int color) {
        for (int i = 0; i < this.colors.size(); i++) {
            if (this.colors.get(i).color == color) {
                return i;
            }
        }
        return 0;
    }
}
