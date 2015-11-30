package com.ni032mas.poti;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.wearable.view.WearableListView;
import android.util.Log;

/**
 * Created by ivansv on 20.11.2015.
 */
public class TimerFeaturesActivity extends Activity {
    public static final String[] FEATURES = new String[]{"Name", "Duration", "Color", "Save", "Start"};
    public static final int DURATION_REQUEST_CODE = 11;
    public static WearableTimer wearTimer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.settings_wlistview);
        wearTimer = new WearableTimer();

        WearableListView wearableListViewFeatures = (WearableListView) findViewById(R.id.settings_list);
        SettingsWearableAdapter settingsAdapter = new SettingsWearableAdapter(this, FEATURES);
        wearableListViewFeatures.setAdapter(settingsAdapter);
        wearableListViewFeatures.setClickListener(featuresClickListener);
    }

    private WearableListView.ClickListener featuresClickListener = new WearableListView.ClickListener() {
        @Override
        public void onClick(WearableListView.ViewHolder viewHolder) {
            int position = viewHolder.getPosition();
            String setting = FEATURES[position];
            switch (setting) {
                case "Name":
                    break;
                case "Duration":
                    Intent newIntent = new Intent(TimerFeaturesActivity.this, SetDurationActivity.class);
                    startActivityForResult(newIntent, DURATION_REQUEST_CODE);
                    break;
                case "Color":
                    break;
                case "Save":
                    App.appData.wearableTimers.add(wearTimer);
                    App.saveLoadDataJSON.saveJSON(App.appData, App.DATA_MAP_KEY);
                    Intent intent = new Intent(TimerFeaturesActivity.this, MainActivity.class);
                    startActivity(intent);
                    TimerFeaturesActivity.this.finish();
                    break;
                case "Start":
                    NotificationTimer nt = new NotificationTimer(TimerFeaturesActivity.this);
                    nt.setupTimer(wearTimer.getDuration());
                    break;
            }
        }

        @Override
        public void onTopEmptyRegionClick() {
        }
    };

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent intent) {
        super.onActivityResult(requestCode, resultCode, intent);
        switch (requestCode) {
            case DURATION_REQUEST_CODE:
                if (resultCode != RESULT_OK) {
                    return;
                }
                if (intent.hasExtra(SetDurationActivity.RETURN_KEY)) {
                    long duration = intent.getExtras().getLong(SetDurationActivity.RETURN_KEY);
                    wearTimer.setDuration(duration);
                    wearTimer.setName(String.valueOf(duration / 1000) + "sec");
                    Log.i("Duration", String.valueOf(duration));
                }
                break;
        }
    }
}
