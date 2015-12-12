package com.ni032mas.poti;

import android.app.Activity;
import android.content.Context;
import android.support.wearable.view.WearableListView;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.TextView;

public class AdditionalOptionsWearableListViewAdapter extends WearableListView.Adapter{
    private final LayoutInflater mInflater;
    ListViewItem[] arrayOptions = new ListViewItem[4];
    Context context;
    AppData appData;
    TimerColor timerColor;
    String name;
    String color;
    String vibration;
    String cycle;


    public AdditionalOptionsWearableListViewAdapter(LayoutInflater mInflater, Activity activity) {
        this.mInflater = mInflater;
        this.context = activity.getApplicationContext();
        this.arrayOptions[0] = new ListViewItem(context.getResources().getString(R.string.name));
        this.arrayOptions[1] = new ListViewItem(context.getResources().getString(R.string.color));
        this.arrayOptions[2] = new ListViewItem(context.getResources().getString(R.string.vibration));
        this.arrayOptions[3] = new ListViewItem(context.getResources().getString(R.string.cycle));
        App app = (App) activity.getApplication();
        this.appData = app.appData;
        this.timerColor = new TimerColor(this.context);
    }

    @Override
    public WearableListView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        WearableListView.ViewHolder viewHolder = new WearableListView.ViewHolder(mInflater.inflate(R.layout.additional_options_item, null));
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(WearableListView.ViewHolder holder, int position) {
        TextView tvLabel = (TextView) holder.itemView.findViewById(R.id.tv_label_add_options);
        TextView tvDescription = (TextView) holder.itemView.findViewById(R.id.tv_description_add_options);
        tvLabel.setText(arrayOptions[position].label);
        switch (position) {
            case 0:
                tvDescription.setText(appData.getLastTimer().getName());
                break;
            case 1:
                tvDescription.setText(timerColor.getColorName(appData.getLastTimer().getColor()) + "");
                tvDescription.setTextColor(appData.getLastTimer().getColor());
                break;
            case 2:
                tvDescription.setText(appData.getLastTimer().getVibration() + "");
                break;
            case 3:
                tvDescription.setText(appData.getLastTimer().getCycle() + "");
                break;
        }
    }

    @Override
    public int getItemCount() {
        return arrayOptions.length;
    }
}
