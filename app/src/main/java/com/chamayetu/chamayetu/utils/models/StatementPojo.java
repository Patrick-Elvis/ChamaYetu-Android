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
    private String dateFrom, dateTo, title;
    private long totalAmount;

    public StatementPojo(){}

    public StatementPojo(String dateFrom, String dateTo, String title, long totalAmount) {
        this.dateFrom = dateFrom;
        this.dateTo = dateTo;
        this.title = title;
        this.totalAmount = totalAmount;
    }

    public String getDateFrom() {
        return dateFrom;
    }

    public void setDateFrom(String dateFrom) {
        this.dateFrom = dateFrom;
    }

    public String getDateTo() {
        return dateTo;
    }

    public void setDateTo(String dateTo) {
        this.dateTo = dateTo;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public long getTotalAmount() {
        return totalAmount;
    }

    public void setTotalAmount(long totalAmount) {
        this.totalAmount = totalAmount;
    }

    protected StatementPojo(Parcel source) {
        this.dateFrom = source.readString();
        this.dateTo = source.readString();
        this.title =source.readString();
        this.totalAmount = source.readLong();
    }

    public static Parcelable.Creator<StatementPojo> CREATOR = new Parcelable.Creator<StatementPojo>() {
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
        dest.writeString(this.dateFrom);
        dest.writeString(this.dateTo);
        dest.writeString(this.title);
        dest.writeLong(this.totalAmount);
    }
}
