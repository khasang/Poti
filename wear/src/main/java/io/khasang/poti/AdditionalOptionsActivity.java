package io.khasang.poti;

import android.app.Activity;
import android.app.FragmentManager;
import android.os.Bundle;

public class AdditionalOptionsActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.additional_options_activity);
        initFragment();
    }

    @Override
    protected void onResume() {
        super.onResume();
        initFragment();
    }

    private void initFragment() {
        AdditionalOptionsFragment additionalOptionsFragment = AdditionalOptionsFragment.newInstance();
        FragmentManager fragmentManager = getFragmentManager();
        fragmentManager.beginTransaction()
                .replace(R.id.frame_layout, additionalOptionsFragment)
                .commit();
    }
}
