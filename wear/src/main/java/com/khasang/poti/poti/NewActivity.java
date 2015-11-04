package com.khasang.poti.poti;

import android.app.Activity;
import android.os.Bundle;
import android.support.wearable.view.GridViewPager;

import java.io.InputStream;
import java.util.Scanner;

/**
 * Created by nadezhda on 14.10.2015.
 */
public class NewActivity extends Activity {
    private GridViewPager mGridViewPager;
    private String jsonVocabularyList;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.new_activity);
        Timers timerList = new Timers();
        timerList.loadTimers(this);
        mGridViewPager = (GridViewPager)findViewById(R.id.gridview);
//        mGridViewPager.setAdapter(new NewAdapter(this, getFragmentManager(),
//                timerList));
        mGridViewPager.setAdapter(new NewAdapter(this, timerList));

    }

}
