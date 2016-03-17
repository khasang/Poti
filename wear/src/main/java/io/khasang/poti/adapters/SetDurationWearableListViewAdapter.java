package io.khasang.poti.adapters;

import android.support.wearable.view.WearableListView;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.TextView;

import io.khasang.poti.layouts.ListViewItem;
import io.khasang.poti.R;

public class SetDurationWearableListViewAdapter extends WearableListView.Adapter {
    private final LayoutInflater mInflater;
    ListViewItem[] mTimeOptions;

    public SetDurationWearableListViewAdapter(LayoutInflater inflater, ListViewItem[] timeOptions) {
        mInflater = inflater;
        mTimeOptions = timeOptions;
    }

    @Override
    public WearableListView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new WearableListView.ViewHolder(mInflater.inflate(R.layout.set_duration_listview_item, null));
    }

    @Override
    public void onBindViewHolder(WearableListView.ViewHolder holder, int position) {
        TextView view = (TextView) holder.itemView.findViewById(R.id.time_text);
        view.setText(mTimeOptions[position].label);
        holder.itemView.setTag(position);
    }

    @Override
    public int getItemCount() {
        return mTimeOptions.length;
    }
}
