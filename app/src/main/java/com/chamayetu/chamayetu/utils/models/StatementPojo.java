package com.chamayetu.chamayetu.utils.models;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * ChamaYetu
 * com.chamayetu.chamayetu.utils.models
 * Created by lusinabrian on 29/09/16.
 * Description: Statement Pojo for the statements node
 */

public class StatementPojo implements Parcelable {
    private int dateFrom, dateTo,nextMeetingTime,agendaTime;
    private float totalAmount;

    protected StatementPojo(Parcel in) {
    }

    public static final Creator<StatementPojo> CREATOR = new Creator<StatementPojo>() {
        @Override
        public StatementPojo createFromParcel(Parcel in) {
            return new StatementPojo(in);
        }

        @Override
        public StatementPojo[] newArray(int size) {
            return new StatementPojo[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
    }
}
