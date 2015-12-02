package com.ni032mas.poti;

import android.app.Fragment;
import android.app.FragmentManager;
import android.content.Context;
import android.support.v4.view.ViewPager;
import android.support.wearable.view.FragmentGridPagerAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.lang.reflect.Type;
import java.util.ArrayList;

public class TimerFragmentPagerAdapter extends FragmentGridPagerAdapter {

    Fragment fragmentFirstPage;
    Fragment fragmentSecondPage;
    FragmentManager fragmentManager;
    Context context;

    public TimerFragmentPagerAdapter(FragmentManager fm, Context context) {
        super(fm);
        this.fragmentManager = fm;
        this.context = context;
    }

    @Override
    public Fragment getFragment(int row, int column) {
        if (column == 0) {
            if (fragmentFirstPage == null) {
                fragmentFirstPage = TimersFragment.newInstance(this);
            }
        }
        else {
            if (fragmentSecondPage == null) {
                fragmentSecondPage = GeneralSettingsFragment.newInstance(this);
            }
            return fragmentSecondPage;
        }
        return fragmentFirstPage;
    }

//    @Override
//    public Fragment instantiateItem(ViewGroup container, int row, int column) {
//        if (column == 2) {
//            fragmentSecondPage = GeneralSettingsFragment.newInstance(this);
//        }
//        return fragmentSecondPage;
//    }

    @Override
    public int getRowCount() {
        return 1;
    }

    @Override
    public int getColumnCount(int i) {
        return 2;
    }
}
