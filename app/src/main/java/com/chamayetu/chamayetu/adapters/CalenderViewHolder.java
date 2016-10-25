package com.chamayetu.chamayetu.adapters;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.chamayetu.chamayetu.R;
import com.chamayetu.chamayetu.models.CalenderModel;

/**
 * ChamaYetu
 * com.chamayetu.chamayetu.adapters
 * Created by lusinabrian on 25/10/16.
 * Description:
 */

public class CalenderViewHolder extends RecyclerView.ViewHolder{

    public TextView eventDetails, eventLocation,eventTime,eventDate,eventMonth;

    public CalenderViewHolder(View itemView) {
        super(itemView);
        eventDetails = (TextView) itemView.findViewById(R.id.calenderevent_details);
        eventLocation = (TextView) itemView.findViewById(R.id.calenderevent_location);
        eventTime = (TextView)itemView.findViewById(R.id.calenderevent_time);
        eventDate = (TextView)itemView.findViewById(R.id.calenderevent_date);
        eventMonth = (TextView)itemView.findViewById(R.id.calenderevent_month);
    }

    public void bind(CalenderModel calenderModel){
        eventDetails.setText(calenderModel.getEventDetails());
        eventLocation.setText(calenderModel.getEventLocation());
        eventTime.setText(calenderModel.getEventTime());
        /*todo: date has to be formated to get number, date, like 17*/
        eventDate.setText(calenderModel.getEventDate());
        eventMonth.setText(calenderModel.getEventDate());
    }
}
