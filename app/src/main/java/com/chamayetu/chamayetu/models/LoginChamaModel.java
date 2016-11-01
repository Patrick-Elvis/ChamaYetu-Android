package com.chamayetu.chamayetu.models;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.firebase.database.DataSnapshot;

import java.util.HashMap;
import java.util.Map;

/**
 * ChamaYetu
 * com.chamayetu.chamayetu.models
 * Created by lusinabrian on 28/10/16.
 * Description: ChamaGroups model for the LoginChama
 */

public class LoginChamaModel implements Parcelable {
    private DataSnapshot chamaGroups;

    public LoginChamaModel(){}

    public LoginChamaModel(DataSnapshot chamaGroups) {
        this.chamaGroups = chamaGroups;
    }

    public DataSnapshot getChamaGroups() {
        return chamaGroups;
    }

    public void setChamaGroups(DataSnapshot chamaGroups) {
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
        dest.writeLong(this.chamaGroups.getChildrenCount());
        for(DataSnapshot d: this.chamaGroups.getChildren()){
            dest.writeString(String.valueOf(d.getValue()));
            dest.writeParcelable((Parcelable) d.getValue(), flags);
        }
    }

    protected LoginChamaModel(Parcel in) {
        long chamaGroupsSize = in.readLong();
        /*this.chamaGroups = new DataSnapshot();
        for (int i = 0; i < chamaGroupsSize; i++) {
            String key = in.readString();
            Object value = in.readParcelable(Object.class.getClassLoader());
            this.chamaGroups.put(key, value);
        }*/
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
