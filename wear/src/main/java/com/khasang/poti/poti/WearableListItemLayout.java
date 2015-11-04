package com.khasang.poti.poti;

import android.animation.ObjectAnimator;
import android.content.Context;
import android.graphics.Color;
import android.support.wearable.view.CircledImageView;
import android.support.wearable.view.WearableListView;
import android.util.AttributeSet;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.jar.Attributes;

/**
 * Created by User on 10.10.2015.
 */
public class WearableListItemLayout extends LinearLayout implements WearableListView.OnCenterProximityListener {

    private CircledImageView mCircle;
    private TextView mName;
    private final float mFadedTextAlpha;
    private final int mUnselectedCircleColor, mSelectedCircleColor, mPressedCircleColor;
    private boolean mIsInCenter;
    private float mBigCircleRadius;
    private float mSmallCircleRadius;
    private ObjectAnimator mScalingDownAnimator;
    private ObjectAnimator mScalingUpAnimator;

    public WearableListItemLayout(Context context){
        this(context, null);

    }
    public WearableListItemLayout(Context context, AttributeSet attr){
        this(context, attr, 0);
    }
    public WearableListItemLayout(Context context, AttributeSet attr, int defStyle){
        super(context, attr, defStyle);

        mFadedTextAlpha = 40 / 100f;
        mUnselectedCircleColor = Color.parseColor("#c1c1c1");
        mSelectedCircleColor = Color.parseColor("#3185ff");
        mPressedCircleColor = Color.parseColor("#2955c5");
        mSmallCircleRadius = getResources().getDimensionPixelSize(R.dimen.small_circle_radius);
        mBigCircleRadius = getResources().getDimensionPixelSize(R.dimen.big_circle_radius);
        // when expanded, the circle may extend beyond the bounds of the view
        setClipChildren(false);
    }

    @Override
    public void onCenterPosition(boolean b) {
        if(b) {
            mScalingDownAnimator.cancel();
            if (!mScalingUpAnimator.isRunning() && mCircle.getCircleRadius() !=
                    mBigCircleRadius) {
                mScalingUpAnimator.start();
            }
        } else {
            mCircle.setCircleRadius(mBigCircleRadius);
        }
        mName.setAlpha(1f);
        mCircle.setCircleColor(mSelectedCircleColor);
        mIsInCenter = true;
    }

    @Override
    public void onNonCenterPosition(boolean b) {
        if(b) {
            mScalingUpAnimator.cancel();
            if(!mScalingDownAnimator.isRunning() && mCircle.getCircleRadius()!=mSmallCircleRadius) {
                mScalingDownAnimator.start();
            }
        } else {
            mCircle.setCircleRadius(mSmallCircleRadius);
        }
        mName.setAlpha(mFadedTextAlpha);
        mCircle.setCircleColor(mUnselectedCircleColor);
        mIsInCenter = false;
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        mCircle = (CircledImageView) findViewById(R.id.circle);
        mName = (TextView) findViewById(R.id.name);
        mScalingUpAnimator = ObjectAnimator.ofFloat(mCircle, "circleRadius", mBigCircleRadius);
        mScalingUpAnimator.setDuration(150L);
        mScalingDownAnimator = ObjectAnimator.ofFloat(mCircle, "circleRadius",
                mSmallCircleRadius);
        mScalingDownAnimator.setDuration(150L);
    }

    @Override
    public void setPressed(boolean pressed) {
        super.setPressed(pressed);
        if(mIsInCenter && pressed) {
            mCircle.setCircleColor(mPressedCircleColor);
        }
        if(mIsInCenter && !pressed) {
            mCircle.setCircleColor(mSelectedCircleColor);
        }
    }
}
