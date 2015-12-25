/*
 * Copyright (C) 2014 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package io.khasang.poti;

import android.app.AlarmManager;
import android.app.IntentService;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.SystemClock;
import android.util.Log;

import io.khasang.poti.util.Constants;

public class TimerNotificationService extends IntentService {
    AppData appData;

    public static final String TAG = "TimerNotificationSvc";

    public TimerNotificationService() {
        super(TAG);
    }

    @Override
    public void onCreate() {
        super.onCreate();
        App app = (App) getApplication();
        appData = app.appData;
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        if (Log.isLoggable(TAG, Log.DEBUG)) {
            Log.d(TAG, "onHandleIntent called with intent: " + intent);
        }
        String action = intent.getAction();
        if (Constants.ACTION_SHOW_ALARM.equals(action)) {
            showTimerDoneNotification(intent.getIntExtra(NotificationTimer.TIMER_N, 0),
                    intent.getStringExtra(NotificationTimer.TIMER_NAME),
                    intent.getIntExtra(NotificationTimer.TIMER_COLOR, getResources().getColor(R.color.light_blue500)));
        } else if (Constants.ACTION_DELETE_ALARM.equals(action)) {
            deleteTimer(intent.getIntExtra(NotificationTimer.TIMER_N, 0));
        } else if (Constants.ACTION_RESTART_ALARM.equals(action)) {
            restartAlarm(intent.getIntExtra(NotificationTimer.TIMER_N, 0));
        } else {
            throw new IllegalStateException("Undefined constant used: " + action);
        }
    }

    private void restartAlarm(int timer) {
        NotificationTimer notificationTimer = new NotificationTimer(getApplicationContext(), appData.getTimer(timer), timer);
        notificationTimer.setupTimer();
        if (Log.isLoggable(TAG, Log.DEBUG)) {
            Log.d(TAG, "Timer restarted.");
        }
    }

    private void deleteTimer(int timer) {
        cancelCountdownNotification(timer);
        AlarmManager alarm = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
        Intent intent = new Intent(Constants.ACTION_SHOW_ALARM, null, this,
                TimerNotificationService.class);
        PendingIntent pendingIntent = PendingIntent.getService(this, 0, intent,
                PendingIntent.FLAG_UPDATE_CURRENT);
        alarm.cancel(pendingIntent);
        if (Log.isLoggable(TAG, Log.DEBUG)) {
            Log.d(TAG, "Timer deleted.");
        }
    }

    private void cancelCountdownNotification(int timer) {
        NotificationManager notifyMgr =
                ((NotificationManager) getSystemService(NOTIFICATION_SERVICE));
        notifyMgr.cancel(timer);
    }

    private void showTimerDoneNotification(int timer, String timerName, int color) {
        // Cancel the countdown notification to show the "timer done" notification.
        cancelCountdownNotification(timer);

        // Create an intent to restart a timer.
        Intent restartIntent = new Intent(Constants.ACTION_RESTART_ALARM, null, this,
                TimerNotificationService.class)
                .putExtra(NotificationTimer.TIMER_N, timer)
                .putExtra(NotificationTimer.TIMER_NAME, timerName)
                .putExtra(NotificationTimer.TIMER_COLOR, color);
        PendingIntent pendingIntentRestart = PendingIntent
                .getService(this, 0, restartIntent, PendingIntent.FLAG_UPDATE_CURRENT);

        // Intent to start activity.
        Intent intentStartActivity = new Intent(this, CountDownActivity.class)
                .putExtra(NotificationTimer.TIMER_N, timer)
                .putExtra(NotificationTimer.TIMER_NAME, timerName)
                .putExtra(NotificationTimer.TIMER_DURATION, appData.getTimer(timer).getDuration())
                .putExtra(NotificationTimer.TIMER_CURRENT_TIME, SystemClock.uptimeMillis())
                .putExtra(NotificationTimer.TIMER_COLOR, color);
        PendingIntent pendingStartActivity = PendingIntent.getActivity(this, 0, intentStartActivity, PendingIntent.FLAG_UPDATE_CURRENT);

        // Create notification that timer has expired.
        Bitmap bitmap = Bitmap.createBitmap(320, 320, Bitmap.Config.ARGB_8888);
        bitmap.eraseColor(color);
        NotificationManager notifyMgr =
                ((NotificationManager) getSystemService(NOTIFICATION_SERVICE));
        Notification notif = new Notification.Builder(this)
                .setSmallIcon(R.drawable.ic_cc_alarm)
                .setContentTitle(timerName + " " + getResources().getString(R.string.timer_done))
                .setContentText(timerName + " " + getResources().getString(R.string.timer_done))
                .setUsesChronometer(true)
                .setWhen(System.currentTimeMillis())
                .addAction(R.drawable.ic_refresh_white_48dp, getString(R.string.timer_restart),
                        pendingIntentRestart)
                .addAction(R.drawable.ic_fullscreen_white_48dp, getString(R.string.fullscreen),
                        pendingStartActivity)
                .setLocalOnly(true)
                .extend(new Notification.WearableExtender().setBackground(bitmap))
                .setVibrate(WearableTimer.getVibratePattern())
                .build();
        notifyMgr.notify(timer, notif);
    }

}
