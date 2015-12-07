package com.ni032mas.poti;

import android.content.Context;
import android.support.wearable.view.WearableListView;
import android.util.AttributeSet;

/**
 * Created by aleksandr.marmyshev on 03.12.2015.
 */
public class TimersWearableListView extends WearableListView {
    boolean isScroll;
    public TimersWearableListView(Context context) {
        super(context);
    }

    public TimersWearableListView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public TimersWearableListView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    public void scrollToPosition(int position) {
        isScroll = false;
        super.scrollToPosition(position);
        isScroll = true;
    }
}
