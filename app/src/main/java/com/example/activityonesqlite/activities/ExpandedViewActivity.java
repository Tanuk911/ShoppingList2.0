package com.example.activityonesqlite.activities;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.example.activityonesqlite.R;
import com.example.activityonesqlite.databases.DBHelper;
import com.example.activityonesqlite.models.entities.ListItem;
import com.example.activityonesqlite.utilites.AdapterUtility;

import java.util.ArrayList;

public class ExpandedViewActivity extends AppCompatActivity {

    ImageButton btnBack;
    TextView txtDate, txtLocation;
    RecyclerView listsRecyclerView;
    ImageButton imgBtnPlus;
    EditText edTxtAddItem, edTxtQuantity;
    Spinner spinnerUnits;
    AdapterUtility adapterUtility = new AdapterUtility(ExpandedViewActivity.this);
    DBHelper dbHelper = new DBHelper(ExpandedViewActivity.this);

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

    private String[] getExtras() {
        String[] extrasArray = new String[2];
        Bundle extras = getIntent().getExtras();
        String date = extras.getString("PositionDate");
        String location = extras.getString("PositionLocation");
        //The key argument here must match that used in the other activity

        extrasArray[0] = date;
        extrasArray[1] = location;

        return extrasArray;
    }

    private void setViewText() {
        txtDate.setText(getExtras()[0]);
        txtLocation.setText(getExtras()[1]);
    }

    private void setupRecyclerView() {
        adapterUtility.setupListRecyclerView(listsRecyclerView, getExtras()[0], getExtras()[1]);
    }

    private void setupSpinner() {
        ArrayList<String> unitsList = new ArrayList<>();
        unitsList.add("Kg");
        unitsList.add("L");
        unitsList.add("Packets");
        unitsList.add("No");

        adapterUtility.setSpinner(unitsList, spinnerUnits);
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
        int scheduleId = dbHelper.getScheduleId(getExtras()[0], getExtras()[1]);
        String itemName = edTxtAddItem.getText().toString();
        float itemQty = Float.parseFloat(edTxtQuantity.getText().toString());
        String itemUnit = spinnerUnits.getSelectedItem().toString();

        if (itemName.isEmpty()){
            edTxtAddItem.setError("Provide Item Name");
        } else if (String.valueOf(itemQty).isEmpty()){
            edTxtQuantity.setError("Provide Item Quantity");
        } else {
            ListItem listItem = new ListItem(scheduleId, itemName, itemQty, itemUnit);
            dbHelper.addListItem(listItem);
            edTxtAddItem.setText("");
            edTxtQuantity.setText("");
            setupRecyclerView();
        }
    }
}