package com.chamayetu.chamayetu.graph;

import com.github.mikephil.charting.components.AxisBase;
import com.github.mikephil.charting.formatter.AxisValueFormatter;

import java.text.DecimalFormat;

/**
 * ChamaYetu
 * com.chamayetu.chamayetu.graph
 * Created by lusinabrian on 13/10/16.
 * Description: formats the axis values of the bar chart
 */

public class MyAxisValueFormatter implements AxisValueFormatter {
    private DecimalFormat mFormat;

    public MyAxisValueFormatter() {
        mFormat = new DecimalFormat("###,###,###,##0.0");
    }

    @Override
    public String getFormattedValue(float value, AxisBase axis) {
        return mFormat.format(value) + " KSH";
    }

    @Override
    public int getDecimalDigits() {
        return 1;
    }
}
