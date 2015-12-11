package com.ni032mas.poti;

import android.animation.ObjectAnimator;
import android.content.Context;
import android.support.wearable.view.WearableListView;
import android.util.AttributeSet;
import android.widget.LinearLayout;
import android.widget.TextView;

public class AdditionalOptionsWearableListViewLayout extends LinearLayout implements WearableListView.OnCenterProximityListener {

    private int onCenterLabelTextColor;
    private int onCenterDescriptionTextColor;
    private int onNonCenterLabelTextColor;
    private int onNonCenterDescriptionTextColor;
    private float labelBigTextSize;
    private float descriptionBigTextSize;
    private float labelSmallTextSize;
    private float descriptionSmallTextSize;
    private ObjectAnimator mIncreaseLabelTextSize;
    private ObjectAnimator mReduceLabelTextSize;
    private ObjectAnimator mIncreaseDescriptionTextSize;
    private ObjectAnimator mReduceDescriptionTextSize;
    TextView tvLabel;
    TextView tvDescription;


    public AdditionalOptionsWearableListViewLayout(Context context) {
        this(context, null);
    }

    public AdditionalOptionsWearableListViewLayout(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public AdditionalOptionsWearableListViewLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.onCenterDescriptionTextColor = getResources().getColor(R.color.black);
        this.onCenterLabelTextColor = getResources().getColor(R.color.grey500);
        this.onNonCenterLabelTextColor = getResources().getColor(R.color.grey300);
        this.onNonCenterDescriptionTextColor = getResources().getColor(R.color.grey600);
        this.labelBigTextSize = getResources().getDimensionPixelSize(R.dimen.big_font_size_label);
        this.descriptionBigTextSize = getResources().getDimensionPixelSize(R.dimen.big_font_size_description);
        this.labelSmallTextSize = getResources().getDimensionPixelSize(R.dimen.small_font_size_label);
        this.descriptionSmallTextSize = getResources().getDimensionPixelSize(R.dimen.small_font_size_description);
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        this.tvLabel = (TextView) findViewById(R.id.tv_label_add_options);
        this.tvDescription = (TextView) findViewById(R.id.tv_description_add_options);
        mIncreaseLabelTextSize = ObjectAnimator.ofFloat(tvLabel, "textSize", labelSmallTextSize);
        mIncreaseLabelTextSize.setDuration(150L);
        mReduceLabelTextSize = ObjectAnimator.ofFloat(tvLabel, "textSize", labelBigTextSize);
        mReduceLabelTextSize.setDuration(150L);
        mIncreaseDescriptionTextSize = ObjectAnimator.ofFloat(tvDescription, "textSize", descriptionSmallTextSize);
        mIncreaseDescriptionTextSize.setDuration(150L);
        mReduceDescriptionTextSize = ObjectAnimator.ofFloat(tvDescription, "textSize", descriptionBigTextSize);
        mReduceDescriptionTextSize.setDuration(150L);
    }

    @Override
    public void onCenterPosition(boolean animate) {
        tvLabel.setTextSize(labelBigTextSize);
        tvLabel.setTextColor(onCenterLabelTextColor);
        tvDescription.setTextSize(descriptionBigTextSize);
        tvDescription.setTextColor(onCenterDescriptionTextColor);
        if (animate) {
            mIncreaseLabelTextSize.cancel();
            mIncreaseDescriptionTextSize.cancel();
            if (!mReduceLabelTextSize.isRunning() && tvLabel.getTextSize() != labelBigTextSize)
            mReduceLabelTextSize.start();
            //mReduceDescriptionTextSize.start();
        }
    }

    @Override
    public void onNonCenterPosition(boolean animate) {
        tvLabel.setTextSize(labelSmallTextSize);
        tvLabel.setTextColor(onNonCenterLabelTextColor);
        tvDescription.setTextSize(descriptionSmallTextSize);
        tvDescription.setTextColor(onNonCenterDescriptionTextColor);
        if (animate) {
            mReduceLabelTextSize.cancel();
            mReduceDescriptionTextSize.cancel();
            if (!mIncreaseLabelTextSize.isRunning() && tvLabel.getTextSize() != labelSmallTextSize) {
                mIncreaseLabelTextSize.start();

            }
            //mIncreaseDescriptionTextSize.start();
        } else {
            tvLabel.setTextSize(labelSmallTextSize);
        }
    }
}
