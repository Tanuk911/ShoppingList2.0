package com.example.activityonesqlite.fragments;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.activityonesqlite.R;
import com.example.activityonesqlite.activities.ExpandedViewActivity;
import com.example.activityonesqlite.activities.ListItemsFrameActivity;
import com.example.activityonesqlite.application.App;
import com.example.activityonesqlite.constants.AppConstants;
import com.example.activityonesqlite.models.entities.ListItem;
import com.example.activityonesqlite.models.entities.Schedule;
import com.example.activityonesqlite.utils.AdapterUtility;
import com.example.activityonesqlite.utils.ExecutorUtility;

import java.util.ArrayList;

public class ListItemsViewFragment extends Fragment {

    View view;
    TextView txtDate, txtLocation;
    RecyclerView listsRecyclerView;
    ImageButton imgBtnPlus;
    EditText edTxtAddItem, edTxtQuantity;
    Spinner spinnerUnits;
    AdapterUtility adapterUtility;
    int scheduleId;
    String date, location;
    Context context;

    public ListItemsViewFragment(int scheduleId, String date, String location, Context context){
        this.scheduleId = scheduleId;
        this.date = date;
        this.location = location;
        this.context = context;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_list_items_view, container, false);

        casting();
        setViewText();
        setupRecyclerView();
        setupSpinner();
        onClickListeners();
        return view;
    }

    private void casting() {
        imgBtnPlus = view.findViewById(R.id.list_item_view_fragment_btnAdd);
        txtDate = view.findViewById(R.id.list_item_view_fragment_txtDate);
        txtLocation = view.findViewById(R.id.list_item_view_fragment_txtLocation);
        listsRecyclerView = view.findViewById(R.id.list_item_view_fragment_recyclerList);
        edTxtAddItem = view.findViewById(R.id.list_item_view_fragment_edtxtNewItem);
        edTxtQuantity = view.findViewById(R.id.list_item_view_fragment_edtxtQty);
        spinnerUnits = view.findViewById(R.id.list_item_view_fragment_spinnerUnits);
        adapterUtility = new AdapterUtility(context);
    }

    private void setViewText() {
                txtDate.setText(date);
                txtLocation.setText(location);
    }

    private void setupRecyclerView() {
        adapterUtility.setupListRecyclerView(listsRecyclerView, scheduleId);
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
            ListItem listItem = new ListItem(scheduleId, itemName, itemQty, itemUnit);
            ExecutorUtility.runOnBackgroundThread(() -> {

                long result = App.getInstance().getDatabaseInstance().listItemDao().insertListItem(listItem);

                ExecutorUtility.runOnMainThread(() -> {
                    if (result == -1) {
                        Toast.makeText(context, "Item Already Exists in this Schedule", Toast.LENGTH_SHORT).show();
                    }
                    edTxtAddItem.setText("");
                    edTxtQuantity.setText("");
                    setupRecyclerView();
                });
            });
        }
    }
}