package io.khasang.poti;

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
    private boolean isActionDown;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.vibrate_layout);
        App app = (App) getApplication();
        appData = app.appData;
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
//                        Log.d("startTime", String.valueOf(startTime));
                        if (startTime - endTime > 0 && endTime > 0) {
                            patternVibrate.add(startTime - endTime);
                            Log.d("Тишина", String.valueOf(startTime - endTime));
                        }
                        vibrator.vibrate(60000);
                    }
                    if (event.getAction() == MotionEvent.ACTION_UP) {
                        vibrator.cancel();
                        endTime = event.getEventTime();
                        if (endTime - startTime > 0) {
                            patternVibrate.add(endTime - startTime);
                            Log.d("Вибрация", String.valueOf(endTime - startTime));
                        }
//                        Log.d("endTime", String.valueOf(endTime));
                    }
                    return false;
                } else {
                    return false;
                }
            }
        });
        cbVibrateRec.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                stopPlayVibrate();
                isRecStart = !isRecStart;
                if (isRecStart) {
                    patternVibrate = new ArrayList<>();
                    tvRec.setTextColor(getResources().getColor(R.color.red));
                    startTime = 0;
                    endTime = 0;
                } else if (!isRecStart && patternVibrate.size() > 0) {
                    appData.getLastTimer().setVibration(Longs.toArray(patternVibrate), getApplicationContext());
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
                appData.getLastTimer().setVibration(WearableTimer.getVibratePattern(), getApplicationContext());
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
}
