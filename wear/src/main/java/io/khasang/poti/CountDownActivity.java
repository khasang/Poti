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

public class CountDownActivity extends Activity {
    TextView tvCountDown;
    private long startTime = 0L;
    private Long startTimeCurrentTime = 0L;
    private Handler myHandler = new Handler();
    final long SLEEP_ACTIVITY = 300000L;
    long timeInMillies = 0L;
    long timeSwap = 0L;
    long finalTime = 0L;
    long endDuration = 0L;
    CountDownTimer countDownTimer;
    long duration;
    int color;
    Vibrator vibrator;
    boolean isVibrate;
    AppData appData;
    TextView tvTimerName;
    WearableTimer wearableTimer;
    private long[] patternVibrate;
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
        isVibrate = true;
        RelativeLayout relativeLayout = (RelativeLayout) findViewById(R.id.rl_countdown);
        relativeLayout.setBackgroundColor(color);
        tvTimerName = (TextView) findViewById(R.id.tv_timer_name);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
        tvCountDown = (TextView) findViewById(R.id.tv_countdown);
        Intent data = getIntent();
        if (data.hasExtra(NotificationTimer.TIMER_DURATION)) {
            duration = data.getLongExtra(NotificationTimer.TIMER_DURATION, 0);
            Log.d("LOG", duration + "");
        }
        if (data.hasExtra(NotificationTimer.TIMER_COLOR)) {
            color = data.getIntExtra(NotificationTimer.TIMER_COLOR, 0);
        }
        if (data.hasExtra(NotificationTimer.TIMER_CURRENT_TIME)) {
            startTimeCurrentTime = data.getLongExtra(NotificationTimer.TIMER_CURRENT_TIME, 0);
        }
        if (data.hasExtra(NotificationTimer.TIMER_N)) {
            int indexTimer = data.getIntExtra(NotificationTimer.TIMER_N, 0) - 1;
            if (indexTimer >= 0) {
                wearableTimer = appData.getTimer(indexTimer);
                patternVibrate = wearableTimer.getVibration();
                tvTimerName.setText(wearableTimer.getName());
            }
        }
        if (startTimeCurrentTime > 0L && duration - (System.currentTimeMillis() - startTimeCurrentTime) > 0) {
            startCountDown(duration - (System.currentTimeMillis() - startTimeCurrentTime));
        } else if (startTimeCurrentTime > 0L && duration - (System.currentTimeMillis() - startTimeCurrentTime) < 0) {
            Log.d("LOG", startTime + "startTime " + startTimeCurrentTime + "startTimeCurrentTime");
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
            if (isVibrate && patternVibrate != null) {
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
        tvTimerName.setText(wearableTimer.getName() + " " + getResources().getString(R.string.timer_done));
        myHandler.postDelayed(updateTimerMethod, 0);
    }

    @Override
    protected void onPause() {
        super.onPause();
        vibrator.cancel();
    }

    @Override
    protected void onStop() {
        super.onStop();
        vibrator = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);
        vibrator.cancel();
        Log.d("LOG", "Остановка вибрации");
//        isVibrate = false;
        finish();
    }
}
