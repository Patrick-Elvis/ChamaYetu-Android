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

    protected ChamaPojo(Parcel in) {
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
    }
}
