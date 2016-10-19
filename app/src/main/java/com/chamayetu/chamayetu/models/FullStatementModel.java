package com.chamayetu.chamayetu.models;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * ChamaYetu
 * com.chamayetu.chamayetu.models
 * Created by lusinabrian on 19/10/16.
 * Description: Model class for the full statement for each chama
 */

public class FullStatementModel implements Parcelable {
    private String date, transactor,details;
    private long amount;

    public FullStatementModel(){}

    public FullStatementModel(String date, String transactor, String details, long amount) {
        this.date = date;
        this.transactor = transactor;
        this.details = details;
        this.amount = amount;
    }

    @Override
    public String toString() {
        return "FullStatementModel{" +
                "date='" + date + '\'' +
                ", transactor='" + transactor + '\'' +
                ", details='" + details + '\'' +
                ", amount=" + amount +
                '}';
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getTransactor() {
        return transactor;
    }

    public void setTransactor(String transactor) {
        this.transactor = transactor;
    }

    public String getDetails() {
        return details;
    }

    public void setDetails(String details) {
        this.details = details;
    }

    public long getAmount() {
        return amount;
    }

    public void setAmount(long amount) {
        this.amount = amount;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.date);
        dest.writeString(this.transactor);
        dest.writeString(this.details);
        dest.writeLong(this.amount);
    }

    private FullStatementModel(Parcel in) {
        this.date = in.readString();
        this.transactor = in.readString();
        this.details = in.readString();
        this.amount = in.readLong();
    }

    public static final Creator<FullStatementModel> CREATOR = new Creator<FullStatementModel>() {
        @Override
        public FullStatementModel createFromParcel(Parcel source) {
            return new FullStatementModel(source);
        }

        @Override
        public FullStatementModel[] newArray(int size) {
            return new FullStatementModel[size];
        }
    };
}
