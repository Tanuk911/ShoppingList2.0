package com.example.activityonesqlite.activities;

import android.app.Dialog;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.example.activityonesqlite.R;
import com.example.activityonesqlite.utilites.AdapterUtility;

import java.util.ArrayList;

public class ListsActivity extends AppCompatActivity {

    RecyclerView recyclerView;
    Button btnBack;
    Dialog dialog_viewList;
    TextView txtDate, txtLocation, txtList, txtAmount;
    EditText edtxtNewList, edtxtAmount, edtxtRemoveItemNo;
    Spinner spinnerUnits;
    Button btnClose, btnPlus, btnMinus, btnEdit, btnConfirm;
    AdapterUtility adapterUtility = new AdapterUtility(this);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_lists);

        activityCasting();
        setupRecyclerView();
        initiateDialog();
        dialogCasting();
        setVisibility();
        onClickListeners();
        setupDialogSpinner();

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    }

    private void activityCasting() {
        recyclerView = findViewById(R.id.lists_recyclerView);
        btnBack = findViewById(R.id.lists_btnBack);
    }

    private void dialogCasting(){
        txtDate = dialog_viewList.findViewById(R.id.view_list_txtDate);
        txtLocation = dialog_viewList.findViewById(R.id.view_list_txtLocation);
        btnClose = dialog_viewList.findViewById(R.id.view_list_close);
        spinnerUnits = dialog_viewList.findViewById(R.id.view_list_spinnerUnits);
        txtList = dialog_viewList.findViewById(R.id.view_list_txtListItem);
        txtAmount = dialog_viewList.findViewById(R.id.view_list_amount);
        edtxtNewList = dialog_viewList.findViewById(R.id.view_list_edtxtNewList);
        edtxtAmount = dialog_viewList.findViewById(R.id.view_list_edtxtAmount);
        edtxtRemoveItemNo = dialog_viewList.findViewById(R.id.view_list_edtxtRemoveItemNo);
        btnPlus = dialog_viewList.findViewById(R.id.view_list_btnAdd);
        btnMinus = dialog_viewList.findViewById(R.id.view_list_btnMinus);
        btnEdit = dialog_viewList.findViewById(R.id.view_list_btnEdit);
        btnConfirm = dialog_viewList.findViewById(R.id.view_list_btnConfrim);
    }

    private void onClickListeners(){
        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                backToMain();
            }
        });

        btnClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                closeDialog();
            }
        });

        btnEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                enableEdit();
            }
        });

        btnConfirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                confirmEdit();
            }
        });
    }

    private void setupRecyclerView(){
        adapterUtility.setupScheduleRecyclerView(recyclerView);
    }

    private void initiateDialog(){
        dialog_viewList = new Dialog(ListsActivity.this);
        dialog_viewList.setContentView(R.layout.view_list_dialog);
        dialog_viewList.getWindow().setLayout(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        dialog_viewList.setCancelable(true);
    }

    private void setVisibility(){
        edtxtRemoveItemNo.setVisibility(View.GONE);
        btnMinus.setVisibility(View.GONE);
    }

    private void setupDialogSpinner(){
        ArrayList<String> unitsList = new ArrayList<>();
        unitsList.add("Kg");
        unitsList.add("L");
        unitsList.add("Packets");
        unitsList.add("No");

        adapterUtility.setSpinner(unitsList, spinnerUnits);
    }

    private void closeDialog() {
        dialog_viewList.dismiss();
    }

    private void enableEdit() {
        btnPlus.setVisibility(View.GONE);
        btnMinus.setVisibility(View.VISIBLE);
        edtxtNewList.setVisibility(View.GONE);
        edtxtAmount.setVisibility(View.GONE);
        spinnerUnits.setVisibility(View.GONE);
        edtxtRemoveItemNo.setVisibility(View.VISIBLE);
    }

    private void confirmEdit() {
        btnMinus.setVisibility(View.GONE);
        btnPlus.setVisibility(View.VISIBLE);
        edtxtRemoveItemNo.setVisibility(View.GONE);
        edtxtNewList.setVisibility(View.VISIBLE);
        edtxtAmount.setVisibility(View.VISIBLE);
        spinnerUnits.setVisibility(View.VISIBLE);
    }

    private void backToMain() {
        finish();
    }
}