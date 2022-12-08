package com.eph.tor;

import android.content.Context;
import android.os.Build;
import android.widget.TextView;

import androidx.annotation.RequiresApi;

import com.applandeo.materialcalendarview.EventDay;

import java.util.Calendar;

public class Appointment extends EventDay {
    private int hour;
    private int minute;
    public String service;
    public String businessOwner;
    public TextView diaplayText;

    public Appointment(Calendar day, int hour, int minute, String businessOwner, String service) {
        super(day, R.drawable.ic_baseline_bookmark_24);
        this.hour = hour;
        this.minute = minute;
        this.businessOwner = businessOwner;
        this.service = service;
    }

    public int getHour(){
        return this.hour;
    }

    public int getMinute(){
        return this.minute;
    }

    public String getTimeString() {
        return this.getHour() + ":" + this.getMinute();
    }

    public String toString(boolean shortDescription) {
        if(!shortDescription) {
            return "Appointment at " + this.businessOwner + " for " + this.service + "\n"
                    + this.getHour() + ":" + this.getMinute();
        }
        else {
            return "";
        }
    }

    public TextView createAppointmentCardForContext(Context context) {
        this.diaplayText = new TextView(context);
        this.diaplayText.setText(this.toString());
        this.diaplayText.setTextSize(20);
        return this.diaplayText;
    }
}
