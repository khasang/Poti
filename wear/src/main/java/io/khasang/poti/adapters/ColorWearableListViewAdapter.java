package io.khasang.poti.adapters;

import android.app.Activity;
import android.content.Context;
import android.support.wearable.view.WearableListView;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;

import io.khasang.poti.AppData;
import io.khasang.poti.util.ColorTimer;
import io.khasang.poti.layouts.ColorWearableListViewLayout;
import io.khasang.poti.R;

public class ColorWearableListViewAdapter extends WearableListView.Adapter{
    private final LayoutInflater mInflater;
    Context context;
    AppData appData;
    public ArrayList<ColorTimer> colors = new ArrayList<>();
    public int centralPosition;

    public ColorWearableListViewAdapter(LayoutInflater mInflater, Activity activity) {
        this.mInflater = mInflater;
        this.context = activity.getApplicationContext();
        this.appData = AppData.getInstance(this.context);
        colors.add(new ColorTimer(context.getResources().getColor(R.color.light_blue500), context.getResources().getString(R.string.light_blue)));
        colors.add(new ColorTimer(context.getResources().getColor(R.color.yellow500), context.getResources().getString(R.string.yellow)));
        colors.add(new ColorTimer(context.getResources().getColor(R.color.green500), context.getResources().getString(R.string.green)));
        colors.add(new ColorTimer(context.getResources().getColor(R.color.purple500), context.getResources().getString(R.string.purple)));
        colors.add(new ColorTimer(context.getResources().getColor(R.color.red500), context.getResources().getString(R.string.red)));
        colors.add(new ColorTimer(context.getResources().getColor(R.color.blue500), context.getResources().getString(R.string.blue)));
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
