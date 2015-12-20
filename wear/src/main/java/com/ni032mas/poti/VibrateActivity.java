package com.ni032mas.poti;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.os.Vibrator;
import android.support.wearable.view.CircularButton;
import android.view.MotionEvent;
import android.view.View;

import java.util.ArrayList;
import java.util.Timer;

/**
 * Created by ni032_000 on 20.12.2015.
 */
public class VibrateActivity extends Activity {
    ArrayList<Long> patternVibrate = new ArrayList<>();
    boolean isRecStart;
    long startTime;
    long startTimeUp;
    long endTime;
    long endTimeUp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.vibrate_layout);
        CircularButton cbSetVibrate = (CircularButton) findViewById(R.id.cb_set_vibrate);
        cbSetVibrate.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (isRecStart) {
                    Vibrator vibrator = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);
                    if (event.getAction() == MotionEvent.ACTION_DOWN) {
                        startTime = event.getEventTime();
                        vibrator.vibrate(60000);
                    }
                    if (event.getAction() == MotionEvent.ACTION_UP) {
                        vibrator.cancel();
                        endTime = event.getEventTime();
                    }
                    if (endTime - startTime > 0 || startTime - endTime > 0) {
                        patternVibrate.add(endTime - startTime);
                    }
                    return true;
                } else {
                    return false;
                }
            }
        });
        final CircularButton cbStartRec = (CircularButton) findViewById(R.id.cb_set_vibrate_start);
        cbStartRec.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                isRecStart = !isRecStart;
                if (isRecStart) {
                    cbStartRec.setColor(R.color.red);
                } else {
                    cbStartRec.setColor(R.color.light_blue500);
                }
            }
        });

    }
}
