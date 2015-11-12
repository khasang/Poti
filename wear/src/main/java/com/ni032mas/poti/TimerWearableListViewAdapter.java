package com.ni032mas.poti;

import android.content.Context;
import android.support.wearable.view.WearableListView;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.TextView;

/**
 * Created by aleksandr.marmyshev on 12.11.2015.
 */
public class TimerWearableListViewAdapter extends WearableListView.Adapter {
    private final Context mContext;
    private final LayoutInflater mInflater;
    ListViewItem[] mTimeOptions;

    TimerWearableListViewAdapter(Context context, ListViewItem[] timeOptions) {
        mContext = context;
        mInflater = LayoutInflater.from(context);
        mTimeOptions = timeOptions;
    }

    @Override
    public WearableListView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new WearableListView.ViewHolder(
                mInflater.inflate(R.layout.timer_list_item, null));
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
