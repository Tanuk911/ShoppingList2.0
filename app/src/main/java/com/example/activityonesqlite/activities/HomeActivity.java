package com.example.activityonesqlite.activities;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.example.activityonesqlite.R;
import com.example.activityonesqlite.models.entities.Schedule;
import com.example.activityonesqlite.utils.DateUtility;
import com.example.activityonesqlite.utils.AdapterUtility;
import com.example.activityonesqlite.application.App;
import com.example.activityonesqlite.utils.ExecutorUtility;

import java.util.ArrayList;

public class HomeActivity extends AppCompatActivity {

    DatePicker datePicker;
    Spinner spinnerLocation;
    RecyclerView txtScheduleList;
    Button btnAdd, btnView;
    AdapterUtility adapterUtility = new AdapterUtility(this);
    Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_home);

        casting();
        setupSpinnerLocation();
        setupRecyclerView();
        onClickListeners();

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    }

    private void casting(){
        datePicker = findViewById(R.id.main_datePicker);
        spinnerLocation = findViewById(R.id.main_spinnerLocation);
        txtScheduleList = findViewById(R.id.main_txtScheduleList);
        btnAdd = findViewById(R.id.main_btnAdd);
        btnView = findViewById(R.id.main_btnView);
    }

    private void onClickListeners(){
        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addScheduleButton();
            }
        });

        btnView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openListActivities();
            }
        });
    }

    private void setupSpinnerLocation(){
        ArrayList<String> locationsList = new ArrayList<>();
        locationsList.add("Keels");
        locationsList.add("Cargills");
        locationsList.add("Arpico");
        locationsList.add("Glomark");
        locationsList.add("SPAR");

        adapterUtility.setSpinner(locationsList, spinnerLocation);
    }

    private void setupRecyclerView(){
        adapterUtility.setupScheduleRecyclerView(txtScheduleList);
    }

    private void addScheduleButton(){

        String date = new DateUtility(datePicker).getDate();
        String location = spinnerLocation.getSelectedItem().toString();
        Schedule schedule = new Schedule(date, location);

        ExecutorUtility.runOnBackgroundThread(() -> {

            long result = App.getInstance().getDatabaseInstance().scheduleDao().insertSchedule(schedule);

            ExecutorUtility.runOnMainThread(() -> {

                if (result == -1) {
                    Toast.makeText(HomeActivity.this, "Schedule Already Exists!", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(HomeActivity.this, "New Schedule Added", Toast.LENGTH_SHORT).show();
                    setupRecyclerView();
                }
            });

        });
    }

    private void openListActivities(){
        Intent intent = new Intent(HomeActivity.this, ListsActivity.class);
        startActivity(intent);
    }
}