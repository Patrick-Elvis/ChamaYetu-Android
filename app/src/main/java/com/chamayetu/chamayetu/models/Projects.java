package com.chamayetu.chamayetu.models;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * ChamaYetu
 * com.chamayetu.chamayetu.models
 * Created by lusinabrian on 14/10/16.
 * Description:
 */

public class Projects implements Parcelable {
    /*  "projDate" : "October 1st 2016",
          "projName" : "Start a coffee shop"*/
    private String projDate,projName;

    public Projects(){}

    public Projects(String projDate, String projName) {
        this.projDate = projDate;
        this.projName = projName;
    }

    public String getProjDate() {
        return projDate;
    }

    public void setProjDate(String projDate) {
        this.projDate = projDate;
    }

    public String getProjName() {
        return projName;
    }

    public void setProjName(String projName) {
        this.projName = projName;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.projDate);
        dest.writeString(this.projName);
    }

    protected Projects(Parcel in) {
        this.projDate = in.readString();
        this.projName = in.readString();
    }

    public static final Creator<Projects> CREATOR = new Creator<Projects>() {
        @Override
        public Projects createFromParcel(Parcel source) {
            return new Projects(source);
        }

        @Override
        public Projects[] newArray(int size) {
            return new Projects[size];
        }
    };
}
