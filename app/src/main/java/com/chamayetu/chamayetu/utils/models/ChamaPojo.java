package com.chamayetu.chamayetu.utils.models;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * ChamaYetu
 * com.chamayetu.chamayetu.utils.models
 * Created by lusinabrian on 28/09/16.
 * Description: The Pojo class for the Chama Object to be displayed in {@link com.chamayetu.chamayetu.mychama.MyChamaView}
 */
public class ChamaPojo implements Parcelable {
    //brief statement amounts
    private long totalAmount,amountExpected, members;
    private String name, venue, milestone,dateCreated,milestoneDate,nextMeetingTime;

    public ChamaPojo(){}

    /**Constructor for creating the ChamaPojo object*/
    public ChamaPojo(String dateCreated,String nextMeetingTime, String milestoneDate, long members, long totalAmount, long amountExpected, String name, String venue, String milestone) {
        this.dateCreated = dateCreated;
        this.nextMeetingTime = nextMeetingTime;
        this.milestoneDate = milestoneDate;
        this.members = members;
        this.totalAmount = totalAmount;
        this.amountExpected = amountExpected;
        this.name = name;
        this.venue = venue;
        this.milestone = milestone;
    }

    public String getDateCreated() {
        return dateCreated;
    }

    public void setDateCreated(String dateCreated) {
        this.dateCreated = dateCreated;
    }

    public String getMilestoneDate() {
        return milestoneDate;
    }

    public void setMilestoneDate(String milestoneDate) {
        this.milestoneDate = milestoneDate;
    }

    public long getAmountExpected() {
        return amountExpected;
    }

    public void setAmountExpected(long amountExpected) {
        this.amountExpected = amountExpected;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getNextMeetingTime() {
        return nextMeetingTime;
    }

    public void setNextMeetingTime(String nextMeetingTime) {
        this.nextMeetingTime = nextMeetingTime;
    }

    public long getMembers() {
        return members;
    }

    public void setMembers(long members) {
        this.members = members;
    }

    public long getTotalAmount() {
        return totalAmount;
    }

    public void setTotalAmount(long totalAmount) {
        this.totalAmount = totalAmount;
    }

    public String getVenue() {
        return venue;
    }

    public void setVenue(String venue) {
        this.venue = venue;
    }

    public String getMilestone() {
        return milestone;
    }

    public void setMilestone(String milestone) {
        this.milestone = milestone;
    }

    public ChamaPojo(Parcel source) {
        this.totalAmount =source.readLong();
        this.amountExpected=source.readLong();
        this.members=source.readLong();
        this.name = source.readString();
        this.venue= source.readString();
        this.milestone= source.readString();
        this.dateCreated= source.readString();
        this.milestoneDate= source.readString();
        this.nextMeetingTime= source.readString();

    }

    public static final Parcelable.Creator CREATOR = new Parcelable.Creator() {
        @Override
        public ChamaPojo createFromParcel(Parcel in) {
            return new ChamaPojo(in);
        }

        @Override
        public ChamaPojo[] newArray(int size) {
            return new ChamaPojo[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeLong(this.totalAmount);
        dest.writeLong(this.amountExpected);
        dest.writeLong(this.members);
        dest.writeString(this.name);
        dest.writeString(this.venue);
        dest.writeString(this.milestone);
        dest.writeString(this.dateCreated);
        dest.writeString(this.milestoneDate);
        dest.writeString(this.nextMeetingTime);
    }
}
