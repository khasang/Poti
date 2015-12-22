package io.ni032mas.poti;

import android.animation.ObjectAnimator;
import android.content.Context;
import android.support.wearable.view.CircledImageView;
import android.support.wearable.view.WearableListView;
import android.util.AttributeSet;
import android.widget.FrameLayout;
import android.widget.TextView;

public class TimersWearableListItemLayout extends FrameLayout
        implements WearableListView.OnCenterProximityListener {
    private final float mFadedTextAlpha;
    private CircledImageView ivStart;
    private CircledImageView ivSetting;
    private final int mUnselectedCircleColor;
    private final int mSelectedCircleColor;
    private final int mCenterTextColor;
    private int mBigWidth;
    private float mSmallCircleRadius;
    private float mBigCircleRadius;
    private TextView mName;
    private ObjectAnimator mScalingDownStart;
    private ObjectAnimator mScalingDownSetting;
    private ObjectAnimator mScalingUpStart;
    private ObjectAnimator mScalingUpSetting;
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
        mSelectedCircleColor = getResources().getColor(R.color.light_blue500);
        mCenterTextColor = getResources().getColor(R.color.grey500);
        mSmallCircleRadius = getResources().getDimensionPixelSize(R.dimen.small_circle_radius_timers);
        mBigCircleRadius = getResources().getDimensionPixelSize(R.dimen.big_circle_radius_timers);
        mBigWidth = getResources().getDimensionPixelSize(R.dimen.big_width);
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
        mScalingUpStart = ObjectAnimator.ofFloat(ivStart, "circleRadius", mBigCircleRadius);
        mScalingUpSetting = ObjectAnimator.ofFloat(ivSetting, "circleRadius", mBigCircleRadius);
        mScalingUpStart.setDuration(150L);
        mScalingUpSetting.setDuration(150L);
    }

    @Override
    public void onCenterPosition(boolean animate) {
        mName.setTextColor(mCenterTextColor);
        if (animate) {
            mScalingDownStart.cancel();
            mScalingDownSetting.cancel();
            if (!mScalingUpStart.isRunning()
                    && !mScalingDownSetting.isRunning()
                    && ivStart.getCircleRadius() != mBigCircleRadius
                    && ivSetting.getCircleRadius() != mBigCircleRadius
                    && mName.getWidth() != mBigWidth) {
                mScalingUpStart.start();
                mScalingUpSetting.start();
            } else {
                ivStart.setCircleRadius(mBigCircleRadius);
                ivSetting.setCircleRadius(mBigCircleRadius);
            }
        }
        mName.setAlpha(1f);
        ivStart.setCircleColor(mSelectedCircleColor);
        ivSetting.setCircleColor(mSelectedCircleColor);
    }

    @Override
    public void onNonCenterPosition(boolean animate) {
        mName.setTextColor(mCenterTextColor);
        if (animate) {
            mScalingUpStart.cancel();
            mScalingUpSetting.cancel();
            if (!mScalingDownStart.isRunning()
                    && !mScalingDownSetting.isRunning()
                    && ivStart.getCircleRadius() != mSmallCircleRadius
                    && ivSetting.getCircleRadius() != mSmallCircleRadius) {
                mScalingDownStart.start();
                mScalingDownSetting.start();
            }
        } else {
            ivStart.setCircleRadius(mSmallCircleRadius);
            ivSetting.setCircleRadius(mSmallCircleRadius);
        }
        mName.setAlpha(mFadedTextAlpha);
        ivStart.setCircleColor(mUnselectedCircleColor);
        ivSetting.setCircleColor(mUnselectedCircleColor);
    }
}
