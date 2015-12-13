package com.ni032mas.poti;

import android.animation.ObjectAnimator;
import android.content.Context;
import android.support.wearable.view.CircledImageView;
import android.support.wearable.view.WearableListView;
import android.util.AttributeSet;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;

public class ColorWearableListViewLayout extends LinearLayout implements WearableListView.OnCenterProximityListener {

    private final float mFadedTextAlpha;
    private CircledImageView mCircle;
    private final int mUnselectedCircleColor;
    private final int mSelectedCircleColor;
    private final int mPressedCircleColor;
    private final int mCenterTextColor;
    private float mSmallCircleRadius;
    private float mBigCircleRadius;
    private float mSmallTextSize;
    private float mBigTextSize;
    private TextView mName;
    private ObjectAnimator mScalingDown;
    private ObjectAnimator mScalingUp;
    private ObjectAnimator mIncreaseTextSize;
    private ObjectAnimator mReduceTextSize;
    private boolean mIsInCenter;
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
        mFadedTextAlpha = getResources().getInteger(R.integer.action_text_faded_alpha) / 100f;
        mUnselectedCircleColor = getResources().getColor(R.color.grey600);
        mSelectedCircleColor = getResources().getColor(R.color.light_blue500);
        mCenterTextColor = getResources().getColor(R.color.white);
        mPressedCircleColor = getResources().getColor(R.color.indigo500);
        mSmallCircleRadius = getResources().getDimensionPixelSize(R.dimen.small_circle_radius);
        mBigCircleRadius = getResources().getDimensionPixelSize(R.dimen.big_circle_radius);
        mSmallTextSize = getResources().getDimensionPixelSize(R.dimen.small_font_size);
        mBigTextSize = getResources().getDimensionPixelSize(R.dimen.big_font_size);
        setClipChildren(false);
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
//        mCircle = (CircledImageView) findViewById(R.id.circle);
        mName = (TextView) findViewById(R.id.tv_color);

//        mScalingDown = ObjectAnimator.ofFloat(mCircle, "circleRadius", mSmallCircleRadius);
//        mScalingDown.setDuration(150L);

        mIncreaseTextSize = ObjectAnimator.ofFloat(mName, "textSize", mSmallTextSize);
        mIncreaseTextSize.setDuration(150L);

        mReduceTextSize = ObjectAnimator.ofFloat(mName, "textSize", mBigTextSize);
        mReduceTextSize.setDuration(150L);

//        mScalingUp = ObjectAnimator.ofFloat(mCircle, "circleRadius", mBigCircleRadius);
//        mScalingUp.setDuration(150L);
    }

    @Override
    public void onCenterPosition(boolean animate) {
        mName.setTextSize(mBigTextSize);
        mName.setTextColor(colors.get(position).color);
//        mCircle.setCircleRadius(mBigCircleRadius);
//        if (animate) {
//            mScalingDown.cancel();
//            mIncreaseTextSize.cancel();
//            if (!mScalingUp.isRunning() && mCircle.getCircleRadius() != mBigCircleRadius) {
//                mScalingUp.start();
//                mReduceTextSize.start();
//            } else {
//                mName.setTextSize(mBigTextSize);
//                mCircle.setCircleRadius(mBigCircleRadius);
//            }
//        }
//        mName.setAlpha(1f);
//        mCircle.setCircleColor(mSelectedCircleColor);
//        mIsInCenter = true;
    }

    @Override
    public void onNonCenterPosition(boolean animate) {
        mName.setTextSize(mSmallTextSize);
        mName.setTextColor(colors.get(position).color);
//        if (animate) {
//            mReduceTextSize.cancel();
//            mScalingUp.cancel();
//            if (!mScalingDown.isRunning() && mCircle.getCircleRadius() != mSmallCircleRadius) {
//                mIncreaseTextSize.start();
//                mScalingDown.start();
//            }
//        } else {
//            mName.setTextSize(mSmallTextSize);
//            mCircle.setCircleRadius(mSmallCircleRadius);
//        }
//        mName.setAlpha(mFadedTextAlpha);
//        mCircle.setCircleColor(mUnselectedCircleColor);
//        mIsInCenter = false;
    }
}
