package com.chamayetu.chamayetu.utils;

import android.support.v7.widget.RecyclerView;
import android.view.MotionEvent;

/**
 * ChamaYetu
 * com.chamayetu.chamayetu.utils
 * Created by lusinabrian on 02/11/16.
 * Description:
 */

public class RecyclerItemClickListener implements RecyclerView.OnItemTouchListener {

    @Override
    public boolean onInterceptTouchEvent(RecyclerView rv, MotionEvent e) {
        return false;
    }

    @Override
    public void onTouchEvent(RecyclerView rv, MotionEvent e) {

    }

    @Override
    public void onRequestDisallowInterceptTouchEvent(boolean disallowIntercept) {

    }
}
