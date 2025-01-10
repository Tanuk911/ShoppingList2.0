package com.example.activityonesqlite.utilites;

import android.content.Context;
import android.widget.TextView;

import java.util.ArrayList;

public class StringUtility {

    public void updateScheduleListView(TextView textView, ArrayList<String> datesList, ArrayList<String> locationsList){
        StringBuilder builder = new StringBuilder();
        for (int i = 0; i < datesList.size(); i++){
            builder.append(i+1).append(") ").append(datesList.get(i)).append(", ").append(locationsList.get(i)).append("\n");
        }
        textView.setText(builder.toString().trim());
    }
}
