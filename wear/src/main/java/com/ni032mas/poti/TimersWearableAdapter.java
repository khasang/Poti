package com.ni032mas.poti;

import android.support.wearable.view.WearableListView;
import android.view.LayoutInflater;
import android.view.View;
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

    private static class ItemViewHolder extends WearableListView.ViewHolder {
        private TextView mItemTextView;
        public ItemViewHolder(View itemView) {
            super(itemView);
            mItemTextView = (TextView) itemView.findViewById(R.id.timer_name);
        }
    }

    @Override
    public WearableListView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ItemViewHolder(mInflater.inflate(R.layout.timers_listview_item, null));
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
