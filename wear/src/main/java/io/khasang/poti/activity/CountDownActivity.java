package io.khasang.poti.activity;

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

import io.khasang.poti.AppData;
import io.khasang.poti.R;
import io.khasang.poti.WearableTimer;
import io.khasang.poti.util.Constants;

public class CountDownActivity extends Activity {
    TextView tvCountDown;
    private long startTime = 0L;
    private Long startTimeCurrentTime = 0L;
    private Handler handlerCountDown = new Handler();
    final long SLEEP_ACTIVITY = 300000L;
    long timeInMillies = 0L;
    long timeSwap = 0L;
    long finalTime = 0L;
    long endDuration = 0L;
    CountDownTimer countDownTimer;
    long duration;
    int color;
    boolean isResume;
    Vibrator vibrator;
    AppData appData;
    TextView tvTimerName;
    WearableTimer wearableTimer;
    private long[] patternVibrate;
    private Runnable updateTimerMethod = new Runnable() {
        public void run() {
            timeInMillies = SystemClock.uptimeMillis() - startTime;
            finalTime = timeSwap + timeInMillies;
            tvCountDown.setText("-" + WearableTimer.getDurationString(finalTime));
            handlerCountDown.postDelayed(this, 0);
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.countdown_activity);
        appData = AppData.getInstance(getApplicationContext());
        vibrator = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);
        vibrator.cancel();
        tvTimerName = (TextView) findViewById(R.id.tv_timer_name);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
        tvCountDown = (TextView) findViewById(R.id.tv_countdown);
        Intent data = getIntent();
        initCountDown(data);
    }

    private void initCountDown(Intent data) {
        if (data.hasExtra(Constants.TIMER_CURRENT_TIME)) {
            startTimeCurrentTime = data.getLongExtra(Constants.TIMER_CURRENT_TIME, 0);
        }
        if (data.hasExtra(Constants.TIMER_N)) {
            int indexTimer = data.getIntExtra(Constants.TIMER_N, 0);
            // FIXME: 23.02.2016 
            Log.i("Log", "Таймер на экране: " + indexTimer);
            if (indexTimer >= 0) {
                wearableTimer = appData.getTimer(indexTimer);
                patternVibrate = wearableTimer.getVibration();
                duration = wearableTimer.getDuration();
                tvTimerName.setText(wearableTimer.getName());
                RelativeLayout relativeLayout = (RelativeLayout) findViewById(R.id.rl_countdown);
                relativeLayout.setBackgroundColor(wearableTimer.getColor().color);
            }
        }
        if (startTimeCurrentTime > 0L && duration - (System.currentTimeMillis() - startTimeCurrentTime) > 0) {
            startCountDown(duration - (System.currentTimeMillis() - startTimeCurrentTime));
        } else if (startTimeCurrentTime > 0L && duration - (System.currentTimeMillis() - startTimeCurrentTime) < 0) {
            startTime = SystemClock.uptimeMillis();
            timeSwap = startTime - startTimeCurrentTime;
            finishCountDown();
        } else if (startTimeCurrentTime.equals(0L)) {
            startCountDown(duration);
        }
        Handler handlerSleepActivity = new Handler();
        handlerSleepActivity.postDelayed(new Runnable() {
            @Override
            public void run() {
                getWindow().clearFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
            }
        }, SLEEP_ACTIVITY);
    }

    private void startCountDown(long millisInFuture) {
        countDownTimer = new CountDownWearableTimer(millisInFuture, 1);
        countDownTimer.start();
    }

    class CountDownWearableTimer extends CountDownTimer {
        public CountDownWearableTimer(long millisInFuture, long countDownInterval) {
            super(millisInFuture, countDownInterval);
        }

        @Override
        public void onFinish() {
            if (patternVibrate != null) {
                vibrator.vibrate(patternVibrate, -1);
            }
            startTime = SystemClock.uptimeMillis();
            finishCountDown();
        }

        @Override
        public void onTick(long millisUntilFinished) {
            tvCountDown.setText(WearableTimer.getDurationString(millisUntilFinished) + "");
            endDuration = millisUntilFinished;
        }
    }

    private void finishCountDown() {
        if (patternVibrate != null) {
            vibrator.vibrate(patternVibrate, -1);
        }
        tvTimerName.setText(wearableTimer.getName() + " " + getResources().getString(R.string.timer_done));
        handlerCountDown.postDelayed(updateTimerMethod, 0);
    }

    @Override
    protected void onPause() {
        super.onPause();
        vibrator.cancel();
        isResume = false;
        Handler handlerActivityFinish = new Handler();
        handlerActivityFinish.postDelayed(new Runnable() {
            @Override
            public void run() {
                if (!isResume) {
                    finish();
                }
            }
        }, 1000);
    }

    @Override
    protected void onResume() {
        super.onResume();
        isResume = true;
    }

    @Override
    protected void onStop() {
        super.onStop();
        vibrator = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);
        vibrator.cancel();
        if (countDownTimer != null) {
            countDownTimer.cancel();
        }
        finish();
    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        if (countDownTimer != null) {
            countDownTimer.cancel();
        }
        initCountDown(intent);
    }
}
