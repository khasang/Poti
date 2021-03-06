package io.khasang.poti.notifications;

import android.app.Activity;
import android.app.AlarmManager;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.util.Log;

import java.util.ArrayList;

import io.khasang.poti.R;
import io.khasang.poti.WearableTimer;
import io.khasang.poti.activity.CountDownActivity;
import io.khasang.poti.services.TimerNotificationService;
import io.khasang.poti.util.Constants;
import io.khasang.poti.util.TimerFormat;

public class NotificationTimer {
    public static final int WIDTH_BG = 480;
    public static final int HEIGHT_BG = 480;
    Activity activity;
    Context context;
    WearableTimer wearableTimer;
    int timerN;
    public static ArrayList<Integer> timers = new ArrayList<>();

    public NotificationTimer(Activity activity, WearableTimer wearableTimer, int timerN) {
        this.context = activity.getApplicationContext();
        this.activity = activity;
        this.wearableTimer = wearableTimer;
        this.timerN = timerN;
        timers.add(new Integer(timerN));
    }

    public NotificationTimer(Context context, WearableTimer wearableTimer, int timerN) {
        this.context = context;
        this.wearableTimer = wearableTimer;
        this.timerN = timerN;
    }

    public void setupTimer() {
        NotificationManager notifyMgr =
                ((NotificationManager) context.getSystemService(context.NOTIFICATION_SERVICE));
        notifyMgr.notify(timerN, buildNotification());
        registerWithAlarmManager();
        if (activity != null) {
            activity.finish();
            Intent intent = new Intent(context, CountDownActivity.class)
                    .putExtra(Constants.TIMER_N, timerN)
                    .putExtra(Constants.TIMER_NAME, wearableTimer.getName())
                    .putExtra(Constants.TIMER_DURATION, wearableTimer.getDuration())
                    .putExtra(Constants.TIMER_COLOR, wearableTimer.getColor().color);
            activity.startActivity(intent);
        }
    }

    private void registerWithAlarmManager() {
        AlarmManager alarm = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
        Intent intent = new Intent(Constants.ACTION_SHOW_ALARM, null, context,
                TimerNotificationService.class)
                .putExtra(Constants.TIMER_N, timerN)
                .putExtra(Constants.TIMER_NAME, wearableTimer.getName())
                .putExtra(Constants.TIMER_COLOR, wearableTimer.getColor().color);
        PendingIntent pendingIntent = PendingIntent.getService(context, timerN, intent,
                PendingIntent.FLAG_UPDATE_CURRENT);
        long wakeupTime = System.currentTimeMillis() + wearableTimer.getDuration();
        alarm.setExact(AlarmManager.RTC_WAKEUP, wakeupTime, pendingIntent);
        Log.i("LOGППППППППП", "Зарегистрирован таймер: " + timerN + " " + wearableTimer.getName() + " " + wearableTimer.getColor().color);
    }

    private Notification buildNotification() {
        // Intent to restart a timer.
        Intent restartIntent = new Intent(Constants.ACTION_RESTART_ALARM, null, context,
                TimerNotificationService.class)
                .putExtra(Constants.TIMER_N, timerN);
        PendingIntent pendingIntentRestart = PendingIntent
                .getService(context, timerN, restartIntent, PendingIntent.FLAG_UPDATE_CURRENT);
        // Intent to delete a timer.
        Intent deleteIntent = new Intent(Constants.ACTION_DELETE_ALARM, null, context,
                TimerNotificationService.class)
                .putExtra(Constants.TIMER_N, timerN);
        // FIXME: 23.02.2016 
        Log.i("LOG", "Создать интент для удаления таймера " + timerN);
        PendingIntent pendingIntentDelete = PendingIntent
                .getService(context, timerN, deleteIntent, PendingIntent.FLAG_UPDATE_CURRENT);


        // Intent to start activity.
        Intent intentStartActivity = new Intent(Constants.ACTION_FULLSCREEN, null, context,
                CountDownActivity.class)
                .putExtra(Constants.TIMER_N, timerN)
                .putExtra(Constants.TIMER_NAME, wearableTimer.getName())
                .putExtra(Constants.TIMER_DURATION, wearableTimer.getDuration())
                .putExtra(Constants.TIMER_CURRENT_TIME, System.currentTimeMillis())
                .putExtra(Constants.TIMER_COLOR, wearableTimer.getColor().color);
        PendingIntent pendingStartActivity = PendingIntent.getActivity(context, timerN, intentStartActivity, PendingIntent.FLAG_UPDATE_CURRENT);
        // FIXME: 23.02.2016
        Log.i("LOG", "Создать интент для переключения таймера " + timerN);
        // Create countdown notification using a chronometer style.
        Bitmap bitmap = Bitmap.createBitmap(WIDTH_BG, HEIGHT_BG, Bitmap.Config.ARGB_8888);
        bitmap.eraseColor(wearableTimer.getColor().color);
        Notification.Builder builder = new Notification.Builder(context)
                .setSmallIcon(R.drawable.ic_poti_draw)
                .setContentTitle(wearableTimer.getName())
                .setContentText(TimerFormat.getTimeString(wearableTimer.getDuration()))
                .setUsesChronometer(true)
                .setWhen(System.currentTimeMillis() + wearableTimer.getDuration())
                .addAction(R.drawable.ic_fullscreen_white_48dp, context.getString(R.string.fullscreen),
                        pendingStartActivity)
                .addAction(R.drawable.ic_refresh_white_48dp, context.getString(R.string.timer_restart),
                        pendingIntentRestart)
                .addAction(R.drawable.ic_delete_white_48dp, context.getString(R.string.timer_delete),
                        pendingIntentDelete)
                .setDeleteIntent(pendingIntentDelete)
                .setLocalOnly(true)
                .setColor(wearableTimer.getColor().color)
                .setShowWhen(false)
                .extend(new Notification.WearableExtender()
                        .setBackground(bitmap));
        if(android.os.Build.VERSION.SDK_INT >= 21){
            builder.setColor(wearableTimer.getColor().color);
        } else {
            builder.setColor(wearableTimer.getColor().color);
        }
        return builder.build();
    }
}
