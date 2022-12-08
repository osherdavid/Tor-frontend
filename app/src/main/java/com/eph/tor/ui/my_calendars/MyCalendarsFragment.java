package com.eph.tor.ui.my_calendars;

import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.applandeo.materialcalendarview.CalendarView;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;


import com.eph.tor.AppointmentCardView;
import com.eph.tor.R;
import com.eph.tor.TorCalendar;

import static android.content.ContentValues.TAG;

public class MyCalendarsFragment extends Fragment {

    private View root;

    @RequiresApi(api = Build.VERSION_CODES.O)
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        this.root = inflater.inflate(R.layout.fragment_my_calendars, container, false);
        return root;
    }

    @Override
    public void onStart() {
        super.onStart();
        //Log.d("CalendarFragment", "onStart: " + root.findViewById(R.id.appointment_card_base));
        TorCalendar calendarView = root.findViewById(R.id.my_calendar_view);
        //AppointmentCardView appointmentCardViewBase = root.findViewById(R.id.appointment_card_base);
        LinearLayout scrollViewLinearLayout = root.findViewById(R.id.my_calendars_scroll_view_linear_layout);
        //ViewGroup.LayoutParams vert = root.findViewById(R.id.appointment_card_base_vertical_layout).getLayoutParams();
        //ViewGroup.LayoutParams horz = root.findViewById(R.id.appointment_card_base_horizontal_layout).getLayoutParams();
        //calendarView.setAppointmentsTemplate(appointmentCardViewBase);
        calendarView.setAppointmentsLinearLayout(scrollViewLinearLayout);
    }
}