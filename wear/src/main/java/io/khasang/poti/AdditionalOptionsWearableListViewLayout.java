package io.khasang.poti;

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
    boolean isCenterPosition = true;
    int position;
    WearableTimer timer;


    public AdditionalOptionsWearableListViewLayout(Context context) {
        this(context, null);
    }

    public AdditionalOptionsWearableListViewLayout(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public AdditionalOptionsWearableListViewLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.onCenterDescriptionTextColor = getResources().getColor(R.color.black);
        this.onCenterLabelTextColor = getResources().getColor(R.color.light_blue500);
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
        mIncreaseLabelTextSize.setDuration(300L);
        mReduceLabelTextSize = ObjectAnimator.ofFloat(tvLabel, "textSize", labelBigTextSize);
        mReduceLabelTextSize.setDuration(300L);
        mIncreaseDescriptionTextSize = ObjectAnimator.ofFloat(tvDescription, "textSize", descriptionSmallTextSize);
        mIncreaseDescriptionTextSize.setDuration(300L);
        mReduceDescriptionTextSize = ObjectAnimator.ofFloat(tvDescription, "textSize", descriptionBigTextSize);
        mReduceDescriptionTextSize.setDuration(300L);
    }

    @Override
    public void onCenterPosition(boolean animate) {
        if (position == AdditionalOptionsFragment.COLOR_POSITION) {
            tvDescription.setTextColor(timer.getColor().color);
        } else {
            tvDescription.setTextColor(onCenterDescriptionTextColor);
        }
        tvLabel.setTextSize(labelBigTextSize);
        tvLabel.setTextColor(onCenterLabelTextColor);
        tvDescription.setTextSize(labelBigTextSize);
    }

    @Override
    public void onNonCenterPosition(boolean animate) {
        tvLabel.setTextSize(labelSmallTextSize);
        tvLabel.setTextColor(onNonCenterLabelTextColor);
        tvDescription.setTextSize(labelSmallTextSize);
        tvDescription.setTextColor(onNonCenterDescriptionTextColor);
    }
}
