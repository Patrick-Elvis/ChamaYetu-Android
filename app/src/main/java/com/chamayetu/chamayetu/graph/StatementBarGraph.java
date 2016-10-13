package com.chamayetu.chamayetu.graph;

import android.content.Context;

import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.highlight.Highlight;
import com.github.mikephil.charting.listener.OnChartValueSelectedListener;

/**
 * ChamaYetu
 * com.chamayetu.chamayetu.graph
 * Created by lusinabrian on 13/10/16.
 * Description:
 */

public class StatementBarGraph implements OnChartValueSelectedListener{
    private BarChart mBarChart;
    private Context context;

    public StatementBarGraph(){}

    public StatementBarGraph(BarChart mBarChart, Context context){
        this.mBarChart = mBarChart;
        this.context = context;
    }

    public void initGraph(){

        mBarChart.setOnChartValueSelectedListener(this);

        mBarChart.setDrawBarShadow(false);
        mBarChart.setDrawValueAboveBar(true);

        // if more than 60 entries are displayed in the chart, no values will be
        // drawn
        mBarChart.setMaxVisibleValueCount(60);

        // scaling can now only be done on x- and y-axis separately
        mBarChart.setPinchZoom(false);

        mBarChart.setDrawGridBackground(false);
        // mBarChart.setDrawYLabels(false);
    }

    @Override
    public void onValueSelected(Entry e, Highlight h) {

    }

    @Override
    public void onNothingSelected() {

    }
}
