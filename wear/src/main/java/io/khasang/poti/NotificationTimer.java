package io.khasang.poti;

import android.app.Activity;
import android.app.AlarmManager;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;

import io.khasang.poti.util.Constants;
import io.khasang.poti.util.TimerFormat;

public class NotificationTimer {
    Activity activity;
    Context context;
    WearableTimer wearableTimer;
    public static final String TIMER_VIBRATE = "timer_vibrate";
    public static final String TIMER_CURRENT_TIME = "timer_current_time";
    public static final String TIMER_N = "timer";
    public static final String TIMER_NAME = "timer_name";
    public static final String TIMER_COLOR = "timer_color";
    public static final String TIMER_DURATION = "timer_duration";
    int timerN;

    public NotificationTimer(Activity activity, WearableTimer wearableTimer, int timerN) {
        this.context = activity.getApplicationContext();
        this.activity = activity;
        this.wearableTimer = wearableTimer;
        this.timerN = timerN;
    }

    public NotificationTimer(Context context, WearableTimer wearableTimer, int timerN) {
        this.context = context;
        this.wearableTimer = wearableTimer;
        this.timerN = timerN;
    }

    void setupTimer() {
        NotificationManager notifyMgr =
                ((NotificationManager) context.getSystemService(context.NOTIFICATION_SERVICE));
        notifyMgr.notify(timerN, buildNotification());
        registerWithAlarmManager();
        if (activity != null) {
            activity.finish();
            Intent intent = new Intent(context, CountDownActivity.class)
                    .putExtra(TIMER_N, timerN)
                    .putExtra(TIMER_NAME, wearableTimer.getName())
                    .putExtra(TIMER_DURATION, wearableTimer.getDuration())
                    .putExtra(TIMER_COLOR, wearableTimer.getColor().color);
            activity.startActivity(intent);
        }
    }

    private void registerWithAlarmManager() {
        AlarmManager alarm = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
        Intent intent = new Intent(Constants.ACTION_SHOW_ALARM, null, context,
                TimerNotificationService.class)
                .putExtra(TIMER_N, timerN)
                .putExtra(TIMER_NAME, wearableTimer.getName())
                .putExtra(TIMER_COLOR, wearableTimer.getColor().color);
        PendingIntent pendingIntent = PendingIntent.getService(context, 0, intent,
                PendingIntent.FLAG_UPDATE_CURRENT);
        long wakeupTime = System.currentTimeMillis() + wearableTimer.getDuration();
        alarm.setExact(AlarmManager.RTC_WAKEUP, wakeupTime, pendingIntent);
    }

    private Notification buildNotification() {
        // Intent to restart a timer.
        Intent restartIntent = new Intent(Constants.ACTION_RESTART_ALARM, null, context,
                TimerNotificationService.class)
                .putExtra(NotificationTimer.TIMER_N, timerN);
        PendingIntent pendingIntentRestart = PendingIntent
                .getService(context, 0, restartIntent, PendingIntent.FLAG_UPDATE_CURRENT);
        // Intent to delete a timer.
        Intent deleteIntent = new Intent(Constants.ACTION_DELETE_ALARM, null, context,
                TimerNotificationService.class)
                .putExtra(NotificationTimer.TIMER_N, timerN);
        PendingIntent pendingIntentDelete = PendingIntent
                .getService(context, 0, deleteIntent, PendingIntent.FLAG_UPDATE_CURRENT);


        // Intent to start activity.
        Intent intentStartActivity = new Intent(Constants.ACTION_FULLSCREEN, null, context,
                CountDownActivity.class)
                .putExtra(TIMER_N, timerN)
                .putExtra(TIMER_NAME, wearableTimer.getName())
                .putExtra(TIMER_DURATION, wearableTimer.getDuration())
                .putExtra(TIMER_CURRENT_TIME, System.currentTimeMillis())
                .putExtra(TIMER_COLOR, wearableTimer.getColor().color);
        PendingIntent pendingStartActivity = PendingIntent.getActivity(context, 0, intentStartActivity, PendingIntent.FLAG_UPDATE_CURRENT);
        // Create countdown notification using a chronometer style.
        Bitmap bitmap = Bitmap.createBitmap(320, 320, Bitmap.Config.ARGB_8888);
        bitmap.eraseColor(wearableTimer.getColor().color);
        return new Notification.Builder(context)
                .setSmallIcon(R.drawable.ic_poti_draw)
                .setContentTitle(wearableTimer.getName())
                .setContentText(TimerFormat.getTimeString(wearableTimer.getDuration()))
                .setUsesChronometer(true)
                .setWhen(System.currentTimeMillis() + wearableTimer.getDuration())
                .addAction(R.drawable.ic_refresh_white_48dp, context.getString(R.string.timer_restart),
                        pendingIntentRestart)
                .addAction(R.drawable.ic_delete_white_48dp, context.getString(R.string.timer_delete),
                        pendingIntentDelete)
                .addAction(R.drawable.ic_fullscreen_white_48dp, context.getString(R.string.fullscreen),
                        pendingStartActivity)
                .setDeleteIntent(pendingIntentDelete)
                .setLocalOnly(true)
                .setColor(wearableTimer.getColor().color)
                .setShowWhen(false)
                .extend(new Notification.WearableExtender()
                        .setBackground(bitmap))
                .build();
    }

    private void cancelCountdown(NotificationManager notifyMgr) {
        notifyMgr.cancel(Constants.NOTIFICATION_TIMER_EXPIRED);
    }
}
