package com.ni032mas.poti;

import android.app.Activity;
import android.os.Bundle;
import android.support.wearable.view.WatchViewStub;
import android.widget.TextView;
import android.app.AlarmManager;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.provider.AlarmClock;
import android.support.wearable.view.WearableListView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.Toast;

import com.ni032mas.poti.util.Constants;
import com.ni032mas.poti.util.TimerFormat;

public class MainActivity extends Activity {

    private TextView mTextView;
    public static final int NUMBER_OF_TIMES = 60;
    public static final int NUMBER_OF_HOUR = 60;
    public static final String TAG = "SetTimerActivity";

    private ListViewItem[] mTimeOptionsSecond = new ListViewItem[NUMBER_OF_TIMES];
    private ListViewItem[] mTimeOptionsMinute = new ListViewItem[NUMBER_OF_TIMES];
    private ListViewItem[] mTimeOptionsHour = new ListViewItem[NUMBER_OF_HOUR];
    private ListViewItem[] mTimeOptions = new ListViewItem[NUMBER_OF_TIMES];
    private WearableListView mWearableListViewSecond;
    private WearableListView mWearableListViewMinute;
    private WearableListView mWearableListViewHour;
    long durationSecond;
    long durationMinute;
    long durationHour;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        int paramLength = getIntent().getIntExtra(AlarmClock.EXTRA_LENGTH, 0);
        if (Log.isLoggable(TAG, Log.DEBUG)) {
            Log.d(TAG, "SetTimerActivity:onCreate=" + paramLength);
        }
        if (paramLength > 0 && paramLength <= 86400) {
            long durationMillis = paramLength * 1000;
            setupTimer(durationMillis);
            finish();
            return;
        }

        Resources res = getResources();
        for (int i = 0; i < NUMBER_OF_TIMES; i++) {
            mTimeOptionsSecond[i] = new ListViewItem(i < 10 ? "0" + i : i + "", i * 1000);
            mTimeOptionsMinute[i] = new ListViewItem(i < 10 ? "0" + i : i + "", i * 60 * 1000);
        }
        for (int i = 0; i < NUMBER_OF_HOUR; i++) {
            mTimeOptionsHour[i] = new ListViewItem(i < 10 ? "0" + i : i + "", i * 24 * 60 * 1000);
        }

        setContentView(R.layout.activity_main);
        final WatchViewStub stub = (WatchViewStub) findViewById(R.id.watch_view_stub);
        stub.setOnLayoutInflatedListener(new WatchViewStub.OnLayoutInflatedListener() {
            @Override
            public void onLayoutInflated(WatchViewStub stub) {
                mWearableListViewSecond = (WearableListView) stub.findViewById(R.id.times_list_view_second);
                mWearableListViewSecond.setAdapter(new TimerWearableListViewAdapter(getApplicationContext(), mTimeOptionsSecond));
                mWearableListViewSecond.setClickListener(new WearableListView.ClickListener() {
                    @Override
                    public void onClick(WearableListView.ViewHolder viewHolder) {
                        setupTimer(durationHour + durationMinute + durationSecond);
                    }

                    @Override
                    public void onTopEmptyRegionClick() {

                    }
                });
                mWearableListViewSecond.addOnCentralPositionChangedListener(new WearableListView.OnCentralPositionChangedListener() {
                    @Override
                    public void onCentralPositionChanged(int i) {
                        durationSecond = mTimeOptionsSecond[i].duration;
                        Toast.makeText(getApplicationContext(), durationSecond + "", Toast.LENGTH_LONG).show();
                    }
                });
                mWearableListViewMinute = (WearableListView) stub.findViewById(R.id.times_list_view_minute);
                mWearableListViewMinute.setAdapter(new TimerWearableListViewAdapter(getApplicationContext(), mTimeOptionsMinute));
                mWearableListViewMinute.setClickListener(new WearableListView.ClickListener() {
                    @Override
                    public void onClick(WearableListView.ViewHolder viewHolder) {
                        setupTimer(durationHour + durationMinute + durationSecond);
                    }

                    @Override
                    public void onTopEmptyRegionClick() {

                    }
                });
                mWearableListViewMinute.addOnCentralPositionChangedListener(new WearableListView.OnCentralPositionChangedListener() {
                    @Override
                    public void onCentralPositionChanged(int i) {
                        durationMinute = mTimeOptionsMinute[i].duration;
                        Toast.makeText(getApplicationContext(), durationMinute + "", Toast.LENGTH_LONG).show();
                    }
                });
                mWearableListViewHour = (WearableListView) stub.findViewById(R.id.times_list_view_hour);
                mWearableListViewHour.setAdapter(new TimerWearableListViewAdapter(getApplicationContext(), mTimeOptionsHour));
                mWearableListViewHour.setClickListener(new WearableListView.ClickListener() {
                    @Override
                    public void onClick(WearableListView.ViewHolder viewHolder) {
                        setupTimer(durationHour + durationMinute + durationSecond);
                    }

                    @Override
                    public void onTopEmptyRegionClick() {

                    }
                });
                mWearableListViewHour.addOnCentralPositionChangedListener(new WearableListView.OnCentralPositionChangedListener() {
                    @Override
                    public void onCentralPositionChanged(int i) {
                        durationHour = mTimeOptionsHour[i].duration;
                        Toast.makeText(getApplicationContext(), durationHour + "", Toast.LENGTH_LONG).show();
                    }
                });
            }
        });
    }

    /**
     * Sets up an alarm (and an associated notification) to go off after <code>duration</code>
     * milliseconds.
     */
    private void setupTimer(long duration) {
        NotificationManager notifyMgr =
                ((NotificationManager) getSystemService(NOTIFICATION_SERVICE));

        // Delete dataItem and cancel a potential old countdown.
        cancelCountdown(notifyMgr);

        // Build notification and set it.
        notifyMgr.notify(Constants.NOTIFICATION_TIMER_COUNTDOWN, buildNotification(duration));

        // Register with the alarm manager to display a notification when the timer is done.
        registerWithAlarmManager(duration);

        finish();
    }

    private void registerWithAlarmManager(long duration) {
        // Get the alarm manager.
        AlarmManager alarm = (AlarmManager) getSystemService(Context.ALARM_SERVICE);

        // Create intent that gets fired when timer expires.
        Intent intent = new Intent(Constants.ACTION_SHOW_ALARM, null, this,
                TimerNotificationService.class);
        PendingIntent pendingIntent = PendingIntent.getService(this, 0, intent,
                PendingIntent.FLAG_UPDATE_CURRENT);

        // Calculate the time when it expires.
        long wakeupTime = System.currentTimeMillis() + duration;

        // Schedule an alarm.
        alarm.setExact(AlarmManager.RTC_WAKEUP, wakeupTime, pendingIntent);
    }

    /**
     * Build a notification including different actions and other various setup and return it.
     *
     * @param duration the duration of the timer.
     * @return the notification to display.
     */

    private Notification buildNotification(long duration) {
        // Intent to restart a timer.
        Intent restartIntent = new Intent(Constants.ACTION_RESTART_ALARM, null, this,
                TimerNotificationService.class);
        PendingIntent pendingIntentRestart = PendingIntent
                .getService(this, 0, restartIntent, PendingIntent.FLAG_UPDATE_CURRENT);

        // Intent to delete a timer.
        Intent deleteIntent = new Intent(Constants.ACTION_DELETE_ALARM, null, this,
                TimerNotificationService.class);
        PendingIntent pendingIntentDelete = PendingIntent
                .getService(this, 0, deleteIntent, PendingIntent.FLAG_UPDATE_CURRENT);

        // Create countdown notification using a chronometer style.
        return new Notification.Builder(this)
                .setSmallIcon(R.drawable.ic_cc_alarm)
                .setContentTitle(getString(R.string.timer_time_left))
                .setContentText(TimerFormat.getTimeString(duration))
                .setUsesChronometer(true)
                .setWhen(System.currentTimeMillis() + duration)
                .addAction(R.drawable.ic_cc_alarm, getString(R.string.timer_restart),
                        pendingIntentRestart)
                .addAction(R.drawable.ic_cc_alarm, getString(R.string.timer_delete),
                        pendingIntentDelete)
                .setDeleteIntent(pendingIntentDelete)
                .setLocalOnly(true)
                .build();
    }

    /**
     * Cancels an old countdown and deletes the dataItem.
     *
     * @param notifyMgr the notification manager.
     */
    private void cancelCountdown(NotificationManager notifyMgr) {
        notifyMgr.cancel(Constants.NOTIFICATION_TIMER_EXPIRED);
    }

    /**
     * Model class for the listview.
     */
    private static class ListViewItem {

        // Duration in milliseconds.
        long duration;
        // Label to display.
        private String label;

        public ListViewItem(String label, long duration) {
            this.label = label;
            this.duration = duration;
        }

        @Override
        public String toString() {
            return label;
        }
    }

    private final class TimerWearableListViewAdapter extends WearableListView.Adapter {
        private final Context mContext;
        private final LayoutInflater mInflater;
        ListViewItem[] mTimeOptions;

        private TimerWearableListViewAdapter(Context context, ListViewItem[] timeOptions) {
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
}
