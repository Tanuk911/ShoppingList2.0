package com.example.activityonesqlite.fragments;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.content.Context;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.Spinner;
import android.widget.TextView;

import com.example.activityonesqlite.R;
import com.example.activityonesqlite.constants.AppConstants;
import com.example.activityonesqlite.utils.AdapterUtility;
import com.example.activityonesqlite.utils.DateUtility;

import java.util.Calendar;

public class ListItemsEditFragment extends Fragment {

    View view;
    TextView txtDate, txtLocation;
    DatePickerDialog datePickerDialog;
    RecyclerView recyclerList;
    int scheduleId;
    String date, location;
    Context context;

    public ListItemsEditFragment(int scheduleId, String date, String location, Context context){
        this.date = date;
        this.location = location;
        this.scheduleId = scheduleId;
        this.context = context;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_list_items_edit, container, false);
        casting();
        initDatePicker();
        onClickListeners();
        setUpRecyclerView();

        return view;
    }

    private void casting(){
        txtDate = view.findViewById(R.id.list_item_edit_fragment_txtDate);
        txtDate.setText(date);
        txtLocation = view.findViewById(R.id.list_item_edit_fragment_txtLocation);
        txtLocation.setText(location);
        recyclerList = view.findViewById(R.id.list_item_edit_fragment_recyclerList);
    }

    private void onClickListeners(){
        txtDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                datePickerDialog.show();
            }
        });

        txtLocation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
    }

    private void setUpRecyclerView(){
        new AdapterUtility(context).setupEditListRecyclerView(recyclerList, scheduleId);
    }

    private void initDatePicker() {
        DatePickerDialog.OnDateSetListener dateSetListener = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                month = month + 1;
                String date = DateUtility.dateStringBuilder(dayOfMonth, month, year);
                txtDate.setText(date);
            }
        };

        Calendar calendar = Calendar.getInstance();
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH);
        int day = calendar.get(Calendar.DAY_OF_MONTH);

        int style = AlertDialog.THEME_HOLO_LIGHT;

        datePickerDialog = new DatePickerDialog(context, style, dateSetListener, year, month, day);
    }
}