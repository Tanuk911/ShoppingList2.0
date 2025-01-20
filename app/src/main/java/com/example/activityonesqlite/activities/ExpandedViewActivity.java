package com.example.activityonesqlite.activities;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.example.activityonesqlite.R;
import com.example.activityonesqlite.application.App;
import com.example.activityonesqlite.constants.AppConstants;
import com.example.activityonesqlite.models.entities.ListItem;
import com.example.activityonesqlite.models.entities.Schedule;
import com.example.activityonesqlite.utils.AdapterUtility;
import com.example.activityonesqlite.utils.ExecutorUtility;

import java.util.ArrayList;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class ExpandedViewActivity extends AppCompatActivity {

    ImageButton btnBack;
    TextView txtDate, txtLocation;
    RecyclerView listsRecyclerView;
    ImageButton imgBtnPlus;
    EditText edTxtAddItem, edTxtQuantity;
    Spinner spinnerUnits;
    AdapterUtility adapterUtility = new AdapterUtility(ExpandedViewActivity.this);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_expanded_view);

        casting();
        setViewText();
        setupRecyclerView();
        setupSpinner();
        onClickListeners();

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    }

    private void casting() {
        btnBack = findViewById(R.id.expanded_view_close);
        imgBtnPlus = findViewById(R.id.expanded_view_btnAdd);
        txtDate = findViewById(R.id.expanded_view_txtDate);
        txtLocation = findViewById(R.id.expanded_view_txtLocation);
        listsRecyclerView = findViewById(R.id.expanded_view_recyclerList);
        edTxtAddItem = findViewById(R.id.expanded_view_edtxtNewItem);
        edTxtQuantity = findViewById(R.id.expanded_view_edtxtQty);
        spinnerUnits = findViewById(R.id.expanded_view_spinnerUnits);
    }

    private int getExtras() {
        Bundle extras = getIntent().getExtras();
        int scheduleId = extras.getInt("ScheduleId");

        return scheduleId;
    }

    private void setViewText() {
        ExecutorUtility.runOnBackgroundThread(() -> {

            Schedule schedule = App.getInstance().getDatabaseInstance().scheduleDao().getScheduleById(getExtras());

            ExecutorUtility.runOnMainThread(() -> {
                txtDate.setText(schedule.getScheduleDate());
                txtLocation.setText(schedule.getScheduleLocation());
            });
        });
    }

    private void setupRecyclerView() {
        adapterUtility.setupListRecyclerView(listsRecyclerView, getExtras());
    }

    private void setupSpinner() {
        adapterUtility.setSpinner(AppConstants.getUnits(), spinnerUnits);
    }

    private void onClickListeners() {
        imgBtnPlus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addItemToList();
            }
        });

        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    private void addItemToList() {
        String itemName = edTxtAddItem.getText().toString();
        float itemQty = Float.parseFloat(edTxtQuantity.getText().toString());
        String itemUnit = spinnerUnits.getSelectedItem().toString();

        if (itemName.isEmpty()) {
            edTxtAddItem.setError("Provide Item Name");
        } else if (String.valueOf(itemQty).isEmpty()) {
            edTxtQuantity.setError("Provide Item Quantity");
        } else {
            ListItem listItem = new ListItem(getExtras(), itemName, itemQty, itemUnit);
            ExecutorUtility.runOnBackgroundThread(() -> {

                long result = App.getInstance().getDatabaseInstance().listItemDao().insertListItem(listItem);

                ExecutorUtility.runOnMainThread(() -> {
                    if (result == -1){
                        Toast.makeText(ExpandedViewActivity.this, "Item Already Exists in this Schedule", Toast.LENGTH_SHORT).show();
                    }
                    edTxtAddItem.setText("");
                    edTxtQuantity.setText("");
                    setupRecyclerView();
                });
            });
        }

    }
}