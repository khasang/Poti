package io.khasang.poti.activity;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.os.Vibrator;
import android.support.wearable.view.CircularButton;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.TextView;

import com.google.common.primitives.Longs;

import java.util.ArrayList;

import io.khasang.poti.AppData;
import io.khasang.poti.R;
import io.khasang.poti.WearableTimer;

public class VibrateActivity extends Activity {
    ArrayList<Long> patternVibrate = new ArrayList<>();
    boolean isRecStart;
    long startTime;
    long endTime;
    Vibrator vibrator;
    AppData appData;
    TextView tvPlay;
    TextView tvRec;
    CircularButton cbVibrateRec;
    CircularButton cbVibratePlay;
    CircularButton cbSetVibrateDefault;
    CircularButton cbSetVibrate;
    private boolean isPlayStart;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.vibrate_layout);
        appData = AppData.getInstance(getApplicationContext());
        vibrator = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);
        tvRec = (TextView) findViewById(R.id.tv_rec);
        tvPlay = (TextView) findViewById(R.id.tv_play);
        cbVibrateRec = (CircularButton) findViewById(R.id.cb_set_vibrate_rec);
        cbVibratePlay = (CircularButton) findViewById(R.id.cb_set_vibrate_play);
        cbSetVibrateDefault = (CircularButton) findViewById(R.id.cb_set_vibrate_default);
        cbSetVibrate = (CircularButton) findViewById(R.id.cb_set_vibrate);
        cbSetVibrate.setRippleColor(R.color.grey600);
        cbSetVibrate.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (isRecStart) {
                    if (event.getAction() == MotionEvent.ACTION_DOWN) {
                        startTime = event.getEventTime();
                        if (startTime - endTime > 0 && endTime > 0) {
                            patternVibrate.add(startTime - endTime);
                        }
                        vibrator.vibrate(60000);
                    }
                    if (event.getAction() == MotionEvent.ACTION_UP) {
                        vibrator.cancel();
                        endTime = event.getEventTime();
                        if (endTime - startTime > 0) {
                            patternVibrate.add(endTime - startTime);
                        }
                    }
                }
                return false;
            }
        });
        cbVibrateRec.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                stopPlayVibrate();
                isRecStart = !isRecStart;
                if (isRecStart) {
                    patternVibrate = new ArrayList<>();
                    patternVibrate.add(new Long(0));
                    tvRec.setTextColor(getResources().getColor(R.color.red));
                    startTime = 0;
                    endTime = 0;
                } else if (!isRecStart && patternVibrate.size() > 0) {
                    appData.getLastTimer().setVibration(Longs.toArray(patternVibrate), getResources().getString(R.string.custom_label));
                    tvRec.setTextColor(getResources().getColor(R.color.black));
                } else if (!isRecStart) {
                    tvRec.setTextColor(getResources().getColor(R.color.black));
                }
            }
        });
        cbVibratePlay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!isRecStart && !isPlayStart) {
                    vibrator.vibrate(appData.getLastTimer().getVibration(), -1);
                    tvPlay.setText(getResources().getString(R.string.stop));
                    cbVibratePlay.setImageResource(R.drawable.stop);
                    tvPlay.setTextColor(getResources().getColor(R.color.red));
                    isPlayStart = true;
                } else {
                    stopPlayVibrate();
                }
            }
        });
        cbSetVibrateDefault.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                appData.getLastTimer().setVibration(WearableTimer.getVibratePattern(), getResources().getString(R.string.default_label));
            }
        });
        cbSetVibrate.setRippleColor(R.color.red);
        cbVibratePlay.setRippleColor(R.color.red);
        cbVibrateRec.setRippleColor(R.color.red);
        cbSetVibrateDefault.setRippleColor(R.color.red);
    }

    private void stopPlayVibrate() {
        vibrator.cancel();
        tvPlay.setText(getResources().getString(R.string.play));
        tvPlay.setTextColor(getResources().getColor(R.color.black));
        cbVibratePlay.setImageResource(R.drawable.play);
        isPlayStart = false;
    }

    @Override
    protected void onResume() {
        super.onResume();
        isRecStart = false;
    }

    @Override
    protected void onPause() {
        super.onPause();
        finish();
    }
}
