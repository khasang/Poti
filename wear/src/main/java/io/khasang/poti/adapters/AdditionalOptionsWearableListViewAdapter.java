package io.khasang.poti.adapters;

import android.app.Activity;
import android.content.Context;
import android.support.wearable.view.WearableListView;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.TextView;

import io.khasang.poti.fragments.AdditionalOptionsFragment;
import io.khasang.poti.layouts.AdditionalOptionsWearableListViewLayout;
import io.khasang.poti.AppData;
import io.khasang.poti.layouts.ListViewItem;
import io.khasang.poti.R;

public class AdditionalOptionsWearableListViewAdapter extends WearableListView.Adapter{
    private LayoutInflater mInflater;
    ListViewItem[] arrayOptions = new ListViewItem[3];
    Context context;
    AppData appData;

    public AdditionalOptionsWearableListViewAdapter(LayoutInflater mInflater, Activity activity) {
        this.mInflater = mInflater;
        this.context = activity.getApplicationContext();
        this.arrayOptions[0] = new ListViewItem(context.getResources().getString(R.string.name));
        this.arrayOptions[1] = new ListViewItem(context.getResources().getString(R.string.color));
        this.arrayOptions[2] = new ListViewItem(context.getResources().getString(R.string.vibration));
//        TODO для следующих релизов
//        this.arrayOptions[3] = new ListViewItem(context.getResources().getString(R.string.cycle));
        this.appData = AppData.getInstance(activity.getApplicationContext());
    }

    @Override
    public WearableListView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        WearableListView.ViewHolder viewHolder = new WearableListView.ViewHolder(mInflater.inflate(R.layout.additional_options_item, null));
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(WearableListView.ViewHolder holder, int position) {
        AdditionalOptionsWearableListViewLayout layout = (AdditionalOptionsWearableListViewLayout) holder.itemView.findViewById(R.id.additional_options_layout_item);
        layout.position = position;
        layout.timer = appData.getLastTimer();
        TextView tvLabel = (TextView) holder.itemView.findViewById(R.id.tv_label_add_options);
        TextView tvDescription = (TextView) holder.itemView.findViewById(R.id.tv_description_add_options);
        tvLabel.setText(arrayOptions[position].label);
        switch (position) {
            case AdditionalOptionsFragment.NAME_POSITION:
                tvDescription.setText(appData.getLastTimer().getName());
                break;
            case AdditionalOptionsFragment.COLOR_POSITION:
                tvDescription.setText(appData.getLastTimer().getColor().name + "");
                tvDescription.setTextColor(appData.getLastTimer().getColor().color);
                break;
            case AdditionalOptionsFragment.VIBRATION_POSITION:
                tvDescription.setText(appData.getLastTimer().getVibrationName() + "");
                break;
            case AdditionalOptionsFragment.CYCLE_POSITION:
                tvDescription.setText(appData.getLastTimer().getCycle() + "");
                break;
        }
    }

    @Override
    public int getItemCount() {
        return arrayOptions.length;
    }
}
