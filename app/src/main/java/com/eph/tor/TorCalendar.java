package com.eph.tor;

import android.content.Context;
import android.util.AttributeSet;

import com.applandeo.materialcalendarview.CalendarView;
import com.applandeo.materialcalendarview.exceptions.OutOfDateRangeException;

import java.util.Calendar;

public class TorCalendar extends CalendarView {

    public TorCalendar(Context context, AttributeSet attrs) throws OutOfDateRangeException {
        super(context, attrs);
        this.setCurrentDate();
    }

    public Calendar setCurrentDate() throws OutOfDateRangeException {
        Calendar calendar = Calendar.getInstance();
        this.setDate(calendar);
        return calendar;
    }
}
