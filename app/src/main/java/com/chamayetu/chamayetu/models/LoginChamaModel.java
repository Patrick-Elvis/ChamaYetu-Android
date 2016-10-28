package com.chamayetu.chamayetu.models;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.HashMap;
import java.util.Map;

/**
 * ChamaYetu
 * com.chamayetu.chamayetu.models
 * Created by lusinabrian on 28/10/16.
 * Description: ChamaGroups model for the LoginChama
 */

public class LoginChamaModel implements Parcelable {
    private Map<String, Object> chamaGroups;

    public LoginChamaModel(){}

    public LoginChamaModel(Map<String, Object> chamaGroups) {
        this.chamaGroups = chamaGroups;
    }

    public Map<String, Object> getChamaGroups() {
        return chamaGroups;
    }

    public void setChamaGroups(Map<String, Object> chamaGroups) {
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
        dest.writeInt(this.chamaGroups.size());
        for (Map.Entry<String, Object> entry : this.chamaGroups.entrySet()) {
            dest.writeString(entry.getKey());
            dest.writeParcelable((Parcelable) entry.getValue(), flags);
        }
    }

    protected LoginChamaModel(Parcel in) {
        int chamaGroupsSize = in.readInt();
        this.chamaGroups = new HashMap<String, Object>(chamaGroupsSize);
        for (int i = 0; i < chamaGroupsSize; i++) {
            String key = in.readString();
            Object value = in.readParcelable(Object.class.getClassLoader());
            this.chamaGroups.put(key, value);
        }
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
