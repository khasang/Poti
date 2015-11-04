package com.khasang.poti.poti;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.provider.AlarmClock;
import android.support.wearable.view.WatchViewStub;
import android.support.wearable.view.WearableListView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import java.io.IOException;
import java.io.ObjectOutputStream;
import java.sql.Array;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Random;

public class MainActivity extends Activity {

    private TextView mTextView;
    private String[] mItems;
    private WearableListView mWearableListView;
    WearableListView.ClickListener mClickListener;

    public static final String MYPREF_TXT = "mypref.txt";
    public static final String DURATION = "DURATION";
    public static final String NAME = "NAME";
    int i;

//    <category android:name="android.intent.category.DEFAULT" />
//    <action android:name="android.intent.action.SET_TIMER" />

    private static final int[] TIMER_PRESETS_DURATION_SECS = { 5, 10, 20, 30, 40, 50, 60, 120,
            180, 240, 300, 360, 420, 480, 540, 600, 900, 1800, 2700, 3600, 5400 };
    private static final String[] TIMER_PRESETS_LABEL = new String[] {
            "5 seconds", "10 seconds", "20 seconds", "30 seconds", "40 seconds", "50 seconds",
            "1 minute", "2 minutes", "3 minutes", "4 minutes", "5 minutes",
            "6 minutes", "7 minutes", "8 minutes", "9 minutes", "10 minutes",
            "15 minutes", "30 minutes", "45 minutes", "1 hour", "1.5 hours"
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //setContentView(R.layout.activity_main);
        //setContentView(R.layout.hello);
        setContentView(R.layout.wearable_list_view);
//        final WatchViewStub stub = (WatchViewStub) findViewById(R.id.watch_view_stub);
//        stub.setOnLayoutInflatedListener(new WatchViewStub.OnLayoutInflatedListener() {
//            @Override
//            public void onLayoutInflated(WatchViewStub stub) {
//                mTextView = (TextView) stub.findViewById(R.id.text);
//            }
//        });


        mClickListener = new WearableListView.ClickListener() {
            @Override
            public void onClick(WearableListView.ViewHolder viewHolder) {
                int position = viewHolder.getPosition();
                i++;
                mItems[position] = mItems[position] + " ! " + i;
                mWearableListView.getAdapter().notifyDataSetChanged();

            }

            @Override
            public void onTopEmptyRegionClick() {

            }
        };

//        SharedPreferences sharedPreferences = getSharedPreferences(MYPREF_TXT, MODE_PRIVATE);
//        Map<String, ?> m = sharedPreferences.getAll();
//        String s = " ";
//        for (String ss : m.keySet()) {
//            s = s + ss + " - " + m.get(ss) + " ";
//        }
//        i = sharedPreferences.getInt(DURATION, 10);
//        Timer2 tm = new Timer2();
//        tm.loadFromFile(this);
//        s = tm.getName();

        //String s = sharedPreferences.getString(NAME, "default");
        //mItems = new String[]{"Timer 1 " + i, "Timer 2 " + i, "Timer 3 " + i, "Timer 4 " + i, "Timer 5 "  + i};
        //mItems = new String[]{s + i, s + i, s + i, s + i, s  + i};
        Timers tm = new Timers();
        tm.loadTimers(this);
        mItems = new String[tm.getItems().size()];
        i = 0;
        for (Timer2 timer: tm.getItems()) {
            mItems[i] = timer.getName() + " " +timer.getDuration();
            i++;
        }
        mWearableListView = (WearableListView) findViewById(R.id.list);
        mWearableListView.setAdapter(new SampleAdapter(this, mItems));
        mWearableListView.setClickListener(mClickListener);


//        int timerDuration = getIntent().getIntExtra(AlarmClock.EXTRA_LENGTH, -1);
//        if(timerDuration > 0) {
//            Timer.createNewTimer(this, timerDuration*1000L);
//            finish();
//        } else {
//            // of EXTRA_LENGTH is not available, then we must prompt the user for a timer duration
//            WearableListView wearableListView = (WearableListView)findViewById(R.id.list);
//            SampleAdapter timerDurationAdapter = new SampleAdapter(this,
//                    TIMER_PRESETS_LABEL);
//            wearableListView.setAdapter(timerDurationAdapter);
//            wearableListView.setClickListener(mClickListener);
//        }




    }

    public static class SampleAdapter extends WearableListView.Adapter {

        private LayoutInflater mInflater;
        private String[] mSampleItems;
        private List<String> mItems;

//        public SampleAdapter(Context context, String[] sampleItems){
//            mInflater = LayoutInflater.from(context);
//            mSampleItems = sampleItems;
//        }

        public SampleAdapter(Context context, String[] items){
            this(context, Arrays.asList(items));
        }

        public SampleAdapter(Context context, List<String> items) {
            mInflater = LayoutInflater.from(context);
            mItems = items;
        }

//        @Override
//        public WearableListView.ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
//            /// simple_list_item_1 - предустановленный layout
//            View view = mInflater.inflate(android.R.layout.simple_list_item_1, viewGroup, false);
//            return new WearableListView.ViewHolder(view);
//        }

        @Override
        public WearableListView.ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
            return new ItemViewHolder(mInflater.inflate(R.layout.list_item, null));
        }

//        @Override
//        public void onBindViewHolder(WearableListView.ViewHolder viewHolder, int i) {
//            TextView textView = (TextView) viewHolder.itemView;
//            textView.setText(mSampleItems[i]);
//        }

        @Override
        public void onBindViewHolder(WearableListView.ViewHolder viewHolder, int position) {
            ItemViewHolder itemViewHolder = (ItemViewHolder) viewHolder;
            TextView textView = itemViewHolder.mItemTextView;
            textView.setText(mItems.get(position));
            ((ItemViewHolder) viewHolder).itemView.setTag(position);
        }

//        @Override
//        public int getItemCount() {
//            return mSampleItems.length;
//        }

        @Override
        public int getItemCount() {
            return mItems.size();
        }

        private static class ItemViewHolder extends WearableListView.ViewHolder {
            private TextView mItemTextView;
            public ItemViewHolder(View itemView) {
                super(itemView);
                mItemTextView = (TextView) itemView.findViewById(R.id.name);
            }
        }
    }

//    private WearableListView.ClickListener mClickListener = new WearableListView.ClickListener() {
//        @Override
//        public void onClick(WearableListView.ViewHolder viewHolder) {
//            // once the user picks a duration, create timer alarm/notification and exit
//            int position = viewHolder.getPosition();
//            int duration = TIMER_PRESETS_DURATION_SECS[position];
//            Timer.createNewTimer(MainActivity.this, duration*1000L);
//            finish();
//        }
//        @Override
//        public void onTopEmptyRegionClick() {
//        }
//    };

    @Override
    protected void onPause() {
        super.onPause();
        SharedPreferences sharedPreferences = getSharedPreferences(MYPREF_TXT, MODE_PRIVATE);
        SharedPreferences.Editor box = sharedPreferences.edit();
        box.putInt(DURATION, i++);
        box.putString(NAME, DURATION);
        box.commit();
    }

    @Override
    protected void onStop() {
        super.onStop();
        SharedPreferences sharedPreferences = getSharedPreferences(MYPREF_TXT, MODE_PRIVATE);
        SharedPreferences.Editor box = sharedPreferences.edit();
        box.putInt(DURATION, i);
        box.putString(NAME, DURATION);
        box.commit();
    }
}
