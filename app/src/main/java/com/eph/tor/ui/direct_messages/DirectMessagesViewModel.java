package com.eph.tor.ui.direct_messages;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class DirectMessagesViewModel extends ViewModel {

    private MutableLiveData<String> mText;

    public DirectMessagesViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("This is Direct Messages fragment");
    }

    public LiveData<String> getText() {
        return mText;
    }
}