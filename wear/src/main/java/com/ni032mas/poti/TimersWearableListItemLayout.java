package com.ni032mas.poti;

import android.animation.ObjectAnimator;
import android.content.Context;
import android.support.wearable.view.CircledImageView;
import android.support.wearable.view.WearableListView;
import android.util.AttributeSet;
import android.widget.LinearLayout;
import android.widget.TextView;

public class TimersWearableListItemLayout extends LinearLayout
        implements WearableListView.OnCenterProximityListener {
    private final float mFadedTextAlpha;
    private CircledImageView ivStart;
    private CircledImageView ivSetting;
    private final int mUnselectedCircleColor, mSelectedCircleColor, mPressedCircleColor, mCenterTextColor;
    private float mSmallCircleRadius, mBigCircleRadius, mSmallTextSize, mBigTextSize;
    private int mBigWidth, mSmallWidth;
    private TextView mName;
    private ObjectAnimator mScalingDownStart;
    private ObjectAnimator mScalingDownSetting;
    private ObjectAnimator mScalingUpStart;
    private ObjectAnimator mScalingUpSetting;
    private ObjectAnimator mIncreaseTextSize;
    private ObjectAnimator mTextWidthUp;
    private ObjectAnimator mTextWidthDown;
    private ObjectAnimator mReduceTextSize;
    private boolean mIsInCenter;
    Context context;

    public TimersWearableListItemLayout(Context context) {
        this(context, null);
    }

    public TimersWearableListItemLayout(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public TimersWearableListItemLayout(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        mFadedTextAlpha = getResources().getInteger(R.integer.action_text_faded_alpha) / 100f;

        mUnselectedCircleColor = getResources().getColor(R.color.grey600);
        mSelectedCircleColor = getResources().getColor(R.color.lightblue500);
        mCenterTextColor = getResources().getColor(R.color.grey500);
        mPressedCircleColor = getResources().getColor(R.color.indigo500);
        mSmallCircleRadius = getResources().getDimensionPixelSize(R.dimen.small_circle_radius_timers);
        mBigCircleRadius = getResources().getDimensionPixelSize(R.dimen.big_circle_radius_timers);
        mSmallTextSize = getResources().getDimensionPixelSize(R.dimen.small_font_size);
        mBigTextSize = getResources().getDimensionPixelSize(R.dimen.big_font_size);
        mBigWidth = getResources().getDimensionPixelSize(R.dimen.big_width);
        mSmallWidth = getResources().getDimensionPixelSize(R.dimen.small_width);

        this.context = context;

        setClipChildren(false);
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        ivStart = (CircledImageView) findViewById(R.id.btn_timerslist_start);
        ivSetting = (CircledImageView) findViewById(R.id.btn_timerslist_setting);
        mName = (TextView) findViewById(R.id.timer_name);

        mScalingDownStart = ObjectAnimator.ofFloat(ivStart, "circleRadius", mSmallCircleRadius);
        mScalingDownSetting = ObjectAnimator.ofFloat(ivSetting, "circleRadius", mSmallCircleRadius);
        mScalingDownStart.setDuration(150L);
        mScalingDownSetting.setDuration(150L);

        mIncreaseTextSize = ObjectAnimator.ofFloat(mName, "textSize", mSmallTextSize);
        mIncreaseTextSize.setDuration(150L);

        mTextWidthUp = ObjectAnimator.ofInt(mName, "width", mBigWidth);
        mTextWidthUp.setDuration(150L);
        mTextWidthDown = ObjectAnimator.ofInt(mName, "width", mSmallWidth);
        mTextWidthDown.setDuration(150L);

        mReduceTextSize = ObjectAnimator.ofFloat(mName, "textSize", mBigTextSize);
        mIncreaseTextSize.setDuration(150L);

        mScalingUpStart = ObjectAnimator.ofFloat(ivStart, "circleRadius", mBigCircleRadius);
        mScalingUpSetting = ObjectAnimator.ofFloat(ivSetting, "circleRadius", mBigCircleRadius);
        mScalingUpStart.setDuration(150L);
        mScalingUpSetting.setDuration(150L);
    }

    @Override
    public void onCenterPosition(boolean animate) {
        //mName.setTextSize(mSmallTextSize);
        mName.setTextColor(mCenterTextColor);
        if (animate) {
            mScalingDownStart.cancel();
            mScalingDownSetting.cancel();
            mIncreaseTextSize.cancel();
            mTextWidthUp.cancel();
            if (!mScalingUpStart.isRunning()
                    && !mScalingDownSetting.isRunning()
                    && !mTextWidthUp.isRunning()
                    && ivStart.getCircleRadius() != mBigCircleRadius
                    && ivSetting.getCircleRadius() != mBigCircleRadius
                    && mName.getWidth() != mBigWidth) {
                mTextWidthDown.start();
                mScalingUpStart.start();
                mScalingUpSetting.start();
                mTextWidthUp.start();
                //mReduceTextSize.start();
            } else {
                //mName.setTextSize(mSmallTextSize);
                mName.setWidth(mSmallWidth);
                ivStart.setCircleRadius(mBigCircleRadius);
                ivSetting.setCircleRadius(mBigCircleRadius);
            }
        }
        mName.setAlpha(1f);
        ivStart.setCircleColor(mSelectedCircleColor);
        ivSetting.setCircleColor(mSelectedCircleColor);
        mIsInCenter = true;
    }

    @Override
    public void onNonCenterPosition(boolean animate) {
        //mName.setTextSize(mBigTextSize);
        mName.setTextColor(mCenterTextColor);
        if (animate) {
            mReduceTextSize.cancel();
            mScalingUpStart.cancel();
            mScalingUpSetting.cancel();
            mTextWidthDown.cancel();
            if (!mScalingDownStart.isRunning()
                    && !mScalingDownSetting.isRunning()
                    && ivStart.getCircleRadius() != mSmallCircleRadius
                    && ivSetting.getCircleRadius() != mSmallCircleRadius) {
                //mIncreaseTextSize.start();
                mScalingDownStart.start();
                mScalingDownSetting.start();
                mTextWidthUp.start();
            }
        } else {
            //mName.setTextSize(mBigTextSize);
            mName.setWidth(mBigWidth);
            ivStart.setCircleRadius(mSmallCircleRadius);
            ivSetting.setCircleRadius(mSmallCircleRadius);
        }

        mName.setAlpha(mFadedTextAlpha);
        ivStart.setCircleColor(mUnselectedCircleColor);
        ivSetting.setCircleColor(mUnselectedCircleColor);
        mIsInCenter = false;
    }

    @Override
    public void setPressed(boolean pressed) {
        super.setPressed(pressed);
        if (mIsInCenter && pressed) {
            ivStart.setCircleColor(mPressedCircleColor);
            ivSetting.setCircleColor(mPressedCircleColor);
        }
        if (mIsInCenter && !pressed) {
            ivStart.setCircleColor(mSelectedCircleColor);
            ivSetting.setCircleColor(mSelectedCircleColor);
        }
    }
}
