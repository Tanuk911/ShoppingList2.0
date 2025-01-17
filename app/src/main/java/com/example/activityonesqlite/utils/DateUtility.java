package com.example.activityonesqlite.utils;

import android.widget.DatePicker;

public class DateUtility {

    private DatePicker datePicker;

    public DateUtility(DatePicker datePicker){
        this.datePicker = datePicker;
    }

    public String getDate(){
        int day = datePicker.getDayOfMonth();
        int month = datePicker.getMonth()+1;
        int year = datePicker.getYear();

        String date = String.format("%02d/%02d/%04d", day, month, year);
        return date;
    }
}
