package io.khasang.poti;

import android.content.Context;
import android.support.wearable.view.WearableListView;
import android.util.AttributeSet;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;

public class ColorWearableListViewLayout extends LinearLayout implements WearableListView.OnCenterProximityListener {
    private float mSmallTextSize;
    private float mBigTextSize;
    private TextView mName;
    ArrayList<ColorTimer> colors;
    int position;

    public ColorWearableListViewLayout(Context context) {
        this(context, null);
    }

    public ColorWearableListViewLayout(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public ColorWearableListViewLayout(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        mSmallTextSize = getResources().getDimensionPixelSize(R.dimen.small_font_size);
        mBigTextSize = getResources().getDimensionPixelSize(R.dimen.big_font_size);
        setClipChildren(false);
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        mName = (TextView) findViewById(R.id.tv_color);
    }

    @Override
    public void onCenterPosition(boolean animate) {
        mName.setTextSize(mBigTextSize);
        mName.setTextColor(colors.get(position).color);
    }

    @Override
    public void onNonCenterPosition(boolean animate) {
        mName.setTextSize(mSmallTextSize);
        mName.setTextColor(colors.get(position).color);
    }
}
