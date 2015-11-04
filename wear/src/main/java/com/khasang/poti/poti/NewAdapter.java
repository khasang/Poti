package com.khasang.poti.poti;

import android.app.Fragment;
import android.app.FragmentManager;
import android.content.Context;
import android.content.res.Resources;
import android.graphics.drawable.Drawable;
import android.support.v4.view.ViewPager;
import android.support.wearable.view.CardFragment;
import android.support.wearable.view.FragmentGridPagerAdapter;
import android.support.wearable.view.GridPagerAdapter;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

/**
 * Created by nadezhda on 14.10.2015.
 */
//public class NewAdapter extends FragmentGridPagerAdapter {
public class NewAdapter extends GridPagerAdapter {

    private Timers timerList;
    private Drawable mBackground;
    private Context c;


    public NewAdapter(Context mContext,Timers timerList) {
        super();
        this.c = mContext;
        this.timerList = timerList;
    }

    //    public NewAdapter(Context context, FragmentManager fm,
//                             Timers timerList) {
//        super(fm);
//        this.timerList = timerList;
//
//    }
//
//    @Override
//    public Fragment getFragment(int row, int col) {
//        Timer2 tm = timerList.getItems().get(row);
//        String description = null;
//        switch (col) {
//            case 0:
//                description = tm.getName();
//                break;
//            case 1:
//                description = "\"" + tm.getDuration() + "\"";
//                break;
//            case 2:
//                description = tm.getName() + " " + tm.getDuration();
//                break;
//        }
//        CardFragment cardFragment = CardFragment.create(tm.getName() +
//                " (" + tm.getDuration() + ")", description);
//        cardFragment.setCardGravity(Gravity.BOTTOM);
//        cardFragment.setExpansionEnabled(true);
//        cardFragment.setExpansionDirection(CardFragment.EXPAND_DOWN);
//        cardFragment.setExpansionFactor(2f);
//        return cardFragment;
//    }

    @Override
    public int getRowCount() {
        return timerList.getItems().size();
    }

    @Override
    public int getColumnCount(int row) {
        if(timerList.getItems().get(row).getDuration() < 10) {
            return 3;
        } else {
            return 2;
        }

    }

    @Override
    public Object instantiateItem(ViewGroup viewGroup, int i, int i1) {

        Timer2 tm = timerList.getItems().get(i);
        String description = null;
        switch (i1) {
            case 0:
                description = tm.getName();
                break;
            case 1:
                description = "\"" + tm.getDuration() + "\"";
                break;
            case 2:
                description = tm.getName() + " " + tm.getDuration();
                break;
        }
        final View view = LayoutInflater.from(c).inflate(R.layout.new_activity, viewGroup, false);
        TextView timerItem = (TextView) view.findViewById(R.id.timerItem);
        timerItem.setText(description);
        viewGroup.addView(view);
        return viewGroup;
    }

    @Override
    public Drawable getBackgroundForPage(int row, int column) {
        return c.getResources().getDrawable(R.drawable.example_bg);
    }



    @Override
    public void destroyItem(ViewGroup viewGroup, int i, int i1, Object o) {

    }

    @Override
    public boolean isViewFromObject(View view, Object o) {
        return false;
    }

}
