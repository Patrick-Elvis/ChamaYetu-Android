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
    /*
      "outgoings":5000,
      "fundsRecieved":15000,*/
    private String dateFrom, dateTo, title;
    private long totalAmount, outgoings, fundsReceived;

    public StatementPojo(){}

    public StatementPojo(String dateFrom, String dateTo, String title, long totalAmount, long outgoings,long fundsReceived) {
        this.dateFrom = dateFrom;
        this.dateTo = dateTo;
        this.title = title;
        this.totalAmount = totalAmount;
        this.outgoings = outgoings;
        this.fundsReceived = fundsReceived;
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

    public long getFundsReceived() {
        return fundsReceived;
    }

    public void setFundsReceived(long fundsReceived) {
        this.fundsReceived = fundsReceived;
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
        dest.writeLong(this.fundsReceived);
    }

    protected StatementPojo(Parcel in) {
        this.dateFrom = in.readString();
        this.dateTo = in.readString();
        this.title = in.readString();
        this.totalAmount = in.readLong();
        this.outgoings = in.readLong();
        this.fundsReceived = in.readLong();
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
                ", fundsReceived=" + fundsReceived +
                '}';
    }
}
