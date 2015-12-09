package com.ni032mas.poti;

import android.content.Context;
import android.content.Intent;

import java.util.HashMap;

/**
 * Created by ni032_000 on 09.12.2015.
 */
public class TimerColor {
    HashMap<Integer, String> colors;

    public TimerColor(Context context) {
        this.colors = new HashMap<>();
        colors.put(context.getResources().getColor(R.color.light_blue500), "Light blue");
        colors.put(context.getResources().getColor(R.color.yellow500), "Yellow");
        colors.put(context.getResources().getColor(R.color.green500), "Green");
        colors.put(context.getResources().getColor(R.color.purple500), "Purple");
        colors.put(context.getResources().getColor(R.color.red500), "Red");
        colors.put(context.getResources().getColor(R.color.blue500), "Blue");
    }
    public String getColorName(Integer color) {
        return colors.get(color);
    }
}
