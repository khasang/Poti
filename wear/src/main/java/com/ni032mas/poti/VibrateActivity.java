package com.ni032mas.poti;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.os.Vibrator;
import android.support.wearable.view.CircularButton;
import android.view.MotionEvent;
import android.view.View;

import com.google.common.primitives.Longs;

import java.util.ArrayList;
import java.util.Timer;

public class VibrateActivity extends Activity {
    ArrayList<Long> patternVibrate = new ArrayList<>();
    boolean isRecStart;
    long startTime;
    long startTimeUp;
    long endTime;
    long endTimeUp;
    Vibrator vibrator;
    AppData appData;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.vibrate_layout);
        App app = (App) getApplication();
        appData = app.appData;
        vibrator = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);
        CircularButton cbSetVibrate = (CircularButton) findViewById(R.id.cb_set_vibrate);
        cbSetVibrate.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (isRecStart) {
                    if (event.getAction() == MotionEvent.ACTION_DOWN) {
                        startTime = event.getEventTime();
                        vibrator.vibrate(60000);
                    }
                    if (event.getAction() == MotionEvent.ACTION_UP) {
                        vibrator.cancel();
                        endTime = event.getEventTime();
                    }
                    if (endTime - startTime > 0) {
                        patternVibrate.add(endTime - startTime);
                    } else if (startTime - endTime > 0 && endTime > 0) {
                        patternVibrate.add(startTime - endTime);
                    }
                    return true;
                } else {
                    return false;
                }
            }
        });
        final CircularButton cbVibrateRec = (CircularButton) findViewById(R.id.cb_set_vibrate_rec);
        cbVibrateRec.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                isRecStart = !isRecStart;
                if (isRecStart) {
                    cbVibrateRec.setColor(R.color.red);
                    patternVibrate = new ArrayList<>();
                } else if (!isRecStart && patternVibrate.size() > 0) {
                    //cbVibrateRec.setColor(R.color.light_blue500);
                    appData.getLastTimer().setVibration(Longs.toArray(patternVibrate));
                }
            }
        });
        CircularButton cbVibratePlay = (CircularButton) findViewById(R.id.cb_set_vibrate_play);
        cbVibratePlay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                vibrator.vibrate(appData.getLastTimer().getVibration(), -1);
            }
        });
    }
}
