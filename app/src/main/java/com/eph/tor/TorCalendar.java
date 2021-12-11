package com.eph.tor;

import android.content.Context;
import android.os.Build;
import android.util.AttributeSet;
import android.util.Log;
import android.widget.LinearLayout;

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
    public List<EventDay> appointmentsForChosenDay;
    private LinearLayout appointmentsLinearLayout;

    @RequiresApi(api = Build.VERSION_CODES.O)
    public TorCalendar(Context context, AttributeSet attrs) throws OutOfDateRangeException {
        super(context, attrs);
        this.appointments = new ArrayList<>();
        this.appointmentsForChosenDay = new ArrayList<>();
        for (int i = 0; i < 100; i++) {
            this.appointments.add(new Appointment(Calendar.getInstance(), 16, 16, "driver" + i, "driving lesson"));
        }
        this.setCurrentDate();
        this.displayEvents();
        this.setOnDayClickListener(this);
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    public void setAppointmentsLinearLayout(LinearLayout linearLayout) {
        this.appointmentsLinearLayout = linearLayout;
        this.updateLinearLayout();
    }

    private void displayEvents() {
        this.setEvents(this.appointments);
        this.invalidate();
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    public void onDayClick(EventDay eventDay) {
        Calendar clickedCalendar = eventDay.getCalendar();
        getAppointmentsForDay(clickedCalendar);
        this.updateLinearLayout();
        this.invalidate();
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    private void updateLinearLayout() {
        this.appointmentsLinearLayout.removeAllViews();
        if(this.appointmentsLinearLayout != null) {
            for (EventDay appointment : this.appointmentsForChosenDay) {
                this.appointmentsLinearLayout.addView((((Appointment) appointment).createTextViewForContext(
                        this.appointmentsLinearLayout.getContext())));
            }
        }
    }

    public List<EventDay> getAppointmentsForDay(Calendar calendar) {
        List<EventDay> appointmentsForDay = new ArrayList<>();
        for (EventDay appointment: this.appointments) {
            if(appointment.getCalendar().equals(calendar)){
                appointmentsForDay.add(appointment);
            }
        }
        this.appointmentsForChosenDay = appointmentsForDay;
        return appointmentsForDay;
    }

    public Calendar setCurrentDate() throws OutOfDateRangeException {
        Calendar calendar = Calendar.getInstance();
        this.setDate(calendar);
        this.getAppointmentsForDay(calendar);
        return calendar;
    }

}
