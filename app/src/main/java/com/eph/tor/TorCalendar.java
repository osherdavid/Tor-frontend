package com.eph.tor;

import android.content.Context;
import android.os.Build;
import android.util.AttributeSet;
import android.util.Log;

import androidx.annotation.RequiresApi;

import com.applandeo.materialcalendarview.CalendarView;
import com.applandeo.materialcalendarview.EventDay;
import com.applandeo.materialcalendarview.exceptions.OutOfDateRangeException;
import com.applandeo.materialcalendarview.listeners.OnDayClickListener;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class TorCalendar extends CalendarView implements OnDayClickListener{

    private List<EventDay> appointments;

    @RequiresApi(api = Build.VERSION_CODES.O)
    public TorCalendar(Context context, AttributeSet attrs) throws OutOfDateRangeException {
        super(context, attrs);
        this.appointments = new ArrayList<>();
        this.setCurrentDate();
        this.appointments.add(new Appointment(Calendar.getInstance(), 12, 16));
        this.appointments.add(new Appointment(Calendar.getInstance(), 16, 16));
        this.displayEvents();
        this.setOnDayClickListener(this);
    }

    private void displayEvents() {
        Log.d("TAG", "displayEvents: " + this.appointments);
        this.setEvents(this.appointments);
        this.invalidate();
    }

    @Override
    public void onDayClick(EventDay eventDay) {
        Calendar clickedCalendar = eventDay.getCalendar();
        for (EventDay appointment: this.appointments) {
            if(appointment.getCalendar().equals(clickedCalendar)){
                Log.d("TAG", "displaySelectedDay: " + appointment);
            }
        }
        this.invalidate();
    }

    public Calendar setCurrentDate() throws OutOfDateRangeException {
        Calendar calendar = Calendar.getInstance();
        this.setDate(calendar);
        return calendar;
    }

}
