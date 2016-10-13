package com.chamayetu.chamayetu.models;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.Map;

/**
 * ChamaYetu
 * com.chamayetu.chamayetu.models
 * Created by lusinabrian on 29/09/16.
 * Description: User Pojo for the User node in the database
 */

public class UserPojo implements Parcelable {
    private String firstName,lastName, email, role;
    private long phoneNumber, totalContributed,avgContribution;
    private Map<String, Object> chamaGroups;

    /**Default constructor required for calls to DataSnapshot.getValue(UserPojo.class)*/
    public UserPojo(){}

    public UserPojo(String firstName, String lastName, String email, String role, long phoneNumber, long totalContributed, long avgContribution, Map<String, Object> chamaGroups) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.role = role;
        this.phoneNumber = phoneNumber;
        this.totalContributed = totalContributed;
        this.avgContribution = avgContribution;
        this.chamaGroups = chamaGroups;
    }

    protected UserPojo(Parcel in) {
        this.firstName = in.readString();
        this.lastName = in.readString();
        this.email = in.readString();
        this.role = in.readString();
        this.phoneNumber = in.readInt();
        this.totalContributed = in.readInt();
        this.avgContribution = in.readInt();
        // todo create a map
        //this.chamaGroups = in.createStringArrayList();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(firstName);
        dest.writeString(lastName);
        dest.writeString(email);
        dest.writeString(role);
        dest.writeLong(phoneNumber);
        dest.writeLong(totalContributed);
        dest.writeLong(avgContribution);
        dest.writeMap(chamaGroups);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Parcelable.Creator CREATOR = new Parcelable.Creator() {
        @Override
        public UserPojo createFromParcel(Parcel in) {
            return new UserPojo(in);
        }

        @Override
        public UserPojo[] newArray(int size) {
            return new UserPojo[size];
        }
    };

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public long getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(long phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public long getTotalContributed() {
        return totalContributed;
    }

    public void setTotalContributed(int totalContributed) {
        this.totalContributed = totalContributed;
    }

    public long getAvgContribution() {
        return avgContribution;
    }

    public void setAvgContribution(int avgContribution) {
        this.avgContribution = avgContribution;
    }

    public Map<String, Object> getChamaGroups() {
        return chamaGroups;
    }

    public void setChamaGroups(Map<String, Object> chamaGroups) {
        this.chamaGroups = chamaGroups;
    }

}
