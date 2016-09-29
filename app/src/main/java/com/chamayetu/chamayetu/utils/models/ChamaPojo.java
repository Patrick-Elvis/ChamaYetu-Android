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
    private long dateCreated,milestoneDate,nextMeetingTime, members;
    private long totalAmount,amountExpected;
    private String name, venue, milestone;

    public ChamaPojo(){}

    /**Constructor for creating the ChamaPojo object*/
    public ChamaPojo(long dateCreated,long nextMeetingTime, long milestoneDate, long members, long totalAmount, long amountExpected, String name, String venue, String milestone) {
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

    public long getDateCreated() {
        return dateCreated;
    }

    public void setDateCreated(long dateCreated) {
        this.dateCreated = dateCreated;
    }

    public long getMilestoneDate() {
        return milestoneDate;
    }

    public void setMilestoneDate(long milestoneDate) {
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

    public long getNextMeetingTime() {
        return nextMeetingTime;
    }

    public void setNextMeetingTime(long nextMeetingTime) {
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
        long[] data = new long[3];
        String[] stringData = new String[2];
        source.readLongArray(data);
        source.readStringArray(stringData);

        this.nextMeetingTime = data[0];
        this.members = data[1];

        totalAmount = source.readLong();
        this.venue = stringData[0];
        this.milestone = stringData[1];
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
        dest.writeLongArray(new long[]{this.nextMeetingTime,
                this.milestoneDate, this.members});
    }
}
