package com.chamayetu.chamayetu.models;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * ChamaYetu
 * com.chamayetu.chamayetu.models
 * Created by lusinabrian on 13/10/16.
 * Description: Model class for the Activity Node
 */

public class ActivityModel implements Parcelable {
    private String activityType, person, date;
    private long amount;

    public ActivityModel(){}

    public ActivityModel(String activityType, String person, String date, long amount) {
        this.activityType = activityType;
        this.person = person;
        this.date = date;
        this.amount = amount;
    }

    @Override
    public String toString() {
        return "ActivityModel{" +
                "activityType='" + activityType + '\'' +
                ", person='" + person + '\'' +
                ", date='" + date + '\'' +
                ", amount=" + amount +
                '}';
    }

    public String getActivityType() {
        return activityType;
    }

    public void setActivityType(String activityType) {
        this.activityType = activityType;
    }

    public String getPerson() {
        return person;
    }

    public void setPerson(String person) {
        this.person = person;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
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
        dest.writeString(this.activityType);
        dest.writeString(this.person);
        dest.writeString(this.date);
        dest.writeLong(this.amount);
    }

    private ActivityModel(Parcel in) {
        this.activityType = in.readString();
        this.person = in.readString();
        this.date = in.readString();
        this.amount = in.readLong();
    }

    public static final Creator<ActivityModel> CREATOR = new Creator<ActivityModel>() {
        @Override
        public ActivityModel createFromParcel(Parcel source) {
            return new ActivityModel(source);
        }

        @Override
        public ActivityModel[] newArray(int size) {
            return new ActivityModel[size];
        }
    };
}
