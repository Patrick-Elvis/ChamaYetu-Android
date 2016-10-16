package com.chamayetu.chamayetu.models;

import android.os.Parcel;
import android.os.Parcelable;

import com.chamayetu.chamayetu.dasboard.DashboardView;

/**
 * ChamaYetu
 * com.chamayetu.chamayetu.models
 * Created by lusinabrian on 28/09/16.
 * Description: The Pojo class for the Chama Object to be displayed in {@link DashboardView}
 */
public class ChamaPojo implements Parcelable {
    private long totalAmount,amountExpected, members,accountNumber;
    private String name, venue, milestone,dateCreated,milestoneDate,nextMeetingTime, bankName;
    private String chairPerson, viceChair, treasurer, secretary;

    public ChamaPojo(){}

    /**Constructor for creating the ChamaPojo object*/
    public ChamaPojo(String dateCreated,String nextMeetingTime, String milestoneDate, long members, long totalAmount, String name, String venue, String milestone) {
        this.dateCreated = dateCreated;
        this.nextMeetingTime = nextMeetingTime;
        this.milestoneDate = milestoneDate;
        this.members = members;
        this.totalAmount = totalAmount;
        this.name = name;
        this.venue = venue;
        this.milestone = milestone;
    }

    /**Constructor for creating the ChamaPojo object*/
    public ChamaPojo(String dateCreated,String nextMeetingTime, String milestoneDate, long members, long totalAmount, String name, String venue,String bankName, long accountNumber) {
        this.dateCreated = dateCreated;
        this.nextMeetingTime = nextMeetingTime;
        this.milestoneDate = milestoneDate;
        this.members = members;
        this.totalAmount = totalAmount;
        this.name = name;
        this.venue = venue;
        this.bankName = bankName;
        this.accountNumber = accountNumber;
    }

    @Override
    public String toString() {
        return "ChamaPojo{" +
                "totalAmount=" + totalAmount +
                ", amountExpected=" + amountExpected +
                ", members=" + members +
                ", accountNumber=" + accountNumber +
                ", name='" + name + '\'' +
                ", venue='" + venue + '\'' +
                ", milestone='" + milestone + '\'' +
                ", dateCreated='" + dateCreated + '\'' +
                ", milestoneDate='" + milestoneDate + '\'' +
                ", nextMeetingTime='" + nextMeetingTime + '\'' +
                ", bankName='" + bankName + '\'' +
                ", chairPerson='" + chairPerson + '\'' +
                ", viceChair='" + viceChair + '\'' +
                ", treasurer='" + treasurer + '\'' +
                ", secretary='" + secretary + '\'' +
                '}';
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

    public long getAccountNumber() {
        return accountNumber;
    }

    public void setAccountNumber(long accountNumber) {
        this.accountNumber = accountNumber;
    }

    public String getBankName() {
        return bankName;
    }

    public void setBankName(String bankName) {
        this.bankName = bankName;
    }

    public String getChairPerson() {
        return chairPerson;
    }

    public void setChairPerson(String chairPerson) {
        this.chairPerson = chairPerson;
    }

    public String getViceChair() {
        return viceChair;
    }

    public void setViceChair(String viceChair) {
        this.viceChair = viceChair;
    }

    public String getTreasurer() {
        return treasurer;
    }

    public void setTreasurer(String treasurer) {
        this.treasurer = treasurer;
    }

    public String getSecretary() {
        return secretary;
    }

    public void setSecretary(String secretary) {
        this.secretary = secretary;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeLong(this.totalAmount);
        dest.writeLong(this.amountExpected);
        dest.writeLong(this.members);
        dest.writeLong(this.accountNumber);
        dest.writeString(this.name);
        dest.writeString(this.venue);
        dest.writeString(this.milestone);
        dest.writeString(this.dateCreated);
        dest.writeString(this.milestoneDate);
        dest.writeString(this.nextMeetingTime);
        dest.writeString(this.bankName);
        dest.writeString(this.chairPerson);
        dest.writeString(this.viceChair);
        dest.writeString(this.treasurer);
        dest.writeString(this.secretary);
    }

    protected ChamaPojo(Parcel in) {
        this.totalAmount = in.readLong();
        this.amountExpected = in.readLong();
        this.members = in.readLong();
        this.accountNumber = in.readLong();
        this.name = in.readString();
        this.venue = in.readString();
        this.milestone = in.readString();
        this.dateCreated = in.readString();
        this.milestoneDate = in.readString();
        this.nextMeetingTime = in.readString();
        this.bankName = in.readString();
        this.chairPerson = in.readString();
        this.viceChair = in.readString();
        this.treasurer = in.readString();
        this.secretary = in.readString();
    }

    public static final Creator<ChamaPojo> CREATOR = new Creator<ChamaPojo>() {
        @Override
        public ChamaPojo createFromParcel(Parcel source) {
            return new ChamaPojo(source);
        }

        @Override
        public ChamaPojo[] newArray(int size) {
            return new ChamaPojo[size];
        }
    };
}
