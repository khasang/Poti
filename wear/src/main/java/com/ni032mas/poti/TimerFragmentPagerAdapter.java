package com.ni032mas.poti;

import android.app.Fragment;
import android.app.FragmentManager;
import android.support.wearable.view.FragmentGridPagerAdapter;

import java.lang.reflect.Type;
import java.util.ArrayList;

public class TimerFragmentPagerAdapter extends FragmentGridPagerAdapter {

    Fragment fragmentFirstPage;
    Fragment fragmentSecondPage;
    FragmentManager fragmentManager;

    public TimerFragmentPagerAdapter(FragmentManager fm) {
        super(fm);
        this.fragmentManager = fm;
    }

    @Override
    public Fragment getFragment(int row, int column) {
        if (column == 0) {
            if (fragmentFirstPage == null) {
                fragmentFirstPage = TimersFragment.newInstance();
            }
            return fragmentFirstPage;
        } else {
            if (fragmentSecondPage == null) {
                fragmentSecondPage = GeneralSettingsFragment.newInstance(this);
            }
            return fragmentSecondPage;
        }
    }

    @Override
    public int getRowCount() {
        return 1;
    }

    @Override
    public int getColumnCount(int i) {
        return 2;
    }

    public interface FirstPageFragmentListener {
        void onSwitchToNextFragment();
    }
}
