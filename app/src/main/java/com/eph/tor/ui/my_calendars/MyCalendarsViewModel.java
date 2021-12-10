package com.eph.tor.ui.my_calendars;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class MyCalendarsViewModel extends ViewModel {

    private MutableLiveData<String> mText;

    public MyCalendarsViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("This is My Calendars fragment");
    }

    public LiveData<String> getText() {
        return mText;
    }
}