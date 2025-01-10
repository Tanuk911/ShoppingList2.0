package com.example.activityonesqlite.adapters;

import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.activityonesqlite.R;

public class ScheduleListViewHolder extends RecyclerView.ViewHolder {

    TextView txtDate, txtLocation;
    Button btnDelete;
    LinearLayout mainLinear;
    public ScheduleListViewHolder(@NonNull View itemView) {
        super(itemView);
        txtDate = itemView.findViewById(R.id.schedule_item_view_txtDate);
        txtLocation = itemView.findViewById(R.id.schedule_item_view_txtLocation);

        mainLinear = itemView.findViewById(R.id.schedule_item_view_mainLinear);
        btnDelete = itemView.findViewById(R.id.schedule_item_view_btnDelete);

    }
}
