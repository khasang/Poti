package com.ni032mas.poti;

import android.content.Context;
import android.support.wearable.view.WearableListView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class SettingsWearableAdapter extends WearableListView.Adapter {
    private ArrayList<WearableTimer> mItems;
    private final LayoutInflater mInflater;

    public SettingsWearableAdapter(Context context, ArrayList<WearableTimer> items) {
        mInflater = LayoutInflater.from(context);
        mItems = items;
    }

    private static class ItemViewHolder extends WearableListView.ViewHolder {
        private TextView mItemTextView;

        public ItemViewHolder(View itemView) {
            super(itemView);
            mItemTextView = (TextView) itemView.findViewById(R.id.settings_name);
        }
    }

    @Override
    public WearableListView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ItemViewHolder(mInflater.inflate(R.layout.settings_wlistview_item, null));
    }

    @Override
    public void onBindViewHolder(WearableListView.ViewHolder viewHolder, int position) {
        ItemViewHolder itemViewHolder = (ItemViewHolder) viewHolder;
        TextView textView = itemViewHolder.mItemTextView;
        textView.setText(mItems.get(position).getName());
        ((ItemViewHolder) viewHolder).itemView.setTag(position);
    }

    @Override
    public int getItemCount() {
        return mItems.size();
    }
}
