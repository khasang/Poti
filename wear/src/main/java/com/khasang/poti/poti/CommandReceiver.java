package com.khasang.poti.poti;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.PowerManager;

/**
 * Created by nadezhda on 12.10.2015.
 */
public class CommandReceiver extends BroadcastReceiver {

    private static final String ACTION_TRIGGER_ALARM =
            "com.khasang.poti.poti.action.TRIGGER_ALARM";
    private static final String ACTION_PAUSE_TIMER =
            "com.khasang.poti.poti.action.PAUSE_TIMER";
    private static final String ACTION_STOP_TIMER =
            "com.khasang.poti.poti.action.STOP_TIMER";
    private static final String EXTRA_START_TIME = "start_time";
    private static PowerManager.WakeLock mWakeLock;


    @Override
    public void onReceive(Context context, Intent intent) {
        String action = intent.getAction();
        if(ACTION_TRIGGER_ALARM.equals(action)) {
            Intent completedIntent = new Intent(context, TimerCompletedActivity.class);
            completedIntent.putExtra(TimerCompletedActivity.EXTRA_START_TIME,
                    intent.getLongExtra(EXTRA_START_TIME, 0L));
            completedIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            CommandReceiver.startWakefulActivity(context, completedIntent);
        } else if(ACTION_PAUSE_TIMER.equals(action)) {
            Timer.pauseTimer(context);
        } else if(ACTION_STOP_TIMER.equals(action)) {
            Timer.stopTimer(context);
        }
    }

    public static Intent makeTriggerAlarmIntent(long startTime){
        Intent intent = new Intent(ACTION_TRIGGER_ALARM);
        intent.putExtra(EXTRA_START_TIME, startTime);
        return intent;
    }

    public static Intent makePauseTimerIntent(){
        return new Intent(ACTION_PAUSE_TIMER);
    }

    public static Intent makeStopTimerIntent(){
        return new Intent(ACTION_STOP_TIMER);
    }

    public static void startWakefulActivity(Context context, Intent intent) {
        PowerManager pm = (PowerManager)context.getSystemService(Context.POWER_SERVICE);
        mWakeLock = pm.newWakeLock(PowerManager.SCREEN_BRIGHT_WAKE_LOCK |
                PowerManager.ACQUIRE_CAUSES_WAKEUP |
                PowerManager.ON_AFTER_RELEASE, "wakeful activity lock");
        mWakeLock.setReferenceCounted(false);
        mWakeLock.acquire(60 * 1000);
        context.startActivity(intent);
    }

    public static void completeWakefulIntent() {
        if(mWakeLock != null) {
            mWakeLock.release();
            mWakeLock = null;
        }
    }
}
