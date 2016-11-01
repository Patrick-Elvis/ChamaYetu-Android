package com.chamayetu.chamayetu.models;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * ChamaYetu
 * com.chamayetu.chamayetu.models
 * Created by lusinabrian on 28/10/16.
 * Description: ChamaGroups model for the LoginChama
 */

public class LoginChamaModel implements Parcelable {
    private String chamaGroups;

    public LoginChamaModel(){}


    public LoginChamaModel(String chamaGroups) {
        this.chamaGroups = chamaGroups;
    }

    public String getChamaGroups() {
        return chamaGroups;
    }

    public void setChamaGroups( String chamaGroups) {
        this.chamaGroups = chamaGroups;
    }

    @Override
    public String toString() {
        return "LoginChamaModel{" +
                "chamaGroups=" + chamaGroups +
                '}';
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.chamaGroups);
    }

    protected LoginChamaModel(Parcel in) {
        this.chamaGroups = in.readString();
    }

    public static final Creator<LoginChamaModel> CREATOR = new Creator<LoginChamaModel>() {
        @Override
        public LoginChamaModel createFromParcel(Parcel source) {
            return new LoginChamaModel(source);
        }

        @Override
        public LoginChamaModel[] newArray(int size) {
            return new LoginChamaModel[size];
        }
    };
}
