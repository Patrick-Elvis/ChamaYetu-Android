package com.chamayetu.chamayetu.models;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * ChamaYetu
 * com.chamayetu.chamayetu.models
 * Created by lusinabrian on 29/09/16.
 * Description: Statement Pojo for the statements node
 */

public class StatementPojo implements Parcelable {

    private String dateFrom, dateTo, title;
    private long totalAmount, outgoings, fundsRecieved;

    public StatementPojo(){}

    public StatementPojo(String dateFrom, String dateTo, String title, long totalAmount, long outgoings,long fundsRecieved) {
        this.dateFrom = dateFrom;
        this.dateTo = dateTo;
        this.title = title;
        this.totalAmount = totalAmount;
        this.outgoings = outgoings;
        this.fundsRecieved = fundsRecieved;
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

    public long getOutgoings() {
        return outgoings;
    }

    public void setOutgoings(long outgoings) {
        this.outgoings = outgoings;
    }

    public long getFundsRecieved() {
        return fundsRecieved;
    }

    public void setFundsRecieved(long fundsRecieved) {
        this.fundsRecieved = fundsRecieved;
    }

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
        dest.writeLong(this.outgoings);
        dest.writeLong(this.fundsRecieved);
    }

    protected StatementPojo(Parcel in) {
        this.dateFrom = in.readString();
        this.dateTo = in.readString();
        this.title = in.readString();
        this.totalAmount = in.readLong();
        this.outgoings = in.readLong();
        this.fundsRecieved = in.readLong();
    }

    public static final Creator<StatementPojo> CREATOR = new Creator<StatementPojo>() {
        @Override
        public StatementPojo createFromParcel(Parcel source) {
            return new StatementPojo(source);
        }

        @Override
        public StatementPojo[] newArray(int size) {
            return new StatementPojo[size];
        }
    };

    @Override
    public String toString() {
        return "StatementPojo{" +
                "dateFrom='" + dateFrom + '\'' +
                ", dateTo='" + dateTo + '\'' +
                ", title='" + title + '\'' +
                ", totalAmount=" + totalAmount +
                ", outgoings=" + outgoings +
                ", fundsRecieved=" + fundsRecieved +
                '}';
    }
}
