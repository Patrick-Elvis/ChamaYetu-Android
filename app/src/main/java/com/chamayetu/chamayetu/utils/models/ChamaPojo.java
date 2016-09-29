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
    private int dateFrom, dateTo,nextMeetingTime,agendaTime, members;
    private float amountTotal;
    private String venue, milestone;

    /**Constructor for creating the ChamaPojo object*/
    public ChamaPojo(int dateFrom, int dateTo, int nextMeetingTime, int agendaTime, int members, float amountTotal, String venue, String milestone) {
        this.dateFrom = dateFrom;
        this.dateTo = dateTo;
        this.nextMeetingTime = nextMeetingTime;
        this.agendaTime = agendaTime;
        this.members = members;
        this.amountTotal = amountTotal;
        this.venue = venue;
        this.milestone = milestone;
    }

    public int getDateFrom() {
        return dateFrom;
    }

    public void setDateFrom(int dateFrom) {
        this.dateFrom = dateFrom;
    }

    public int getDateTo() {
        return dateTo;
    }

    public void setDateTo(int dateTo) {
        this.dateTo = dateTo;
    }

    public int getNextMeetingTime() {
        return nextMeetingTime;
    }

    public void setNextMeetingTime(int nextMeetingTime) {
        this.nextMeetingTime = nextMeetingTime;
    }

    public int getAgendaTime() {
        return agendaTime;
    }

    public void setAgendaTime(int agendaTime) {
        this.agendaTime = agendaTime;
    }

    public int getMembers() {
        return members;
    }

    public void setMembers(int members) {
        this.members = members;
    }

    public float getAmountTotal() {
        return amountTotal;
    }

    public void setAmountTotal(float amountTotal) {
        this.amountTotal = amountTotal;
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
        int[] data = new int[5];
        String[] stringData = new String[2];
        source.readIntArray(data);
        source.readStringArray(stringData);

        this.dateFrom = data[0];
        this.dateTo = data[1];
        this.nextMeetingTime = data[2];
        this.agendaTime = data[3];
        this.members = data[4];

        amountTotal = source.readFloat();
        this.venue = stringData[0];
        this.milestone = stringData[1];
    }

    public static final Creator<ChamaPojo> CREATOR = new Creator<ChamaPojo>() {
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
        dest.writeIntArray(new int[]{this.dateFrom, this.dateTo,this.nextMeetingTime,
                this.agendaTime, this.members});
    }
}
