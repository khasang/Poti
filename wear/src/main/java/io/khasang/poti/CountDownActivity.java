package io.khasang.poti;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Handler;
import android.os.SystemClock;
import android.os.Vibrator;
import android.util.Log;
import android.view.WindowManager;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.google.common.hash.Hasher;

public class CountDownActivity extends Activity {
    TextView tvCountDown;
    private long startTime = 0L;
    private Long startTimeCurrentTime = 0L;
    private Handler myHandler = new Handler();
    final long SLEEP_ACTIVITY = 300000L;
    long timeInMillies = 0L;
    long timeSwap = 0L;
    long finalTime = 0L;
    CountDownTimer countDownTimer;
    long duration;
    int color;
    Vibrator vibrator;
    AppData appData;
    private Runnable updateTimerMethod = new Runnable() {
        public void run() {
            timeInMillies = SystemClock.uptimeMillis() - startTime;
            finalTime = timeSwap + timeInMillies;
// TODO: 24.12.2015 ni032mas
//            int seconds = (int) (finalTime / 1000);
//            int minutes = seconds / 60;
//            seconds = seconds % 60;
//            int milliseconds = (int) (finalTime % 1000);
//            tvCountDown.setText("" + minutes + ":"
//                    + String.format("%02d", seconds) + ":"
//                    + String.format("%03d", milliseconds));
            tvCountDown.setText("-" + WearableTimer.getDurationString(finalTime));
            myHandler.postDelayed(this, 0);
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.countdown_activity);
        App app = (App) getApplication();
        appData = app.appData;
        vibrator = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);
        vibrator.cancel();
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
        tvCountDown = (TextView) findViewById(R.id.tv_countdown);
        Intent data = getIntent();
        if (data.hasExtra(NotificationTimer.TIMER_DURATION)) {
            duration = data.getLongExtra(NotificationTimer.TIMER_DURATION, 0);
        }
        if (data.hasExtra(NotificationTimer.TIMER_DURATION)) {
            color = data.getIntExtra(NotificationTimer.TIMER_COLOR, 0);
        }
        if (data.hasExtra(NotificationTimer.TIMER_CURRENT_TIME)) {
            startTimeCurrentTime = data.getLongExtra(NotificationTimer.TIMER_CURRENT_TIME, 0);
        }
        RelativeLayout relativeLayout = (RelativeLayout) findViewById(R.id.rl_countdown);
        relativeLayout.setBackgroundColor(color);
        if (startTimeCurrentTime > 0L && duration - (System.currentTimeMillis() - startTimeCurrentTime) > 0) {
            countDownTimer = new CountDownWearableTimer(duration - (System.currentTimeMillis() - startTimeCurrentTime), 1);
            countDownTimer.start();
        } else if (startTimeCurrentTime > 0L && duration - (System.currentTimeMillis() - startTimeCurrentTime) < 0) {
            startTime = SystemClock.uptimeMillis();
            timeSwap = startTime - startTimeCurrentTime;
            myHandler.postDelayed(updateTimerMethod, 0);
        } else if (startTimeCurrentTime.equals(0L)) {
            countDownTimer = new CountDownWearableTimer(duration, 1);
            countDownTimer.start();
        }
        Handler handlerSleepActivity = new Handler();
        handlerSleepActivity.postDelayed(new Runnable() {
            @Override
            public void run() {
                getWindow().clearFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
            }
        }, SLEEP_ACTIVITY);
        if (data.hasExtra(NotificationTimer.TIMER_VIBRATE) && data.getBooleanExtra(NotificationTimer.TIMER_VIBRATE, false)) {
            vibrator.vibrate(appData.getTimer());
        }
    }

    class CountDownWearableTimer extends CountDownTimer {
        public CountDownWearableTimer(long millisInFuture, long countDownInterval) {
            super(millisInFuture, countDownInterval);
        }

        @Override
        public void onFinish() {
            startTime = SystemClock.uptimeMillis();
            myHandler.postDelayed(updateTimerMethod, 0);
        }

        @Override
        public void onTick(long millisUntilFinished) {
            tvCountDown.setText(WearableTimer.getDurationString(millisUntilFinished) + "");
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
    }
}
