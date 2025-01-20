package com.example.activityonesqlite.activities;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.example.activityonesqlite.R;
import com.example.activityonesqlite.application.App;
import com.example.activityonesqlite.fragments.ListItemsEditFragment;
import com.example.activityonesqlite.fragments.ListItemsViewFragment;
import com.example.activityonesqlite.listeners.OnFragmentEditListener;
import com.example.activityonesqlite.models.entities.ListItem;
import com.example.activityonesqlite.utils.ExecutorUtility;

import java.util.List;

public class ListItemsFrameActivity extends AppCompatActivity implements OnFragmentEditListener {

    Button btnEdit, btnConfirm;
    ImageButton btnImgBack;
    Fragment editFragment, viewFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_list_items_frame);

        casting();
        replaceFragment(viewFragment);
        onClickListeners();

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    }

    private void casting() {
        btnEdit = findViewById(R.id.list_item_frame_btnEdit);
        btnConfirm = findViewById(R.id.list_item_frame_btnConfrim);
        btnImgBack = findViewById(R.id.list_item_frame_close);
        editFragment = new ListItemsEditFragment(getExtras(), getScheduleDate(), getScheduleLocation(), ListItemsFrameActivity.this);
        viewFragment = new ListItemsViewFragment(getExtras(), getScheduleDate(), getScheduleLocation(), ListItemsFrameActivity.this);
    }

    private void onClickListeners() {
        btnImgBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        btnEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                replaceFragment(editFragment);
            }
        });

        btnConfirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                replaceFragment(viewFragment);
            }
        });
    }

    private int getExtras() {
        Bundle extras = getIntent().getExtras();
        int scheduleId = extras.getInt("ScheduleId");
        return scheduleId;
    }

    private String getScheduleDate() {
        String date = App.getInstance().getDatabaseInstance().scheduleDao().getScheduleById(getExtras()).getScheduleDate();
        return date;
    }

    private String getScheduleLocation() {
        String location = App.getInstance().getDatabaseInstance().scheduleDao().getScheduleById(getExtras()).getScheduleLocation();
        return location;
    }

    private void replaceFragment(Fragment fragment) {
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.list_item_frame_frameLayout, fragment);
        fragmentTransaction.commit();
    }

    @Override
    public void onEditDate(int scheduleId, String date) {

    }

    @Override
    public void onEditLocation(int scheduleId, String location) {

    }

    @Override
    public void onEditListItem(List<ListItem> changedItems) {

    }
}