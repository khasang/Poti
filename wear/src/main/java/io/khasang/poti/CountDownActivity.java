package io.khasang.poti;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Handler;
import android.os.SystemClock;
import android.util.Log;
import android.widget.RelativeLayout;
import android.widget.TextView;

public class CountDownActivity extends Activity {
    TextView tvCountDown;
    private long startTime = 0L;
    private long startTimeCurrentTime = 0L;
    private Handler myHandler = new Handler();
    long timeInMillies = 0L;
    long timeSwap = 0L;
    long finalTime = 0L;
    CountDownTimer countDownTimer;
    long duration;
    int color;
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
        tvCountDown = (TextView) findViewById(R.id.tv_countdown);
        Log.d("LOG", "Результат");
        Intent data = getIntent();
        if (data.hasExtra(NotificationTimer.TIMER_DURATION)) {
            duration = data.getLongExtra(NotificationTimer.TIMER_DURATION, 0);
        }
        if (data.hasExtra(NotificationTimer.TIMER_DURATION)) {
            color = data.getIntExtra(NotificationTimer.TIMER_COLOR, 0);
        }
        if (data.hasExtra(NotificationTimer.TIMER_CURRENT_TIME)) {
            startTimeCurrentTime = data.getIntExtra(NotificationTimer.TIMER_CURRENT_TIME, 0);
        }
        RelativeLayout relativeLayout = (RelativeLayout) findViewById(R.id.rl_countdown);
        relativeLayout.setBackgroundColor(color);
        if (startTimeCurrentTime > 0 || duration - (System.currentTimeMillis() - startTimeCurrentTime ) > 0) {
            countDownTimer = new CountDownWearableTimer(duration - (System.currentTimeMillis() - startTimeCurrentTime), 1);
            countDownTimer.start();
        } else if (startTimeCurrentTime > 0 || duration - (System.currentTimeMillis() - startTimeCurrentTime ) < 0) {

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
}
