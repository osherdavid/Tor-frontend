package com.eph.tor;

import android.content.Context;
import android.os.Build;
import android.widget.TextView;

import androidx.annotation.RequiresApi;

import com.applandeo.materialcalendarview.EventDay;

import java.time.LocalTime;
import java.util.Calendar;
import java.util.GregorianCalendar;

public class Appointment extends EventDay {
    public LocalTime time;
    public String service;
    public String businessOwner;
    public TextView diaplayText;

    @RequiresApi(api = Build.VERSION_CODES.O)
    public Appointment(Calendar day, int hour, int minute, String businessOwner, String service) {
        super(day, R.drawable.ic_baseline_bookmark_24);
        this.time = LocalTime.of(hour, minute);
        this.businessOwner = businessOwner;
        this.service = service;
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    public int getHour(){
        return this.time.getHour();
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    public int getMinute(){
        return this.time.getMinute();
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    public String toString() {
        return "Appointment at " + this.businessOwner + " for " + this.service + "\n"
                + this.getHour() + ":" + this.getMinute();
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    public TextView createTextViewForContext(Context context) {
        this.diaplayText = new TextView(context);
        this.diaplayText.setText(this.toString());
        this.diaplayText.setTextSize(20);
        return this.diaplayText;
    }
}
