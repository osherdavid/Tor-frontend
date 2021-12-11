package com.eph.tor;

import android.os.Build;

import androidx.annotation.RequiresApi;

import com.applandeo.materialcalendarview.EventDay;

import java.time.LocalTime;
import java.util.Calendar;
import java.util.GregorianCalendar;

public class Appointment extends EventDay {
    private LocalTime time;

    @RequiresApi(api = Build.VERSION_CODES.O)
    public Appointment(Calendar day, int hour, int minute) {
        super(day, R.drawable.ic_baseline_bookmark_24);
        time = LocalTime.of(hour, minute);
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
        return "" + this.getHour() + ":" + this.getMinute();
    }
}
