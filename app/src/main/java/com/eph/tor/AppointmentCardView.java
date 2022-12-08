package com.eph.tor;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.util.AttributeSet;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.ViewOutlineProvider;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.core.content.ContextCompat;

import com.applandeo.materialcalendarview.exceptions.OutOfDateRangeException;

public class AppointmentCardView extends CardView {
    private TextView businessOwnerTextView;
    private TextView serviceTextView;
    private TextView timeTextView;
    private Context context;
    private LinearLayout mainLinearLayout;
    final int LAYOUT_MARGINS = 30;
    final int TEXT_SIZE = 20;

    private View[][] GetCardStructure() {
        View[][] views = {
                {},
                {this.timeTextView},
                {this.serviceTextView, this.businessOwnerTextView},
                {},
        };
        return views;
    }

    public AppointmentCardView(Context context) {
        super(context);
        this.context = context;
    }

    public AppointmentCardView(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.context = context;
    }

    public void SetAppointment(Appointment appointment) {
        Log.i("TAG", "SetAppointment: Setting Appointment");
        this.SetAttributes();
        this.businessOwnerTextView = this.CreateAppointmentTextView(appointment.businessOwner);
        this.serviceTextView = this.CreateAppointmentTextView(appointment.service);
        this.timeTextView = this.CreateAppointmentTextView(appointment.getTimeString());
        mainLinearLayout = GetLinearLayout(LinearLayout.HORIZONTAL, false, 1f);
        this.addView(mainLinearLayout);
        CreateCard();
    }

    private void CreateCard() {
        View[][] cardStruct = GetCardStructure();
        for (int i = 0; i < cardStruct.length; i++) {
            LinearLayout subLinearLayout = GetLinearLayout(LinearLayout.VERTICAL, true, 1f/cardStruct.length);
            //subLinearLayout.setGravity(Gravity.CENTER);
            for (View view : cardStruct[i]) {
                subLinearLayout.addView(view);
            }
            mainLinearLayout.addView(subLinearLayout);
        }
    }

    private LinearLayout GetLinearLayout(int orientation, boolean shouldWrapContent, float weight) {
        LinearLayout linearLayout = new LinearLayout(this.context);
        linearLayout.setOrientation(orientation);
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                shouldWrapContent ? LinearLayout.LayoutParams.WRAP_CONTENT : LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.MATCH_PARENT,
                weight
        );
        params.gravity = -1;
        params.setMargins(LAYOUT_MARGINS, LAYOUT_MARGINS, LAYOUT_MARGINS,LAYOUT_MARGINS);
        linearLayout.setLayoutParams(params);
        return linearLayout;
    }

    private void SetAttributes() {
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.MATCH_PARENT
        );
        params.gravity = -1;
        params.setMargins(0, this.LAYOUT_MARGINS, 0, this.LAYOUT_MARGINS);
        this.setLayoutParams(params);
        this.setRadius(150);
        this.setCardElevation(20);
        this.setPreventCornerOverlap(true);
        this.setCardBackgroundColor(ContextCompat.getColor(this.context, R.color.colorPrimary));
    }

    private TextView CreateAppointmentTextView(String text) {
        TextView textView = new TextView(context);
        textView.setText(text);
        textView.setTextSize(this.TEXT_SIZE);
        textView.setTextColor(Color.WHITE);
        return textView;
    }
}
