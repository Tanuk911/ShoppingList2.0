package com.example.activityonesqlite.utils;

import android.content.Context;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.activityonesqlite.adapters.ItemListAdapter;
import com.example.activityonesqlite.adapters.ScheduleListAdapter;
import com.example.activityonesqlite.application.App;
import com.example.activityonesqlite.models.entities.ListItem;
import com.example.activityonesqlite.models.entities.Schedule;

import java.util.ArrayList;
import java.util.List;

public class AdapterUtility {

    private Context context;

    public AdapterUtility(Context context) {
        this.context = context;
    }

    public void setupScheduleRecyclerView(RecyclerView recyclerView) {
        ExecutorUtility.runOnBackgroundThread(() -> {
            List<Schedule> schedules = App.getInstance().getDatabaseInstance().scheduleDao().getAllSchedules();

            ExecutorUtility.runOnMainThread(() -> {
                ScheduleListAdapter scheduleListAdapter = new ScheduleListAdapter(context, schedules);
                recyclerView.setLayoutManager(new LinearLayoutManager(context));
                recyclerView.setAdapter(scheduleListAdapter);
            });
        });
    }

    public void setupListRecyclerView(RecyclerView recyclerView, int scheduleId) {
        ExecutorUtility.runOnBackgroundThread(() -> {
            List<ListItem> listItems = App.getInstance().getDatabaseInstance().listItemDao().getListItemsByScheduleId(scheduleId);

            ExecutorUtility.runOnMainThread(() -> {
                ItemListAdapter itemListAdapter = new ItemListAdapter(context, listItems);

                recyclerView.setLayoutManager(new LinearLayoutManager(context));
                recyclerView.setAdapter(itemListAdapter);
            });
        });
    }

    public void setSpinner(ArrayList<String> spinnerItems, Spinner spinner) {
        ArrayAdapter<String> adapter = new ArrayAdapter<>(context, android.R.layout.simple_spinner_item, spinnerItems);
        adapter.setDropDownViewResource(android.R.layout.select_dialog_singlechoice);
        spinner.setAdapter(adapter);
    }
}
