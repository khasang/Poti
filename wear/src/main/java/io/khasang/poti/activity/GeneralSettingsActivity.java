package io.khasang.poti.activity;

import android.app.Activity;
import android.app.FragmentManager;
import android.os.Bundle;

import io.khasang.poti.fragments.GeneralSettingsFragment;
import io.khasang.poti.R;

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
