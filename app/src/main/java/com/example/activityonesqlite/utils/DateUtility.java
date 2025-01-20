package com.example.activityonesqlite.utils;

import android.app.DatePickerDialog;
import android.widget.DatePicker;

public class DateUtility {

    public DateUtility(){
    }

    public static String getDateFromDatePicker(DatePicker datePicker){
        int day = datePicker.getDayOfMonth();
        int month = datePicker.getMonth()+1;
        int year = datePicker.getYear();

        return dateStringBuilder(day, month, year);
    }

    public static String dateStringBuilder(int day, int month, int year){
        return String.format("%02d/%02d/%04d", day, month, year);
    }
}
