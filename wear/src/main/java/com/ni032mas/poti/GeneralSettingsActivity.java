package com.ni032mas.poti;

import android.app.Activity;
import android.app.FragmentManager;
import android.os.Bundle;

/**
 * Created by ni032_000 on 04.12.2015.
 */
public class GeneralSettingsActivity extends Activity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fragment);
        GeneralSettingsFragment generalSettingsFragment = GeneralSettingsFragment.newInstance();
        FragmentManager fragmentManager = getFragmentManager();
        fragmentManager.beginTransaction()
                .replace(R.id.frame_layout, generalSettingsFragment)
                .commit();
    }
}
