package com.eph.tor.ui.my_calendars;

import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.applandeo.materialcalendarview.CalendarView;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;


import com.eph.tor.R;
import com.eph.tor.TorCalendar;

public class MyCalendarsFragment extends Fragment {


    @RequiresApi(api = Build.VERSION_CODES.O)
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_my_calendars, container, false);
        TorCalendar calendarView = root.findViewById(R.id.my_calendar_view);
        LinearLayout scrollViewLinearLayout = root.findViewById(R.id.my_calendars_scroll_view_linear_layout);
        calendarView.setAppointmentsLinearLayout(scrollViewLinearLayout);
        return root;
    }
}