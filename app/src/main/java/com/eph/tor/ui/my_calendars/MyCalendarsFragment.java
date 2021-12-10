package com.eph.tor.ui.my_calendars;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.applandeo.materialcalendarview.CalendarView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import com.eph.tor.R;

public class MyCalendarsFragment extends Fragment {

    private MyCalendarsViewModel myCalendarsViewModel;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        myCalendarsViewModel =
                ViewModelProviders.of(this).get(MyCalendarsViewModel.class);
        View root = inflater.inflate(R.layout.fragment_my_calendars, container, false);
        final CalendarView calendarView = root.findViewById(R.id.my_calendar_view);
        return root;
    }
}