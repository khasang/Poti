/*
 * Copyright (C) 2014 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.ni032mas.poti;

import android.animation.ObjectAnimator;
import android.content.Context;
import android.support.wearable.view.CircledImageView;
import android.support.wearable.view.WearableListView;
import android.util.AttributeSet;
import android.widget.FrameLayout;
import android.widget.TextView;

public class SetDurationWearableListItemLayout extends FrameLayout
        implements WearableListView.OnCenterProximityListener {
    private final float mFadedTextAlpha;
    private CircledImageView mCircle;
    private final int mUnselectedCircleColor, mSelectedCircleColor, mPressedCircleColor, mCenterTextColor;
    private float mSmallCircleRadius, mBigCircleRadius, mSmallTextSize, mBigTextSize;
    private TextView mName;
    private ObjectAnimator mScalingDown;
    private ObjectAnimator mScalingUp;
    private ObjectAnimator mIncreaseTextSize;
    private ObjectAnimator mReduceTextSize;
    private boolean mIsInCenter;
    Context context;

    public SetDurationWearableListItemLayout(Context context) {
        this(context, null);
    }

    public SetDurationWearableListItemLayout(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public SetDurationWearableListItemLayout(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        mFadedTextAlpha = getResources().getInteger(R.integer.action_text_faded_alpha) / 100f;

        mUnselectedCircleColor = getResources().getColor(R.color.grey600);
        mSelectedCircleColor = getResources().getColor(R.color.lightblue500);
        mCenterTextColor = getResources().getColor(R.color.grey500);
        mPressedCircleColor = getResources().getColor(R.color.indigo500);
        mSmallCircleRadius = getResources().getDimensionPixelSize(R.dimen.small_circle_radius);
        mBigCircleRadius = getResources().getDimensionPixelSize(R.dimen.big_circle_radius);
        mSmallTextSize = getResources().getDimensionPixelSize(R.dimen.small_font_size);
        mBigTextSize = getResources().getDimensionPixelSize(R.dimen.big_font_size);

        this.context = context;

        setClipChildren(false);
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        mCircle = (CircledImageView) findViewById(R.id.circle);
        mName = (TextView) findViewById(R.id.time_text);

        mScalingDown = ObjectAnimator.ofFloat(mCircle, "circleRadius", mSmallCircleRadius);
        mScalingDown.setDuration(150L);

        mIncreaseTextSize = ObjectAnimator.ofFloat(mName, "textSize", mSmallTextSize);
        mIncreaseTextSize.setDuration(150L);

        mReduceTextSize = ObjectAnimator.ofFloat(mName, "textSize", mBigTextSize);
        mIncreaseTextSize.setDuration(150L);

        mScalingUp = ObjectAnimator.ofFloat(mCircle, "circleRadius", mBigCircleRadius);
        mScalingUp.setDuration(150L);
    }

    @Override
    public void onCenterPosition(boolean animate) {
        mName.setTextSize(mBigTextSize);
        mName.setTextColor(mCenterTextColor);
        mCircle.setCircleRadius(mBigCircleRadius);
        if (animate) {
            mScalingDown.cancel();
            mIncreaseTextSize.cancel();
            if (!mScalingUp.isRunning() && mCircle.getCircleRadius() != mBigCircleRadius) {
                mScalingUp.start();
                mReduceTextSize.start();
            } else {
                mName.setTextSize(mBigTextSize);
                mCircle.setCircleRadius(mBigCircleRadius);
            }
        }
        mName.setAlpha(1f);
        mCircle.setCircleColor(mSelectedCircleColor);
        mIsInCenter = true;
    }

    @Override
    public void onNonCenterPosition(boolean animate) {
        mName.setTextSize(mSmallTextSize);
        mName.setTextColor(mCenterTextColor);
        if (animate) {
            mReduceTextSize.cancel();
            mScalingUp.cancel();
            if (!mScalingDown.isRunning() && mCircle.getCircleRadius() != mSmallCircleRadius) {
                mIncreaseTextSize.start();
                mScalingDown.start();
            }
        } else {
            mName.setTextSize(mSmallTextSize);
            mCircle.setCircleRadius(mSmallCircleRadius);
        }

        mName.setAlpha(mFadedTextAlpha);
        mCircle.setCircleColor(mUnselectedCircleColor);
        mIsInCenter = false;
    }

    @Override
    public void setPressed(boolean pressed) {
        super.setPressed(pressed);
        if (mIsInCenter && pressed) {
            mCircle.setCircleColor(mPressedCircleColor);
        }
        if (mIsInCenter && !pressed) {
            mCircle.setCircleColor(mSelectedCircleColor);
        }
    }
}
