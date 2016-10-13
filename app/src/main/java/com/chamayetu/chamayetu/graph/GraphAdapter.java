package com.chamayetu.chamayetu.graph;

import com.robinhood.spark.SparkAdapter;

import java.util.Random;

/**
 * ChamaYetu
 * com.chamayetu.chamayetu.graph
 * Created by lusinabrian on 13/10/16.
 * Description: Graph adapter to display data over time for the data
 * Displays a line chart
 */

public class GraphAdapter extends SparkAdapter {
    private final float[] yData;
    private final Random random;

    /**Constructor to randomize data, for now*/
    public GraphAdapter(){
        random = new Random();
        yData = new float[50];
        randomize();
    }

    public void randomize() {
        for (int i = 0, count = yData.length; i < count; i++) {
            yData[i] = random.nextFloat();
        }
        notifyDataSetChanged();
    }

    @Override
    public int getCount() {
        return yData.length;
    }

    @Override
    public Object getItem(int index) {
        return yData[index];
    }

    @Override
    public float getY(int index) {
        return yData[index];
    }
}
