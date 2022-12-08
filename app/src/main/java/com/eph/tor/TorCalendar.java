package com.eph.tor;

import android.content.Context;
import android.graphics.Color;
import android.os.Build;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;

import androidx.annotation.RequiresApi;
import androidx.cardview.widget.CardView;

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
    private AppointmentCardView appointmentsTemplate;

    public TorCalendar(Context context, AttributeSet attrs) throws OutOfDateRangeException {
        super(context, attrs);
        this.appointments = new ArrayList<>();
        this.appointmentsForChosenDay = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            this.appointments.add(new Appointment(Calendar.getInstance(), 16, 16, "driver" + i, "driving lesson"));
        }
        this.setCurrentDate();
        this.displayEvents();
        this.setOnDayClickListener(this);
    }
    public void setAppointmentsTemplate(AppointmentCardView appointmentsTemplate) {
        this.appointmentsTemplate = appointmentsTemplate;
    }
    public void setAppointmentsLinearLayout(LinearLayout linearLayout) {
        this.appointmentsLinearLayout = linearLayout;
        this.updateLinearLayout();
    }

    private void displayEvents() {
        this.setEvents(this.appointments);
        this.invalidate();
    }

    @Override
    public void onDayClick(EventDay eventDay) {
        Calendar clickedCalendar = eventDay.getCalendar();
        getAppointmentsForDay(clickedCalendar);
        this.updateLinearLayout();
        this.invalidate();
    }

    private void updateLinearLayout() {
        this.appointmentsLinearLayout.removeAllViews();
        if(this.appointmentsLinearLayout != null) {
            for (EventDay appointment : this.appointmentsForChosenDay) {
                AppointmentCardView appointmentCard = new AppointmentCardView(this.appointmentsLinearLayout.getContext());
                appointmentCard.SetAppointment((Appointment) appointment);
                this.appointmentsLinearLayout.addView(appointmentCard);

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
