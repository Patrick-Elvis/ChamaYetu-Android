package com.chamayetu.chamayetu.models;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * ChamaYetu
 * com.chamayetu.chamayetu.models
 * Created by lusinabrian on 15/10/16.
 * Description: for the members node
 */

public class MembersModel implements Parcelable {
    private String username;
    private boolean isMember;

    public MembersModel() {}

    public MembersModel(String username, boolean isMember) {
        this.username = username;
        this.isMember = isMember;
    }

    @Override
    public String toString() {
        return "MembersModel{" +
                "username='" + username + '\'' +
                ", isMember=" + isMember +
                '}';
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public boolean isMember() {
        return isMember;
    }

    public void setMember(boolean member) {
        isMember = member;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.username);
        dest.writeByte(this.isMember ? (byte) 1 : (byte) 0);
    }

    protected MembersModel(Parcel in) {
        this.username = in.readString();
        this.isMember = in.readByte() != 0;
    }

    public static final Parcelable.Creator<MembersModel> CREATOR = new Parcelable.Creator<MembersModel>() {
        @Override
        public MembersModel createFromParcel(Parcel source) {
            return new MembersModel(source);
        }

        @Override
        public MembersModel[] newArray(int size) {
            return new MembersModel[size];
        }
    };
}
