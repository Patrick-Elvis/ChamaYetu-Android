package com.chamayetu.chamayetu.calender;

import android.support.v4.app.Fragment;

/**
 * ChamaYetu
 * com.chamayetu.chamayetu
 * Created by lusinabrian on 25/10/16.
 * Description:
 */

public class CalenderFrag extends Fragment{

    public CalenderFrag() {}

    public static Fragment newInstance(){
        CalenderFrag calenderFrag = new CalenderFrag();
        calenderFrag.setRetainInstance(true);
        return calenderFrag;
    }


}
