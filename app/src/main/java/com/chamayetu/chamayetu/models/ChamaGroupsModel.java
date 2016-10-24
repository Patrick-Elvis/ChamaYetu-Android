package com.chamayetu.chamayetu.models;

import android.os.Parcel;
import android.os.Parcelable;
import java.util.Map;

/**
 * ChamaYetu
 * com.chamayetu.chamayetu.models
 * Created by lusinabrian on 24/10/16.
 * Description: Model class for the user's chama groups, Key,Value Pairs,
 * String and Boolean
 */

public class ChamaGroupsModel implements Parcelable {
    private Map<String, Boolean> chamaGroups;

    public ChamaGroupsModel(){}

    public ChamaGroupsModel(Map<String, Boolean> chamaGroups) {
        this.chamaGroups = chamaGroups;
    }

    private ChamaGroupsModel(Parcel in) {

    }

    public static final Creator<ChamaGroupsModel> CREATOR = new Creator<ChamaGroupsModel>() {
        @Override
        public ChamaGroupsModel createFromParcel(Parcel in) {
            return new ChamaGroupsModel(in);
        }

        @Override
        public ChamaGroupsModel[] newArray(int size) {
            return new ChamaGroupsModel[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public String toString() {
        return "ChamaGroupsModel{" +
                "chamaGroups=" + chamaGroups +
                '}';
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeMap(this.chamaGroups);
    }

    public Map<String, Boolean> getChamaGroups() {
        return chamaGroups;
    }

    public void setChamaGroups(Map<String, Boolean> chamaGroups) {
        this.chamaGroups = chamaGroups;
    }

}
