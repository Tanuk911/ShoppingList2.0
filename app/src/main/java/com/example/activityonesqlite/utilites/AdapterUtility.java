package com.example.activityonesqlite.utilites;

import android.content.Context;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.activityonesqlite.adapters.ItemListAdapter;
import com.example.activityonesqlite.adapters.ScheduleListAdapter;
import com.example.activityonesqlite.databases.DBHelper;
import com.example.activityonesqlite.models.ItemModel;

import java.util.ArrayList;
import java.util.List;

public class AdapterUtility {

    private Context context;

    public AdapterUtility(Context context){
        this.context = context;
    }

    public void setupScheduleRecyclerView(RecyclerView recyclerView){
        DBHelper dbHelper = new DBHelper(context);
        ScheduleListAdapter scheduleListAdapter = new ScheduleListAdapter(context, dbHelper.getAllSchedules());

        recyclerView.setLayoutManager(new LinearLayoutManager(context));
        recyclerView.setAdapter(scheduleListAdapter);
    }

    public void setupListRecyclerView(RecyclerView recyclerView, String date, String location){
        DBHelper dbHelper = new DBHelper(context);
        int listScheduleId = dbHelper.getScheduleId(date, location);
        ItemListAdapter itemListAdapter = new ItemListAdapter(context, dbHelper.getAllLists(listScheduleId));

        recyclerView.setLayoutManager(new LinearLayoutManager(context));
        recyclerView.setAdapter(itemListAdapter);
    }

    public void setSpinner(ArrayList<String> spinnerItems, Spinner spinner){
        ArrayAdapter<String> adapter =
                new ArrayAdapter<>(context, android.R.layout.simple_spinner_item, spinnerItems);
        adapter.setDropDownViewResource(android.R.layout.select_dialog_singlechoice);
        spinner.setAdapter(adapter);
    }
}
