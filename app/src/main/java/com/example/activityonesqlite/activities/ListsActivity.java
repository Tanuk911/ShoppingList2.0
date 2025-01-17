package com.example.activityonesqlite.activities;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.example.activityonesqlite.R;
import com.example.activityonesqlite.utils.AdapterUtility;

public class ListsActivity extends AppCompatActivity {

    RecyclerView recyclerView;
    Button btnBack;
    AdapterUtility adapterUtility = new AdapterUtility(this);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_lists);

        activityCasting();
        setupRecyclerView();
        onClickListeners();

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

    private void onClickListeners(){
        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                backToMain();
            }
        });
    }

    private void setupRecyclerView(){
        adapterUtility.setupScheduleRecyclerView(recyclerView);
    }

    private void backToMain() {
        finish();
    }
}