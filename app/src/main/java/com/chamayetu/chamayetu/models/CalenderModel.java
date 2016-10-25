package com.chamayetu.chamayetu.models;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * ChamaYetu
 * com.chamayetu.chamayetu.models
 * Created by lusinabrian on 25/10/16.
 * Description: Model for calender events*/

public class CalenderModel implements Parcelable{
    private String eventDetails,eventDate,eventLocation,eventMonth,eventTime;

    public CalenderModel() {}

    public CalenderModel(String eventDetails, String eventDate, String eventLocation, String eventMonth, String eventTime) {
        this.eventDetails = eventDetails;
        this.eventDate = eventDate;
        this.eventLocation = eventLocation;
        this.eventMonth = eventMonth;
        this.eventTime = eventTime;
    }

    public String getEventDetails() {
        return eventDetails;
    }

    public void setEventDetails(String eventDetails) {
        this.eventDetails = eventDetails;
    }

    public String getEventDate() {
        return eventDate;
    }

    public void setEventDate(String eventDate) {
        this.eventDate = eventDate;
    }

    public String getEventLocation() {
        return eventLocation;
    }

    public void setEventLocation(String eventLocation) {
        this.eventLocation = eventLocation;
    }

    public String getEventMonth() {
        return eventMonth;
    }

    public void setEventMonth(String eventMonth) {
        this.eventMonth = eventMonth;
    }

    public String getEventTime() {
        return eventTime;
    }

    public void setEventTime(String eventTime) {
        this.eventTime = eventTime;
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.eventDetails);
        dest.writeString(this.eventDate);
        dest.writeString(this.eventLocation);
        dest.writeString(this.eventTime);
        dest.writeString(this.eventMonth);
    }

    protected CalenderModel(Parcel in) {
        this.eventDetails = in.readString();
        this.eventDate = in.readString();
        this.eventLocation = in.readString();
        this.eventTime = in.readString();
        this.eventMonth = in.readString();
    }

    public static final Creator<CalenderModel> CREATOR = new Creator<CalenderModel>() {
        @Override
        public CalenderModel createFromParcel(Parcel source) {
            return new CalenderModel(source);
        }

        @Override
        public CalenderModel[] newArray(int size) {
            return new CalenderModel[size];
        }
    };
}
