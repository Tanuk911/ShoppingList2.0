package com.example.activityonesqlite.activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.Spinner;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.example.activityonesqlite.databases.DBHelper;
import com.example.activityonesqlite.R;
import com.example.activityonesqlite.models.entities.ScheduleModel;
import com.example.activityonesqlite.utilites.DateUtility;
import com.example.activityonesqlite.utilites.AdapterUtility;

import java.util.ArrayList;
import java.util.List;

public class HomeActivity extends AppCompatActivity {

    DatePicker datePicker;
    Spinner spinnerLocation;
    RecyclerView txtScheduleList;
    Button btnAdd, btnView;
    DBHelper dbHelper = new DBHelper(HomeActivity.this);
    AdapterUtility adapterUtility = new AdapterUtility(this);

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
        List<ScheduleModel> allSchedules = dbHelper.getAllSchedules();

        String date = new DateUtility(datePicker).getDate();
        String location = spinnerLocation.getSelectedItem().toString();
        ScheduleModel scheduleModel = new ScheduleModel(-1, date, location);
        dbHelper.addSchedule(scheduleModel);

        setupRecyclerView();
    }

    private void openListActivities(){
        Intent intent = new Intent(HomeActivity.this, ListsActivity.class);
        startActivity(intent);
    }
}