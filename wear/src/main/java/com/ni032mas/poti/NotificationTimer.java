package com.ni032mas.poti;

import android.app.Activity;
import android.app.AlarmManager;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;

import com.ni032mas.poti.util.Constants;
import com.ni032mas.poti.util.TimerFormat;

/**
 * Created by ni032_000 on 15.11.2015.
 */
public class NotificationTimer {
    Activity activity;
    Context context;
    WearableTimer wearableTimer;

    public NotificationTimer(Activity activity, WearableTimer wearableTimer) {
        this.context = activity.getApplicationContext();
        this.activity = activity;
        this.wearableTimer = wearableTimer;
    }

    void setupTimer(int timer) {
        NotificationManager notifyMgr =
                ((NotificationManager) context.getSystemService(context.NOTIFICATION_SERVICE));
        // Delete dataItem and cancel a potential old countdown.
        //TODO ni032mas
        //cancelCountdown(notifyMgr);
        notifyMgr.notify(timer, buildNotification());
        registerWithAlarmManager();
        activity.finish();
    }

    private void registerWithAlarmManager() {
        // Get the alarm manager.
        AlarmManager alarm = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
        // Create intent that gets fired when timer expires.
        Intent intent = new Intent(Constants.ACTION_SHOW_ALARM, null, context,
                TimerNotificationService.class);
        PendingIntent pendingIntent = PendingIntent.getService(context, 0, intent,
                PendingIntent.FLAG_UPDATE_CURRENT);
        // Calculate the time when it expires.
        long wakeupTime = System.currentTimeMillis() + wearableTimer.getDuration();
        // Schedule an alarm.
        alarm.setExact(AlarmManager.RTC_WAKEUP, wakeupTime, pendingIntent);
    }

    private Notification buildNotification() {
        // Intent to restart a timer.
        Intent restartIntent = new Intent(Constants.ACTION_RESTART_ALARM, null, context,
                TimerNotificationService.class);
        PendingIntent pendingIntentRestart = PendingIntent
                .getService(context, 0, restartIntent, PendingIntent.FLAG_UPDATE_CURRENT);

        // Intent to delete a timer.
        Intent deleteIntent = new Intent(Constants.ACTION_DELETE_ALARM, null, context,
                TimerNotificationService.class);
        PendingIntent pendingIntentDelete = PendingIntent
                .getService(context, 0, deleteIntent, PendingIntent.FLAG_UPDATE_CURRENT);

        // Create countdown notification using a chronometer style.
        Bitmap bitmap = Bitmap.createBitmap(320,320, Bitmap.Config.ARGB_8888);
        bitmap.eraseColor(wearableTimer.getColor().color);
        return new Notification.Builder(context)
                .setSmallIcon(R.drawable.ic_cc_alarm)
                .setContentTitle(wearableTimer.getName())
                .setContentText(TimerFormat.getTimeString(wearableTimer.getDuration()))
                .setUsesChronometer(true)
                .setWhen(System.currentTimeMillis() + wearableTimer.getDuration())
                        //TODO Добавил и закомментировал не устаревшие методы
                        //.addAction(new Notification.Action.Builder(Icon.createWithResource(this, R.drawable.ic_cc_alarm), getString(R.string.timer_restart), pendingIntentRestart).build())
                        //.addAction(new Notification.Action.Builder(Icon.createWithResource(this, R.drawable.ic_cc_alarm), getString(R.string.timer_delete), pendingIntentDelete).build())
                .addAction(R.drawable.ic_cc_alarm, context.getString(R.string.timer_restart),
                        pendingIntentRestart)
                .addAction(R.drawable.ic_cc_alarm, context.getString(R.string.timer_delete),
                        pendingIntentDelete)
                .setDeleteIntent(pendingIntentDelete)
                .setLocalOnly(true)
                .setColor(wearableTimer.getColor().color)
                .extend(new Notification.WearableExtender().setBackground(bitmap))
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
}
